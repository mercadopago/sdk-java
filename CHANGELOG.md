# Changelog

All notable changes to this project will be documented in this file.

This project follows [Keep a Changelog](https://keepachangelog.com/en/1.0.0/)
and [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [3.6.0] - 2026-08-25

### Added
- **COF network data support**: new fields on `PaymentNetworkDataRequest`/`PaymentNetworkData`, `PaymentPointOfInteractionRequest`/`PaymentPointOfInteraction`, and `OrderStoredCredential`

## [3.5.0] - 2026-08-11

### Added
- **Automatic Payments example**: two-step recurring flow ([#386](https://github.com/mercadopago/sdk-java/pull/386))
- **Order model**: reuse `client/common` types in `OrderPayerRequest`, add `country` to `AddressRequest`, restore `currency` field position in `OrderCreateRequest` ([#386](https://github.com/mercadopago/sdk-java/pull/386))

### Fixed
- **Stored credential**: rename `prevTransactionRef` to `previousTransactionReference` ([#386](https://github.com/mercadopago/sdk-java/pull/386))
- Deprecated `Matchers` replaced with `ArgumentMatchers` in unit tests ([#386](https://github.com/mercadopago/sdk-java/pull/386))

### CI
- Standardize CI/CD workflows ([#399](https://github.com/mercadopago/sdk-java/pull/399))
- Add mock-based unit tests ([#399](https://github.com/mercadopago/sdk-java/pull/399))
- Skip lombok-maven-plugin delombok in CI to support Java 17 and 21 ([#399](https://github.com/mercadopago/sdk-java/pull/399))

## [3.4.0] - 2026-08-04

### Added

- **SDK ergonomics**: typed exceptions, configurable retry, and auto-pagination ([#392](https://github.com/mercadopago/sdk-java/pull/392))
  - `MPApiException` now has 12 specific subtypes per HTTP status code (`400`→`MPBadRequestException`, `429`→`MPRateLimitException`, etc.)
  - `MPRequestOptions` gains optional `maxRetries`, `retryOn`, `initialDelayMs`, `maxDelayMs` and `onRetry` callback
  - New `MPAutoPagingIterable<T>` for lazy auto-pagination on `PaymentClient`, `CustomerClient` and `PreapprovalClient`
- **Missing API methods** — `DisbursementRefundClient.list()`, `AdvancedPaymentClient.update()`, `CustomerCard.update()`, `PaymentClient.update()` ([#391](https://github.com/mercadopago/sdk-java/pull/391))
- **CREDENTIAL_ON_FILE messaging fields** on `Payment` types ([#388](https://github.com/mercadopago/sdk-java/pull/388)): `firstTransaction`, `storage`, `transactionInitiator`, `reference`
- **Example**: add create preference example ([#310](https://github.com/mercadopago/sdk-java/pull/310))

### Fixed

- Webhook `toleranceSeconds` unit mismatch — `ts` header value was being compared in seconds against a millisecond clock ([#393](https://github.com/mercadopago/sdk-java/pull/393))
- `constantTimeEquals` `RangeError` on multibyte v1 hash ([#393](https://github.com/mercadopago/sdk-java/pull/393))

### Dependencies

- Bump `httpclient5` to `5.6.3` ([#389](https://github.com/mercadopago/sdk-java/pull/389))
- Bump `actions/setup-java` to `v5.7.0` ([#390](https://github.com/mercadopago/sdk-java/pull/390))
- Bump `actions/checkout` to `v7.0.1` ([#387](https://github.com/mercadopago/sdk-java/pull/387))

## [3.3.0] - 2026-06-30

### Added

- **Checkout PRO orders**: extended `OrderClient` with full Checkout PRO support via the Orders API.
  New request types enable configuring redirect URLs, auto-return behavior, availability windows,
  user-type restrictions, tracking pixels (Google Ads / Facebook Ads), shipment details, and
  interest-free installment rules.

- **`Order.checkoutUrl`**: new field on the `Order` resource returning the redirect URL generated
  at order creation to send the buyer into the Checkout PRO flow.

- **`OrderOnlineConfig` fields**: `autoReturn` and `availableFrom` added to both the request and
  resource models.

- **`OrderConfigRequest` fields**: `statementDescriptor` and `defaultPaymentDueDate` for
  customising the card statement text and offline payment expiry.

- **`OrderShipmentRequest` fields**: `mode`, `localPickup`, `cost`, `freeShipping`, `freeMethods`,
  and `address` — plus `@Builder` support on `OrderShipmentRequest`, `OrderReceiverAddressRequest`,
  and `OrderItemRequest`.

- **`OrderTrackRequest`**: new request class for conversion-tracking pixels (Google Ads /
  Facebook Ads).

- **`OrderFreeShippingMethodRequest`**: new request class for free shipping method IDs.

- **`OrderInstallmentsRequest` / `OrderInstallmentsInterestFreeRequest`**: new request classes
  replacing `OrderInstallments` in `OrderPaymentMethodConfig` — enables "range" and "list"
  interest-free installment rules.

- **`OrderRetriesConfig`**: new class exposing the payment-retry `allowed` flag returned in order
  responses.

- **`CreateOrderCheckoutPro` example**: reference implementation showing a full Checkout PRO
  order creation flow.

### Fixed

- **`WebhookSignatureValidator`**: `data.id` is now lowercased before being included in the HMAC
  manifest, matching the behaviour documented by Mercado Pago and preventing signature
  verification failures caused by mixed-case IDs.

## [3.2.0] - 2026-05-27

### Added

- **PreApprovalPlan**: subscription plan template management — create, get, update, search
  (`POST/GET/PUT /preapproval_plan`). Enables reusable billing templates for subscriptions.

- **AdvancedPayment**: marketplace split-payment management — create, get, search, capture, cancel,
  updateReleaseDate (`POST/GET/PUT /v1/advanced_payments`). Enables distributing a single
  payment among multiple sellers.

- **Invoice**: retrieval and search of subscription billing invoices — get, search
  (`GET /authorized_payments`). Enables monitoring of billing cycles generated by preapprovals.

- **DisbursementRefund**: refunds for split-payment disbursements — createAll, create
  (`POST /v1/advanced_payments/{id}/refunds`). Enables partial and full refunds of individual
  disbursements within an advanced payment.

- **Chargeback**: read-only access to payment dispute records — get, search
  (`GET /v1/chargebacks`). Enables monitoring and response to cardholder disputes.
