# MP API Reference Audit Report — Java SDK
_Generado: 2026-04-24_

## Resumen
| Categoria | Cantidad |
|-----------|----------|
| Total metodos API | 48 |
| Con reference URL | 28 (58%) |
| Sin reference URL (gaps) | 20 (42%) |
| URLs no canonicas (dominio pais) | 16 |
| URLs canonicas (.com) | 10 |

---

## URLs no canonicas (dominio por pais)

Estas URLs usan dominios por pais (`.com.br`, `.com.ar`) en lugar del canonico `https://www.mercadopago.com/developers/en/reference/...`:

| Archivo | URL encontrada | Dominio |
|---------|---------------|---------|
| PaymentRefundClient.java | `https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds/post` | .com.br |
| PaymentRefundClient.java | `https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds_refund_id/get` | .com.br |
| PaymentRefundClient.java | `https://www.mercadopago.com.br/developers/en/reference/chargebacks/_payments_id_refunds/get` | .com.br |
| CustomerClient.java | `https://www.mercadopago.com.br/developers/en/reference/online-payments/checkout-api/customers/search-customer/get` | .com.br |
| CustomerCardClient.java | `https://www.mercadopago.com.br/developers/en/reference/cards/_customers_customer_id_cards/get` | .com.br |
| IdentificationTypeClient.java | `https://www.mercadopago.com.br/developers/en/reference/identification_types/_identification_types/get` | .com.br |
| MerchantOrderClient.java | `https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders/post` | .com.br |
| MerchantOrderClient.java | `https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders_id/get` | .com.br |
| MerchantOrderClient.java | `https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders_id/put` | .com.br |
| MerchantOrderClient.java | `https://www.mercadopago.com.br/developers/en/reference/merchant_orders/_merchant_orders_search/get` | .com.br |
| OauthClient.java | `https://www.mercadopago.com.br/developers/en/reference/oauth/_oauth_token/post` | .com.br |
| PreferenceClient.java | `https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences/post` | .com.br |
| PreferenceClient.java | `https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_id/get` | .com.br |
| PreferenceClient.java | `https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_id/put` | .com.br |
| PreferenceClient.java | `https://www.mercadopago.com.br/developers/en/reference/preferences/_checkout_preferences_search/get` | .com.br |
| PaymentMethodClient.java | `https://www.mercadopago.com.br/developers/en/reference/payment_methods/_payment_methods/get` | .com.br |
| PointClient.java | `https://www.mercadopago.com.ar/developers/en/reference/in-person-payments/point/orders/create-order/post` | .com.ar |
| PointClient.java | `https://www.mercadopago.com.ar/developers/en/reference/in-person-payments/point/orders/get-order/get` | .com.ar |
| PointClient.java | `https://www.mercadopago.com.ar/developers/en/reference/in-person-payments/point/orders/cancel-order/post` | .com.ar |
| PointClient.java | `https://www.mercadopago.com.ar/developers/en/reference/in-person-payments/point/terminals/get-terminals/get` | .com.ar |
| PointClient.java | `https://www.mercadopago.com.ar/developers/en/reference/in-person-payments/point/terminals/update-operation-mode/patch` | .com.ar |

---

## Links faltantes (gaps) — 20 metodos sin referencia

### PaymentClient.java (4 gaps de 5 metodos)
- [ ] `get(Long id)` — GET /v1/payments/{id}
- [ ] `cancel(Long id)` — PUT /v1/payments/{id} (status=cancelled)
- [ ] `capture(Long id, BigDecimal amount)` — PUT /v1/payments/{id} (capture=true)
- [ ] `search(MPSearchRequest request)` — GET /v1/payments/search

### CustomerCardClient.java (4 gaps de 4 metodos)
- [ ] `get(String customerId, String cardId)` — GET /v1/customers/{id}/cards/{cardId}
- [ ] `create(String customerId, CustomerCardCreateRequest request)` — POST /v1/customers/{id}/cards
- [ ] `delete(String customerId, String cardId)` — DELETE /v1/customers/{id}/cards/{cardId}
- [ ] `listAll(String customerId)` — GET /v1/customers/{id}/cards

