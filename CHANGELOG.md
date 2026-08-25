# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [3.6.0] - 2024-01-15

### Added
- Comprehensive Javadoc documentation for all API client classes
- Detailed class-level documentation with usage examples for:
  - `MercadoPagoConfig` - Global SDK configuration with thread-safety improvements
  - `MercadoPagoClient` - Abstract base client with enhanced header management
  - `AdvancedPaymentClient` - Marketplace split payments (disbursements)
  - `CardTokenClient` - PCI-compliant card tokenization
  - `ChargebackClient` - Chargeback dispute management
  - `CustomerClient` - Customer profile and saved cards management
  - `CustomerCardClient` - Customer card operations
  - `DisbursementRefundClient` - Refund operations for split payments
  - `IdentificationTypeClient` - Country-specific ID document types
  - `InvoiceClient` - Subscription invoice management
- Enhanced parameter documentation with `@see` references and API links
- Thread-safety documentation for `MercadoPagoConfig` volatile fields
- Three-level timeout resolution priority documentation (request options > request object > global config)
- Automatic idempotency key generation documentation for POST/PUT/PATCH methods
- Path parameter encoding utility method documentation
- Request DTO documentation for all client request objects:
  - `AdvancedPaymentCreateRequest`, `AdvancedPaymentUpdateRequest`
  - `CardTokenRequest`, `CardTokenRawRequest`
  - `CustomerRequest`, `CustomerAddressRequest`, `CustomerCardCreateRequest`
  - `DisbursementRefundCreateRequest`
  - Common DTOs: `AddressRequest`, `IdentificationRequest`, `PhoneRequest`, `SubMerchant`, `InvoicePeriod`, `SubscriptionSequence`

### Enhanced
- Improved code documentation coverage to ~100% for public APIs
- Added detailed method-level documentation explaining:
  - Parameter validation and constraints
  - Return value structure and nullability
  - Exception handling scenarios
  - API endpoint URLs and HTTP methods
  - Thread-safety guarantees
- Enhanced lazy initialization documentation for HTTP client singleton
- Added comprehensive documentation for authorization header management
- Improved logging configuration documentation
- Added proxy and retry handler configuration documentation

### Fixed
- Clarified access token resolution order (per-request > global config)
- Documented OAuth token endpoint special handling (skips Bearer header)
- Improved custom header merging behavior documentation
- Enhanced connection pooling and timeout configuration documentation

### Documentation
- Added API reference links to all client classes
- Added usage examples to all major client classes
- Enhanced parameter descriptions with examples (e.g., date formats, country codes)
- Improved cross-references between related classes using `@see` tags
- Added detailed descriptions of marketplace split payment flows
- Documented card tokenization best practices and PCI compliance
- Enhanced customer and saved cards workflow documentation

## [3.5.0] - Previous Release

### Features
- Core SDK functionality
- Advanced Payments API support
- Card Tokenization API
- Chargebacks API
- Customers API with saved cards
- Identification Types API
- Invoice/Subscription payments API
- Disbursement Refunds API
- Search and pagination support with `MPSearchRequest`
- Custom request options with `MPRequestOptions`
- Configurable HTTP client with connection pooling
- Proxy and retry handler support
- Idempotency key automatic generation
- Comprehensive error handling with `MPException` and `MPApiException`

[3.6.0]: https://github.com/mercadopago/sdk-java/compare/v3.5.0...v3.6.0
[3.5.0]: https://github.com/mercadopago/sdk-java/releases/tag/v3.5.0