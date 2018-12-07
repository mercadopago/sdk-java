[![Build Status](https://travis-ci.org/mercadopago/dx-java.svg?branch=master)](https://travis-ci.org/mercadopago/dx-java)
[![codecov](https://codecov.io/gh/mercadopago/dx-java/branch/master/graph/badge.svg)](https://codecov.io/gh/mercadopago/dx-java)

# MercadoPago SDK for Java

This library provides developers with a simple set of bindings to the Mercado Pago API.

## Installation

### Using Maven
1. Append MercadoPago dependencies to pom.xml

  ```xml
    ...
    <dependencies>
        <dependency>
            <groupId> com.mercadopago </groupId>
            <artifactId> dx-java </artifactId>
            <version> 1.0.36 </version>
        </dependency>
    </dependencies>
    ...
  ```
2. Run `mvn install` and thats all, you have Mercado Pago SDK installed.

## Quick Start

1. Import Mercado Pago basic clases. `import com.mercadopago.*;`
2. Setup your credentials.
  - **For Web-checkout:**
    ```java
      MercadoPago.SDK.setClientSecret("CLIENT_SECRET");
      MercadoPago.SDK.setClientId("CLIENT_ID");
    ```
  - **For API or Custom-checkout:**
    ```java
      MercadoPago.SDK.setAccessToken("ACCESS_TOKEN");
    ```
3. Using Resource objects.

![sdk resource structure](https://user-images.githubusercontent.com/864790/34393059-9acad058-eb2e-11e7-9987-494eaf19d109.png)

**Sample**

```java
  import com.mercadopago.*;
  import com.mercadopago.exceptions.MPConfException;
  import com.mercadopago.exceptions.MPException;
  import com.mercadopago.resources.Payment;
  import com.mercadopago.resources.datastructures.payment.Payer;

  public class Main {

      public static void main(String[] args)throws MPException, MPConfException {

          MercadoPago.SDK.setClientSecret(System.getenv("CLIENT_SECRET"));
          MercadoPago.SDK.setClientId(System.getenv("CLIENT_ID_OK"));

          Payment payment = new Payment()
                  .setTransactionAmount(100f)
                  .setToken("your_cardtoken")
                  .setDescription("description")
                  .setInstallments(1)
                  .setPaymentMethodId("visa")
                  .setPayer(new Payer()
                          .setEmail("dummy_email"));

          payment.save();



      }
  }
```

### Support

1. Create an issue https://github.com/mercadopago/dx-java/issues
2. Follow your issue status in Issues Support Dashboard https://github.com/mercadopago/dx-java/projects/2

