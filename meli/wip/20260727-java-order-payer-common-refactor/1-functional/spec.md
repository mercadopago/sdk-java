# Functional Specification: java-order-payer-common-refactor

**Feature**: java-order-payer-common-refactor
**Status**: approved
**Mode**: express (brownfield, pure refactor)

## Problem Statement

`OrderPayerRequest` en el sdk-java (PR #386) mezcla capas y duplica estructuras que ya existen en el paquete `client/common`:

1. Usa `resources/common/Identification` y `resources/common/Phone` (clases de la capa de **respuesta/deserialización**) en la capa de **request**, mezclando responsabilidades.
2. Define su propia clase `OrderPayerAddressRequest` que **duplica** `client/common/AddressRequest` pero con menos campos (le faltan `complement` y `floor`).
3. Ninguna variante de `AddressRequest` tiene el campo `country`, que la Orders API acepta.

Esto genera inconsistencia con el resto del SDK, que ya centraliza estas estructuras en `client/common`, y limita los campos de dirección disponibles para integradores de Orders.

## Objectives

- Unificar `OrderPayerRequest` para que use exclusivamente las clases de `client/common` (capa de request).
- Eliminar la duplicación de `OrderPayerAddressRequest`.
- Agregar el campo `country` a `client/common/AddressRequest` para cobertura completa de la Orders API.
- Garantizar cero cambios en el JSON serializado y cero regresiones en tests existentes.

## Success Metrics

- El JSON generado por `OrderPayerRequest` es **byte-idéntico** al actual para los campos existentes.
- Todos los tests existentes del sdk-java pasan sin modificación de assertions.
- `OrderPayerAddressRequest.java` queda eliminado (sin referencias huérfanas).
- `mvn compile` y `mvn test` pasan.

## Scope

### In Scope
- Modificar `client/common/AddressRequest.java` → agregar campo `country`.
- Modificar `OrderPayerRequest.java` → usar `IdentificationRequest`, `PhoneRequest`, `AddressRequest` de `client/common`.
- Eliminar `OrderPayerAddressRequest.java`.
- Test de serialización que valide el JSON de `OrderPayerRequest` con la cadena tipada completa.

### Out of Scope
- Cambios en la capa de respuesta (`resources/common/*`) — se mantienen intactas.
- Refactor de `MerchantOrderPayerRequest` u otros recursos.
- Cambios en Go, Node.js, PHP, Python o Ruby (analizados por separado — Go es duplicación idiomática, el resto no aplica).

## User Stories

### US-1: Integrador usa dirección de payer con campos completos
**Como** integrador del sdk-java construyendo un Order,
**quiero** que `OrderPayerRequest.address` acepte los mismos campos que el resto del SDK (incluyendo `complement`, `floor`, `country`),
**para** no verme limitado por una clase de dirección con menos campos.

**Acceptance Criteria**:
- `OrderPayerRequest.builder().address(AddressRequest.builder()...)` compila y acepta `country`, `complement`, `floor`.
- El JSON serializado incluye los campos seteados y omite los `null`.

### US-2: Consistencia de capas en el SDK
**Como** mantenedor del sdk-java,
**quiero** que `OrderPayerRequest` use las clases de request de `client/common`,
**para** eliminar la mezcla entre capa de respuesta y de request.

**Acceptance Criteria**:
- `OrderPayerRequest` importa desde `client/common`, no desde `resources/common`.
- `OrderPayerAddressRequest` ya no existe en el código base.
- Ningún test existente se rompe.

## Dependencies

- Ninguna externa. Refactor interno del sdk-java.

## Risks & Edge Cases

- **Riesgo de serialización**: mitigado — el Serializer usa `FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES` y los nombres de campo coinciden exactamente entre las clases viejas y nuevas, produciendo JSON idéntico.
- **Riesgo de builder roto**: mitigado — el único test que construye `OrderPayerRequest` usa solo `.email()`; no referencia el builder de address.

## E2E Scenarios (LTP)

### E2E-1: Serialización de OrderPayerRequest con cadena tipada completa
Verificar que un `OrderPayerRequest` construido con `IdentificationRequest`, `PhoneRequest` y `AddressRequest` (de `client/common`) produce el JSON snake_case esperado, con `country` incluido y campos `null` omitidos.
