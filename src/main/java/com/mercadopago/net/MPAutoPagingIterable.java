package com.mercadopago.net;

import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Lazy, auto-paging iterable that fetches pages of results on demand.
 *
 * <p>Consumers iterate over individual items without needing to manage offset/limit
 * pagination manually. Pages are fetched one at a time; iteration stops when:
 * <ul>
 *   <li>the {@code results} list of the current page is {@code null} or empty, or</li>
 *   <li>{@code offset + limit >= paging.total} (no more items).</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 * MPSearchRequest request = MPSearchRequest.builder().limit(100).offset(0).build();
 * for (Payment p : paymentClient.searchAll(request)) {
 *     process(p);
 * }
 * }</pre>
 *
 * @param <T> the resource type contained in each page
 */
public class MPAutoPagingIterable<T> implements Iterable<T> {

  /** Default page size when the search request has no explicit limit set. */
  private static final int DEFAULT_PAGE_SIZE = 100;

  /** Functional interface for executing a search that returns a page of results. */
  @FunctionalInterface
  public interface SearchFunction<T> {
    /**
     * Executes a search request and returns a page of results.
     *
     * @param request        the search parameters for this page
     * @param requestOptions optional per-request overrides, may be {@code null}
     * @return a page of results
     * @throws MPException    on transport-level failure
     * @throws MPApiException on API error response
     */
    MPResultsResourcesPage<T> search(MPSearchRequest request, MPRequestOptions requestOptions)
        throws MPException, MPApiException;
  }

  private final SearchFunction<T> searchFunction;
  private final MPSearchRequest initialRequest;
  private final MPRequestOptions requestOptions;

  /**
   * Creates a new auto-paging iterable.
   *
   * @param searchFunction the function to call to retrieve each page
   * @param initialRequest the initial search request (offset/limit define the starting point)
   * @param requestOptions optional per-request options, may be {@code null}
   * @param ignored        retained for API compatibility with callers that pass the type token
   */
  public MPAutoPagingIterable(
      SearchFunction<T> searchFunction,
      MPSearchRequest initialRequest,
      MPRequestOptions requestOptions,
      Class<T> ignored) {
    this.searchFunction = searchFunction;
    this.initialRequest = initialRequest;
    this.requestOptions = requestOptions;
  }

  @Override
  public Iterator<T> iterator() {
    return new AutoPagingIterator();
  }

  private class AutoPagingIterator implements Iterator<T> {

    private List<T> currentPage = Collections.emptyList();
    private int indexInPage = 0;
    private int currentOffset;
    private int limit;
    private int totalItems = Integer.MAX_VALUE; // updated after first fetch
    private boolean firstFetch = true;
    private boolean done = false;

    AutoPagingIterator() {
      this.currentOffset = initialRequest.getOffset() != null ? initialRequest.getOffset() : 0;
      this.limit = initialRequest.getLimit() != null && initialRequest.getLimit() > 0
          ? initialRequest.getLimit()
          : DEFAULT_PAGE_SIZE;
    }

    @Override
    public boolean hasNext() {
      if (indexInPage < currentPage.size()) {
        return true;
      }
      if (done) {
        return false;
      }
      fetchNextPage();
      return indexInPage < currentPage.size();
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException("No more elements in the auto-paging iterator");
      }
      return currentPage.get(indexInPage++);
    }

    private void fetchNextPage() {
      if (!firstFetch && currentOffset >= totalItems) {
        done = true;
        return;
      }

      MPSearchRequest pageRequest = MPSearchRequest.builder()
          .limit(limit)
          .offset(currentOffset)
          .filters(initialRequest.getFilters())
          .build();

      try {
        MPResultsResourcesPage<T> page = searchFunction.search(pageRequest, requestOptions);

        List<T> results = page.getResults();
        if (results == null || results.isEmpty()) {
          done = true;
          currentPage = Collections.emptyList();
          return;
        }

        if (page.getPaging() != null) {
          totalItems = page.getPaging().getTotal();
        }

        currentPage = results;
        indexInPage = 0;
        firstFetch = false;

        // Advance offset for the next page fetch
        currentOffset += results.size();

        // If we've already received all items, mark as done after this page
        if (currentOffset >= totalItems) {
          done = true;
        }

      } catch (MPException | MPApiException e) {
        throw new MPAutoPagingException(
            "Error fetching page at offset " + currentOffset, e);
      }
    }
  }

  /**
   * Unchecked wrapper for {@link MPException} and {@link MPApiException} thrown during
   * auto-pagination. Callers may catch this to handle API errors mid-iteration.
   */
  public static class MPAutoPagingException extends RuntimeException {

    /**
     * Creates a new paging exception.
     *
     * @param message descriptive message
     * @param cause   the underlying checked exception
     */
    public MPAutoPagingException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
