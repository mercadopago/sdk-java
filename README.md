# Mercado Pago SDK v2 for Java

[![Maven Central](https://img.shields.io/maven-central/v/com.mercadopago/sdk-java.svg)](https://search.maven.org/search?q=g:com.mercadopago%20AND%20a:sdk-java)
![APM](https://img.shields.io/apm/l/vim-mode)

The official [Mercado Pago](https://www.mercadopago.com.br/developers/pt/guides) Java client library.

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
    <version>2.0.0</version>
</dependency>
  ```

2. Run `mvn install` and that's all, you have Mercado Pago SDK installed.

3. Copy the access_token in the [credentials](https://www.mercadopago.com/mlb/account/credentials) section of the page
   and replace YOUR_ACCESS_TOKEN with it.

That's it! Mercado Pago SDK has been successfully installed.

## 📚 Documentation

See our documentation for more details.

- Mercado Pago reference API. [Portuguese](https://www.mercadopago.com.br/developers/pt/reference)
  / [English](https://www.mercadopago.com.br/developers/en/reference)
  / [Spanish](https://www.mercadopago.com.br/developers/es/reference)

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
    } catch (MPException | MPApiException ex) {
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

You can make your own http client instead of using our `MPDefaultHttpClient` since it implements our `MPHttpClient`
interface.

```java
public class CustomHttpClient implements MPHttpClient {
  //...
}
```

## Development

To contribute to the project you need to have installed in your machine [pre-commit tool](https://pre-commit.com/) to
check the code style and formatting.

1. To check if pre-commit is installed, you must run the command below successfully:

```
$ pre-commit --version
pre-commit 2.17.0
```

2. After pre-commit is installed at your machine, inside the SDK project folder, you must run the command below to set up the git hook scripts of the
project:

```
$ pre-commit install
```

After that, git hooks will run automatically before every commit command.

## ❤️ Support

If you require technical support, please contact our support team
at [developers.mercadopago.com](https://developers.mercadopago.com)

## 🏻 License

```
MIT license. Copyright (c) 2022 - Mercado Pago / Mercado Libre 
For more information, see the LICENSE file.
```
