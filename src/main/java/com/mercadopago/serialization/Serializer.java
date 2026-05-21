package com.mercadopago.serialization;

import static com.google.gson.stream.JsonToken.END_DOCUMENT;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import com.mercadopago.exceptions.MPJsonParseException;
import com.mercadopago.net.MPElementsResourcesPage;
import com.mercadopago.net.MPResource;
import com.mercadopago.net.MPResourceList;
import com.mercadopago.net.MPResultsResourcesPage;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Central JSON serialization and deserialization utility for the MercadoPago Java SDK.
 *
 * <p>This class wraps Google Gson and provides static helper methods to convert between
 * Java objects (extending {@link MPResource}) and their JSON representations. Key behaviors:
 * <ul>
 *   <li><b>Field naming:</b> Java camelCase fields are mapped to JSON snake_case
 *       via {@link FieldNamingPolicy#LOWER_CASE_WITH_UNDERSCORES}.</li>
 *   <li><b>Date handling:</b> {@link OffsetDateTime} and {@link LocalDate} are serialized
 *       as ISO 8601 strings. Deserialization attempts multiple ISO 8601 format variants
 *       (see {@link #ISO8601_DATETIME_FORMATTERS}) to handle responses from different
 *       API versions.</li>
 *   <li><b>Validation:</b> JSON strings are validated before deserialization via
 *       {@link #isJsonValid(String)} to provide clearer error messages.</li>
 * </ul>
 *
 * @see MPResource
 * @see MPJsonParseException
 */
public class Serializer {

  /**
   * ISO 8601 extended date-time formatter for deserialization.
   * Pattern: {@code yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX][XX][X]} -- supports optional
   * milliseconds and various offset formats.
   */
  private static final DateTimeFormatter DESERIALIZE_DATE_FORMAT_ISO8601_EXTENDED =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX][XX][X]");

  /**
   * ISO 8601 basic (compact) date-time formatter for deserialization.
   * Pattern: {@code yyyyMMdd'T'HHmmss[.SSS][XXX][XX][X]} -- no hyphens or colons in
   * the date/time portion.
   */
  private static final DateTimeFormatter DESERIALIZE_DATE_FORMAT_ISO8601_BASIC =
      DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss[.SSS][XXX][XX][X]");

  /**
   * Ordered array of date-time formatters tried during deserialization. The first
   * formatter that successfully parses the input wins. Order:
   * {@link DateTimeFormatter#ISO_DATE_TIME}, extended ISO 8601, basic ISO 8601.
   */
  private static final DateTimeFormatter[] ISO8601_DATETIME_FORMATTERS =
      new DateTimeFormatter[] {
        DateTimeFormatter.ISO_DATE_TIME,
        DESERIALIZE_DATE_FORMAT_ISO8601_EXTENDED,
        DESERIALIZE_DATE_FORMAT_ISO8601_BASIC,
      };

  /**
   * ISO 8601 date-time pattern used for <b>serialization</b> of {@link OffsetDateTime}
   * values. Always includes milliseconds and a full timezone offset
   * (e.g., {@code 2023-10-15T14:30:00.000-03:00}).
   */
  private static final String SERIALIZE_DATE_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

  /**
   * Attempts to parse a JSON element as an {@link OffsetDateTime} by trying each formatter
   * in {@link #ISO8601_DATETIME_FORMATTERS} in order.
   *
   * @param json the JSON element containing a date-time string
   * @return the parsed {@link OffsetDateTime}, or {@code null} if the element is null/empty
   * @throws DateTimeParseException if none of the known formatters can parse the value
   */
  private static OffsetDateTime parseDateTime(JsonElement json) {
    if (json == null || json.getAsString().isEmpty()) {
      return null;
    }

    for (int i = 0; i < ISO8601_DATETIME_FORMATTERS.length; i++) {
      try {
        return OffsetDateTime.parse(json.getAsString(), ISO8601_DATETIME_FORMATTERS[i]);
      } catch (DateTimeParseException e) {
        // try all formatters, if none works, throw exception from last one
        if (i == ISO8601_DATETIME_FORMATTERS.length - 1) {
          throw e;
        }
      }
    }
    return null;
  }

  /**
   * Pre-configured Gson instance shared by all serialization methods. Configured with:
   * <ul>
   *   <li>{@link FieldNamingPolicy#LOWER_CASE_WITH_UNDERSCORES} for snake_case JSON mapping</li>
   *   <li>Custom {@link OffsetDateTime} serializer/deserializer using ISO 8601 formats</li>
   *   <li>Custom {@link LocalDate} serializer/deserializer using {@link DateTimeFormatter#ISO_LOCAL_DATE}</li>
   * </ul>
   */
  private static final Gson GSON =
      new GsonBuilder()
          .registerTypeAdapter(
              OffsetDateTime.class,
              (JsonDeserializer<OffsetDateTime>) (json, type, context) -> parseDateTime(json))
          .registerTypeAdapter(
              OffsetDateTime.class,
              (JsonSerializer<OffsetDateTime>)
                  (offsetDateTime, type, context) ->
                      new JsonPrimitive(
                          DateTimeFormatter.ofPattern(SERIALIZE_DATE_FORMAT_ISO8601)
                              .format(offsetDateTime)))
          .registerTypeAdapter(
              LocalDate.class,
              (JsonSerializer<LocalDate>)
                  (localDate, type, context) ->
                      new JsonPrimitive(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE)))
          .registerTypeAdapter(
              LocalDate.class,
              (JsonDeserializer<LocalDate>)
                  (localDate, type, context) -> LocalDate.parse(localDate.getAsString()))
          .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
          .create();

  /**
   * Deserializes a JSON string into a single {@link MPResource} instance.
   *
   * <p>The JSON is first validated with {@link #isJsonValid(String)}, then converted
   * using the pre-configured Gson instance (snake_case mapping, ISO 8601 dates).
   *
   * @param clazz      the target class to deserialize into
   * @param jsonObject the raw JSON string returned by the API
   * @param <T>        a type extending {@link MPResource}
   * @return the deserialized resource instance
   * @throws MPJsonParseException if the JSON is malformed or cannot be mapped to the target class
   */
  public static <T extends MPResource> T deserializeFromJson(Class<T> clazz, String jsonObject)
      throws MPJsonParseException {
    try {
      if (isJsonValid(jsonObject)) {
        return GSON.fromJson(jsonObject, clazz);
      } else {
        throw new MPJsonParseException(String.format("Could not parse json: %s", jsonObject));
      }
    } catch (IOException e) {
      throw new MPJsonParseException("Could not parse json", e);
    }
  }

  /**
   * Deserializes a JSON string into an {@link MPResultsResourcesPage}, which represents
   * a paginated API response whose items are nested under a {@code "results"} key.
   *
   * @param type       the parameterized type token (e.g.,
   *                   {@code new TypeToken<MPResultsResourcesPage<Payment>>(){}.getType()})
   * @param jsonObject the raw JSON string containing the paginated response
   * @param <T>        a type extending {@link MPResource}
   * @return the deserialized page containing a list of results and paging metadata
   * @throws MPJsonParseException if the JSON is malformed or cannot be mapped to the target type
   * @see MPResultsResourcesPage
   */
  public static <T extends MPResource>
      MPResultsResourcesPage<T> deserializeResultsResourcesPageFromJson(
          Type type, String jsonObject) throws MPJsonParseException {
    try {
      if (isJsonValid(jsonObject)) {
        return GSON.fromJson(jsonObject, type);
      } else {
        throw new MPJsonParseException(String.format("Could not parse json: %s", jsonObject));
      }
    } catch (IOException e) {
      throw new MPJsonParseException("Could not parse json", e);
    }
  }

  /**
   * Deserializes a JSON string into an {@link MPElementsResourcesPage}, which represents
   * a paginated API response whose items are nested under an {@code "elements"} key.
   *
   * @param type       the parameterized type token (e.g.,
   *                   {@code new TypeToken<MPElementsResourcesPage<Refund>>(){}.getType()})
   * @param jsonObject the raw JSON string containing the paginated response
   * @param <T>        a type extending {@link MPResource}
   * @return the deserialized page containing a list of elements and paging metadata
   * @throws MPJsonParseException if the JSON is malformed or cannot be mapped to the target type
   * @see MPElementsResourcesPage
   */
  public static <T extends MPResource>
      MPElementsResourcesPage<T> deserializeElementsResourcesPageFromJson(
          Type type, String jsonObject) throws MPJsonParseException {
    try {
      if (isJsonValid(jsonObject)) {
        return GSON.fromJson(jsonObject, type);
      } else {
        throw new MPJsonParseException(String.format("Could not parse json: %s", jsonObject));
      }
    } catch (IOException | MPJsonParseException e) {
      throw new MPJsonParseException("Could not parse json", e);
    }
  }

  /**
   * Deserializes a JSON array string into an {@link MPResourceList} containing individual
   * resource instances.
   *
   * <p>This method handles API responses that return a top-level JSON array (as opposed
   * to a paginated wrapper object). Each element of the array is deserialized independently
   * and collected into the returned list.
   *
   * @param clazz      the class of each element in the JSON array
   * @param jsonObject the raw JSON array string (e.g., {@code [{"id":1}, {"id":2}]})
   * @param <T>        a type extending {@link MPResource}
   * @return an {@link MPResourceList} containing the deserialized resources
   * @throws MPJsonParseException if the JSON is malformed or an element cannot be deserialized
   * @see MPResourceList
   */
  public static <T extends MPResource> MPResourceList<T> deserializeListFromJson(
      Class<T> clazz, String jsonObject) throws MPJsonParseException {
    try {
      if (isJsonValid(jsonObject)) {
        MPResourceList<T> resourceList = new MPResourceList<>();
        List<T> results = new ArrayList<>();
        JsonArray jsonArray = JsonParser.parseString(jsonObject).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
          T resource = GSON.fromJson(jsonArray.get(i), clazz);
          results.add(resource);
        }
        resourceList.setResults(results);

        return resourceList;
      } else {
        throw new MPJsonParseException(String.format("Could not parse json: %s", jsonObject));
      }
    } catch (IOException e) {
      throw new MPJsonParseException("Could not parse json", e);
    }
  }

  /**
   * Serializes a Java object into a Gson {@link JsonObject} suitable for use as an
   * API request body.
   *
   * <p>Fields are converted to snake_case and dates are formatted as ISO 8601 strings
   * per the SDK's Gson configuration.
   *
   * @param resource the object to serialize (typically an API request DTO)
   * @param <T>      the type of the object
   * @return a {@link JsonObject} representation of the resource
   */
  public static <T> JsonObject serializeToJson(T resource) {
    return (JsonObject) GSON.toJsonTree(resource);
  }

  /**
   * Validates whether the given string is syntactically correct JSON.
   *
   * <p>The method uses a streaming {@link JsonReader} to walk the entire token structure
   * without materializing objects, making it efficient for large payloads. It returns
   * {@code false} for malformed JSON and {@code true} for valid JSON objects, arrays,
   * or primitive values.
   *
   * @param json the raw string to validate
   * @return {@code true} if the string is valid JSON, {@code false} otherwise
   * @throws IOException if an I/O error occurs while reading the string
   */
  public static boolean isJsonValid(String json) throws IOException {
    try {
      JsonReader jsonReader = new JsonReader(new StringReader(json));
      JsonToken token;
      loop:
      while ((token = jsonReader.peek()) != END_DOCUMENT && token != null) {
        switch (token) {
          case BEGIN_ARRAY:
            jsonReader.beginArray();
            break;
          case END_ARRAY:
            jsonReader.endArray();
            break;
          case BEGIN_OBJECT:
            jsonReader.beginObject();
            break;
          case END_OBJECT:
            jsonReader.endObject();
            break;
          case NAME:
            jsonReader.nextName();
            break;
          case STRING:
          case NUMBER:
          case BOOLEAN:
          case NULL:
            jsonReader.skipValue();
            break;
          case END_DOCUMENT:
            break loop;
          default:
            throw new AssertionError(token);
        }
      }
      return true;
    } catch (final MalformedJsonException ignored) {
      return false;
    }
  }
}