### CardTokenClient.java (2 gaps de 2 metodos)
- [ ] `get(String id)` — GET /v1/card_tokens/{id}
- [ ] `create(CardTokenRequest request)` — POST /v1/card_tokens

### OrderClient.java (9 gaps de 10 metodos)
- [ ] `get(String id)` — GET /v1/orders/{id}
- [ ] `process(String id)` — POST /v1/orders/{id}/process
- [ ] `createTransaction(String orderId, OrderTransactionRequest request)` — POST /v1/orders/{id}/transactions
- [ ] `updateTransaction(String orderId, String transactionId, OrderPaymentRequest request)` — PUT /v1/orders/{id}/transactions/{transactionId}
- [ ] `cancel(String orderId)` — POST /v1/orders/{id}/cancel
- [ ] `capture(String orderId)` — POST /v1/orders/{id}/capture
- [ ] `deleteTransaction(String orderId, String transactionId)` — DELETE /v1/orders/{id}/transactions/{transactionId}
- [ ] `refund(String orderId, OrderRefundRequest request)` — POST /v1/orders/{id}/refund
- [ ] `search(MPSearchRequest request)` — GET /v1/orders

### PreapprovalClient.java (4 gaps de 4 metodos)
- [ ] `get(String id)` — GET /preapproval/{id}
- [ ] `create(PreapprovalCreateRequest request)` — POST /preapproval
- [ ] `update(String id, PreapprovalUpdateRequest request)` — PUT /preapproval/{id}
- [ ] `search(MPSearchRequest request)` — GET /preapproval/search

### Otros
- [ ] `CustomerClient.delete(String customerId)` — DELETE /v1/customers/{id}
- [ ] `OauthClient.getAuthorizationURL(...)` — URL builder (no endpoint directo)
- [ ] `PointClient.getPaymentIntentStatus(String id)` — GET /point/integration-api/payment-intents/{id}/events
- [ ] `UserClient.get()` — GET /users/me

---

## Cobertura por cliente

| Cliente | Metodos | Con URL | Sin URL | Cobertura |
|---------|---------|---------|---------|-----------|
| PaymentClient | 6 | 1 | 5* | 17% |
| PaymentRefundClient | 3 | 3 | 0 | 100% |
| CustomerClient | 5 | 4 | 1 | 80% |
| CustomerCardClient | 4 | 0 | 4 | 0% |
| CardTokenClient | 2 | 0 | 2 | 0% |
| IdentificationTypeClient | 1 | 1 | 0 | 100% |
| MerchantOrderClient | 4 | 4 | 0 | 100% |
| OauthClient | 3 | 2 | 1 | 67% |
| OrderClient | 10 | 1 | 9 | 10% |
| PointClient | 7 | 6 | 1 | 86% |
| PreapprovalClient | 4 | 0 | 4 | 0% |
| PreferenceClient | 4 | 4 | 0 | 100% |
| PaymentMethodClient | 1 | 1 | 0 | 100% |
| UserClient | 1 | 0 | 1 | 0% |

*PaymentClient: tiene 1 URL a nivel de clase pero solo cubre create()

---

## Lista de revision manual
- [ ] PaymentClient.java — 4 metodos sin referencia API
- [ ] CustomerCardClient.java — 4 metodos sin referencia API (0% cobertura)
- [ ] CardTokenClient.java — 2 metodos sin referencia API (0% cobertura)
- [ ] OrderClient.java — 9 metodos sin referencia API (10% cobertura)
- [ ] PreapprovalClient.java — 4 metodos sin referencia API (0% cobertura)
- [ ] CustomerClient.java — delete() sin referencia API
- [ ] OauthClient.java — getAuthorizationURL() sin referencia API
- [ ] PointClient.java — getPaymentIntentStatus() sin referencia API
- [ ] UserClient.java — get() sin referencia API
- [ ] 16 URLs usan dominio por pais (.com.br/.com.ar) en lugar del canonico (.com)
