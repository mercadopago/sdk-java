package com.mercadopago.net;

import static com.mercadopago.helper.MockHelper.generateHttpResponseFromFile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.*;

import com.mercadopago.BaseClientTest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.ResultsPaging;
import com.mercadopago.resources.payment.Payment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for TASK-004: Auto-pagination via {@link MPAutoPagingIterable}.
 *
 * <p>Tests cover:
 * <ul>
 *   <li>Single-page iteration (results.size() == total)</li>
 *   <li>Multi-page iteration across page boundaries</li>
 *   <li>Empty result set stops iteration immediately</li>
 *   <li>NoSuchElementException when exhausted</li>
 *   <li>{@link PaymentClient#searchAll} integration</li>
 * </ul>
 */
class MPAutoPagingIterableTest extends BaseClientTest {

  /** Builds a page with synthetic items. */
  private static <T> MPResultsResourcesPage<T> buildPage(List<T> items, int total, int offset) {
    MPResultsResourcesPage<T> page = new MPResultsResourcesPage<>();
    page.setResults(items);
    // ResultsPaging uses @Getter — we need to set via reflection since it has no setter
    try {
      java.lang.reflect.Field totalF = ResultsPaging.class.getDeclaredField("total");
      java.lang.reflect.Field limitF = ResultsPaging.class.getDeclaredField("limit");
      java.lang.reflect.Field offsetF = ResultsPaging.class.getDeclaredField("offset");
      totalF.setAccessible(true);
      limitF.setAccessible(true);
      offsetF.setAccessible(true);
      ResultsPaging paging = new ResultsPaging();
      totalF.set(paging, total);
      limitF.set(paging, items.size() > 0 ? items.size() : 10);
      offsetF.set(paging, offset);
      page.setPaging(paging);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
    return page;
  }

  @Test
  void singlePage_iteratesAllItems() throws MPException, MPApiException {
    List<String> items = Arrays.asList("a", "b", "c");
    MPResultsResourcesPage<String> page = buildPage(items, 3, 0);

    MPAutoPagingIterable<String> iterable = new MPAutoPagingIterable<>(
        (req, opts) -> page,
        MPSearchRequest.builder().limit(10).offset(0).build(),
        null,
        String.class);

    List<String> collected = new ArrayList<>();
    for (String item : iterable) {
      collected.add(item);
    }

    assertEquals(Arrays.asList("a", "b", "c"), collected);
  }

  @Test
  void emptyResults_iterationStopsImmediately() throws MPException, MPApiException {
    MPResultsResourcesPage<String> page = buildPage(Collections.emptyList(), 0, 0);

    MPAutoPagingIterable<String> iterable = new MPAutoPagingIterable<>(
        (req, opts) -> page,
        MPSearchRequest.builder().limit(10).offset(0).build(),
        null,
        String.class);

    assertFalse(iterable.iterator().hasNext());
  }

  @Test
  void multiPage_fetchesAllPages() throws MPException, MPApiException {
    // Page 1: items 1-3, total=5
    MPResultsResourcesPage<Integer> page1 = buildPage(Arrays.asList(1, 2, 3), 5, 0);
    // Page 2: items 4-5, total=5
    MPResultsResourcesPage<Integer> page2 = buildPage(Arrays.asList(4, 5), 5, 3);
    // Page 3: empty — stops
    MPResultsResourcesPage<Integer> pageEmpty = buildPage(Collections.emptyList(), 5, 5);

    MPAutoPagingIterable.SearchFunction<Integer> fn = mock(
        MPAutoPagingIterable.SearchFunction.class);
    when(fn.search(any(), any()))
        .thenReturn(page1)
        .thenReturn(page2)
        .thenReturn(pageEmpty);

    MPAutoPagingIterable<Integer> iterable = new MPAutoPagingIterable<>(
        fn,
        MPSearchRequest.builder().limit(3).offset(0).build(),
        null,
        Integer.class);

    List<Integer> collected = new ArrayList<>();
    for (Integer item : iterable) {
      collected.add(item);
    }

    assertEquals(Arrays.asList(1, 2, 3, 4, 5), collected);
    verify(fn, times(2)).search(any(), any()); // 2 fetches needed (page1 gives 3, page2 gives 2=total)
  }

  @Test
  void iterator_hasNext_idempotent() throws MPException, MPApiException {
    MPResultsResourcesPage<String> page = buildPage(Collections.singletonList("only"), 1, 0);

    MPAutoPagingIterable<String> iterable = new MPAutoPagingIterable<>(
        (req, opts) -> page,
        MPSearchRequest.builder().limit(10).offset(0).build(),
        null,
        String.class);

    Iterator<String> iter = iterable.iterator();
    assertTrue(iter.hasNext());
    assertTrue(iter.hasNext()); // calling twice does not advance
    assertEquals("only", iter.next());
    assertFalse(iter.hasNext());
  }

  @Test
  void iterator_next_withoutHasNext_throwsNoSuchElement() {
    MPResultsResourcesPage<String> page = buildPage(Collections.emptyList(), 0, 0);

    MPAutoPagingIterable<String> iterable = new MPAutoPagingIterable<>(
        (req, opts) -> page,
        MPSearchRequest.builder().limit(10).offset(0).build(),
        null,
        String.class);

    Iterator<String> iter = iterable.iterator();
    assertThrows(NoSuchElementException.class, iter::next);
  }

  @Test
  void defaultPageSize_usedWhenRequestHasNoLimit() throws MPException, MPApiException {
    MPResultsResourcesPage<String> page = buildPage(Arrays.asList("x", "y"), 2, 0);

    MPAutoPagingIterable.SearchFunction<String> fn = mock(
        MPAutoPagingIterable.SearchFunction.class);
    when(fn.search(any(), any())).thenReturn(page);

    MPAutoPagingIterable<String> iterable = new MPAutoPagingIterable<>(
        fn,
        MPSearchRequest.builder().offset(0).build(), // no limit
        null,
        String.class);

    List<String> collected = new ArrayList<>();
    for (String s : iterable) {
      collected.add(s);
    }
    assertEquals(2, collected.size());
  }

  @Test
  void apiError_wrappedInMPAutoPagingException() throws MPException, MPApiException {
    MPAutoPagingIterable.SearchFunction<String> fn = mock(
        MPAutoPagingIterable.SearchFunction.class);
    when(fn.search(any(), any())).thenThrow(new MPException("connection error"));

    MPAutoPagingIterable<String> iterable = new MPAutoPagingIterable<>(
        fn,
        MPSearchRequest.builder().limit(10).offset(0).build(),
        null,
        String.class);

    Iterator<String> iter = iterable.iterator();
    assertThrows(MPAutoPagingIterable.MPAutoPagingException.class, iter::hasNext);
  }

  // --------- Integration: PaymentClient.searchAll() ----------

  @Test
  void paymentClient_searchAll_returnsMPAutoPagingIterable() throws IOException, MPException, MPApiException {
    HttpResponse httpResponse = generateHttpResponseFromFile(
        "payment/payment_search.json", HttpStatus.OK);
    doReturn(httpResponse)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PaymentClient paymentClient = new PaymentClient();
    MPSearchRequest request = MPSearchRequest.builder().limit(5).offset(0).build();

    Iterable<Payment> allPayments = paymentClient.searchAll(request);
    assertNotNull(allPayments);
    assertInstanceOf(MPAutoPagingIterable.class, allPayments);
  }

  @Test
  void paymentClient_searchAll_iteratesFirstPage() throws IOException, MPException, MPApiException {
    // payment_search.json has 5 results with paging.total=102
    // We mock 2 calls: first returns 5 items, second returns empty (stops iteration)
    HttpResponse httpResponse1 = generateHttpResponseFromFile(
        "payment/payment_search.json", HttpStatus.OK);
    HttpResponse httpResponse2 = generateHttpResponseFromFile(
        "payment/payment_search_empty.json", HttpStatus.OK);

    doReturn(httpResponse1)
        .doReturn(httpResponse2)
        .when(HTTP_CLIENT)
        .execute(any(HttpRequestBase.class), any(HttpContext.class));

    PaymentClient paymentClient = new PaymentClient();
    MPSearchRequest request = MPSearchRequest.builder().limit(5).offset(0).build();

    List<Payment> collected = new ArrayList<>();
    for (Payment p : paymentClient.searchAll(request)) {
      collected.add(p);
    }

    assertFalse(collected.isEmpty());
    assertEquals(5, collected.size());
  }
}
