# Java examples

These programs demonstrate integration flows implemented by the Java SDK. Start with Orders, then choose another API only when the payment product requires it.

## Orders first

Orders is the most complete learning path in this repository: it covers creation, retrieval, processing, capture, cancellation, refunds, and transaction management.

| Stage | Goal | Example |
|---|---|---|
| 1. Create | Submit a basic order | [`CreateOrder.java`](apis/order/CreateOrder.java) |
| 2. Inspect | Retrieve the server-side result | [`GetOrderById.java`](apis/order/GetOrderById.java) |
| 3. Advance | Process or capture an order | [`ProcessOrderById.java`](apis/order/ProcessOrderById.java) · [`CaptureOrder.java`](apis/order/CaptureOrder.java) |
| 4. Reverse | Cancel, partially refund, or fully refund | [`CancelOrder.java`](apis/order/CancelOrder.java) · [`RefundPartial.java`](apis/order/RefundPartial.java) · [`RefundTotal.java`](apis/order/RefundTotal.java) |
| 5. Manage transactions | Create, update, or delete a transaction | [`CreateTransaction.java`](apis/order/CreateTransaction.java) · [`UpdateTransaction.java`](apis/order/UpdateTransaction.java) · [`DeleteTransaction.java`](apis/order/DeleteTransaction.java) |
| 6. Add a payment flow | Use Checkout Pro, PSE, 3DS, or automatic payments | [`CreateOrderCheckoutPro.java`](apis/order/CreateOrderCheckoutPro.java) · [`CreateOrderPSE.java`](apis/order/CreateOrderPSE.java) · [`CreateOrderWith3DS.java`](apis/order/CreateOrderWith3DS.java) · [`CreateOrderWithAutomaticPayments.java`](apis/order/CreateOrderWithAutomaticPayments.java) |
| 7. Add specialized fields | Use industry data or Point configuration | [`CreateOrderWithIndustryFields.java`](apis/order/CreateOrderWithIndustryFields.java) · [`CreateOrderWithPointConfig.java`](apis/order/CreateOrderWithPointConfig.java) |

Before production use, persist the idempotency key and handle typed exceptions. See [request options](../../../../../../guides/request-options.md) and [error handling](../../../../../../guides/error-handling.md).

## Other supported examples

| Goal | API | Examples |
|---|---|---|
| Create a hosted checkout | Preferences API | [`apis/preference/`](apis/preference/) |
| Create a subscription plan | Preapproval Plan API | [`apis/preapprovalplan/`](apis/preapprovalplan/) |
| Create a payment | Payments API | [`apis/payment/`](apis/payment/) |
| Search chargebacks | Chargebacks API | [`apis/chargeback/`](apis/chargeback/) |
| Refund a disbursement | Advanced Payments API | [`apis/disbursementrefund/`](apis/disbursementrefund/) |

Use test credentials and test data, replace every placeholder, and confirm fields in the [API reference](https://www.mercadopago.com/developers/en/reference). Never commit credentials or use production card data in examples.
