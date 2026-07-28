# Feature Metadata

**Feature Name**: java-order-payer-common-refactor
**Feature ID**: feat-20260727-java-order-payer-common-refactor
**Feature UUID**: a3f7c2d1-8e4b-4a9f-b6c5-2d1e0f3a7b8c
**Mode**: express
**Created**: 2026-07-27
**Status**: in_progress
**Phase**: 4-implementation
**Execution Strategy**: batched

## Approvals

functional:
  status: approved
  approved_by: Diego Gerardo Barajas Suarez
  approved_at: 2026-07-28T00:48:32Z
technical:
  status: approved
  approved_by: Diego Gerardo Barajas Suarez
  approved_at: 2026-07-28T00:48:32Z

## Project Context

**Application**: sdk-java
**Language**: java
**Build Tool**: maven
**Platform**: backend
**Project Mode**: brownfield
**Execution Mode**: express
**Project Type**: production
**Spec Language**: es

## Feature Description

Refactorizar `OrderPayerRequest` en sdk-java (PR #386) para:
1. Reemplazar `OrderPayerAddressRequest` por `client/common/AddressRequest` + agregar campo `country`
2. Reemplazar `resources/common/Identification` y `Phone` por `client/common/IdentificationRequest` y `PhoneRequest`
3. Eliminar `OrderPayerAddressRequest.java` si queda sin uso
4. Asegurar que ningún test existente se rompa

## LTP E2E

ltp_enabled: true
ltp_scope: serialization specs for OrderPayerRequest JSON output

## Relationship Check

relationship_check:
  performed_at: 2026-07-27T00:00:00Z
  tier: NONE
  decision: skipped_greenfield
  candidates: []
  candidates_weak: []

relates_to: []

## Testing Config

test_coverage_target: 80
update_imports_only: true
