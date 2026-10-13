# Mercado Pago Java SDK documentation

Use the root README to install the SDK and send a first request. This index connects runnable examples with the operational behavior implemented by the Java SDK.

## Start with Orders

Orders is the recommended learning path because it demonstrates the complete payment lifecycle. Begin with [CreateOrder](../src/main/java/com/mercadopago/example/apis/order/CreateOrder.java), inspect the result with [GetOrderById](../src/main/java/com/mercadopago/example/apis/order/GetOrderById.java), and continue with [ProcessOrderById](../src/main/java/com/mercadopago/example/apis/order/ProcessOrderById.java), [CaptureOrder](../src/main/java/com/mercadopago/example/apis/order/CaptureOrder.java), [CancelOrder](../src/main/java/com/mercadopago/example/apis/order/CancelOrder.java), or [RefundTotal](../src/main/java/com/mercadopago/example/apis/order/RefundTotal.java).

Before production traffic, add [bounded retries and idempotency](request-options.md) and [typed error handling](error-handling.md). The [Orders-first example catalog](../src/main/java/com/mercadopago/example/README.md#orders-first) also covers Checkout Pro, transactions, PSE, 3DS, automatic payments, industry fields, and Point configuration.

## Operational guides

| Question | Guide |
|---|---|
| How should timeout, retries, and idempotency be configured? | [Request options](request-options.md) |
| How should failures be classified? | [Error handling](error-handling.md) |
| How should signed notifications be processed? | [Webhook validation](webhooks.md) |
| What should be checked first during an incident? | [Troubleshooting](troubleshooting.md) |

Endpoint fields and response schemas belong to the [Mercado Pago API reference](https://www.mercadopago.com/developers/en/reference).
