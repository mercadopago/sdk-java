package com.mercadopago.serialization;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mercadopago.net.MPResource;

public class Serializer {

  private static final String DATE_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

  private static final Gson gson = new GsonBuilder()
      .setDateFormat(DATE_FORMAT_ISO8601)
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .create();

  public static <T extends MPResource> T deserializeFromJson(Class<T> clazz, String jsonObject) {
    return gson.fromJson(jsonObject, clazz);
  }

  public static <T> JsonObject serializeToJson(T resource) {
    return (JsonObject) gson.toJsonTree(resource);
  }

}
