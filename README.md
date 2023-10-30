# Mercado Pago SDK for Java

[![Maven Central](https://img.shields.io/maven-central/v/com.mercadopago/sdk-java.svg)](https://search.maven.org/search?q=g:com.mercadopago%20AND%20a:sdk-java)
![APM](https://img.shields.io/apm/l/vim-mode)

The official [Mercado Pago](https://www.mercadopago.com/developers/en/guides) Java client library.

## 💡 Requirements

Java 1.8 or later

## 📲 Installation

First time using Mercado Pago? Create your [Mercado Pago account](https://www.mercadopago.com), if you don’t have one
already.

1. Append MercadoPago dependencies to pom.xml

  ```xml

<dependency>
    <groupId>com.mercadopago</groupId>
    <artifactId>sdk-java</artifactId>
    <version>2.1.16</version>
</dependency>
  ```

2. Run `mvn install` and that's all, you have Mercado Pago SDK installed.

3. Copy the access_token in the [credentials](https://www.mercadopago.com/developers/panel) section of the page and
   replace YOUR_ACCESS_TOKEN with it.

That's it! Mercado Pago SDK has been successfully installed.

## 🌟 Getting Started

Simple usage looks like:

```java
import com.mercadopago.*;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import java.math.BigDecimal;

public class Example {

  public static void main(String[] args) {
    MercadoPagoConfig.setAccessToken("YOUR_ACCESS_TOKEN");

    PaymentClient client = new PaymentClient();

    PaymentCreateRequest createRequest =
        PaymentCreateRequest.builder()
            .transactionAmount(new BigDecimal(1000))
            .token("your_cardtoken")
            .description("description")
            .installments(1)
            .paymentMethodId("visa")
            .payer(PaymentPayerRequest.builder().email("dummy_email").build())
            .build();

    try {
      Payment payment = client.create(createRequest);
      System.out.println(payment);
    } catch (MPApiException ex) {
      System.out.printf(
          "MercadoPago Error. Status: %s, Content: %s%n",
          ex.getApiResponse().getStatusCode(), ex.getApiResponse().getContent());
    } catch (MPException ex) {
      ex.printStackTrace();
    }
  }
}
```

### Per-request Configuration

All the request methods accept an optional `MPRequestOptions` object. With this you can set a custom access token,
custom timeouts or even any custom headers you want, like an idempotency key for example.

```java
public class Example {

  public static void main(String[] args) {
    PaymentClient client = new PaymentClient();

    Map<String, String> customHeaders = new HashMap<>();
    customHeaders.put("x-idempotency-key", "...");

    MPRequestOptions requestOptions =
        MPRequestOptions.builder()
            .accessToken("custom_access_token")
            .connectionRequestTimeout(2000)
            .connectionTimeout(2000)
            .socketTimeout(2000)
            .customHeaders(customHeaders)
            .build();

    try {
      Payment payment = client.create(createRequest, requestOptions);
      System.out.println(payment);
    } catch (MPException | MPApiException ex) {
      ex.printStackTrace();
    }
  }
}
```

### SDK configurations

You can also set some customizations directly at MercadoPagoConfig class, for example set custom timeouts, a custom http
client, log configurations, etc...

```java
public class Example {

  public static void main(String[] args) {

    MercadoPagoConfig.setConnectionRequestTimeout(2000);
    MercadoPagoConfig.setSocketTimeout(2000);
    MercadoPagoConfig.setLoggingLevel(Level.FINEST);
  }
}
```

### Custom Http Client

You can use a custom http client instead of using the default `MPDefaultHttpClient` by implementing the `MPHttpClient`
interface.

```java
public class CustomHttpClient implements MPHttpClient {
  //...
}
```

## 📚 Documentation

See our documentation for more details.

- Mercado Pago reference API. [Portuguese](https://www.mercadopago.com/developers/pt/reference)
  / [English](https://www.mercadopago.com/developers/en/reference)
  / [Spanish](https://www.mercadopago.com/developers/es/reference)

## 🤝 Contributing

All contributions are welcome, ranging from people wanting to triage issues, others wanting to write documentation, to
people wanting to contribute code.

Please read and follow our [contribution guidelines](CONTRIBUTING.md). Contributions not following these guidelines will
be disregarded. The guidelines are in place to make all of our lives easier and make contribution a consistent process
for everyone.

### Patches to version 1.x.x

Since the release of version 2.0.0, version 1 is deprecated and will not be receiving new
features, only bug fixes. If you need to submit PRs for that version, please do so by using `develop-v1` as your base
branch.

## ❤️ Support

If you require technical support, please contact our support team at our developers
site:  [English](https://www.mercadopago.com/developers/en/support/center/contact)
/ [Portuguese](https://www.mercadopago.com/developers/pt/support/center/contact)
/ [Spanish](https://www.mercadopago.com/developers/es/support/center/contact)

## 🏻 License

```
MIT license. Copyright (c) 2022 - Mercado Pago / Mercado Libre 
For more information, see the LICENSE file.
```
