package com.mercadopago.net;

/** Http status class. */
public class HttpStatus {
  public static final int OK = 200;

  public static final int CREATED = 201;

  public static final int NO_CONTENT = 204;

  public static final int BAD_REQUEST = 400;

  public static final int UNAUTHORIZED = 401;

  public static final int PAYMENT_REQUIRED = 402;

  public static final int FORBIDDEN = 403;

  public static final int NOT_FOUND = 404;

  public static final int CONFLICT = 409;

  public static final int UNPROCESSABLE_ENTITY = 422;

  public static final int LOCKED = 423;

  public static final int FAILED_DEPENDENCY = 424;

  public static final int TOO_MANY_REQUESTS = 429;

  public static final int INTERNAL_SERVER_ERROR = 500;
}
