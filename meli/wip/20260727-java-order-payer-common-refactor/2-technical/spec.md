# Technical Specification: java-order-payer-common-refactor

**Feature**: java-order-payer-common-refactor
**Status**: approved
**Mode**: express (brownfield, pure-logic refactor)
**Platform**: backend (Java / Maven / Lombok / Gson)

## Executive Summary

Refactor interno del sdk-java que unifica `OrderPayerRequest` sobre las clases de request compartidas en `client/common`, elimina la duplicación de `OrderPayerAddressRequest`, y agrega el campo `country` a `client/common/AddressRequest`. Sin cambios de comportamiento en la serialización.

## Architecture

```
                 ┌────────────────────────┐
                 │   OrderPayerRequest     │
                 └───────────┬────────────┘
                             │ usa (client/common)
          ┌──────────────────┼──────────────────┐
          ▼                  ▼                   ▼
 ┌──────────────────┐ ┌──────────────┐ ┌──────────────────┐
 │IdentificationReq │ │ PhoneRequest │ │  AddressRequest  │
 │  (client/common) │ │(client/common)│ │  (+ country)     │
 └──────────────────┘ └──────────────┘ └──────────────────┘

 ELIMINADO: OrderPayerAddressRequest (client/order)
 SIN CAMBIOS: resources/common/Identification, Phone (capa response)
```

## Design Decisions

### DD-1: Reutilizar client/common en lugar de crear/duplicar clases

**Selected**: Usar `IdentificationRequest`, `PhoneRequest`, `AddressRequest` de `client/common`.

**Options Considered**:
- **Opción A (seleccionada)**: Reutilizar las clases existentes de `client/common`. Cero clases nuevas, elimina 1 duplicada.
- **Opción B**: Mantener `OrderPayerAddressRequest` y solo agregarle campos. Perpetúa la duplicación.
- **Opción C**: Crear un nuevo paquete `client/order/common`. Introduce una tercera capa innecesaria.

**Trade-offs Accepted**: `AddressRequest` usa `@SuperBuilder` (no `@Builder`); el código consumidor usa `AddressRequest.builder()` igual, sin diferencia práctica. Aceptamos alinear el builder style del paquete common.

**Rationale**: `client/common` ya es el hogar canónico de estas estructuras de request en el SDK (usado por payment, preference, customer). Reutilizar mantiene consistencia y reduce superficie de mantenimiento.

### DD-2: Serialización sin cambios

**Selected**: Confiar en `FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES` del Serializer existente.

**Options Considered**:
- **Opción A (seleccionada)**: No tocar el Serializer. Los nombres de campo coinciden → JSON idéntico.
- **Opción B**: Agregar anotaciones `@SerializedName`. Innecesario, agrega ruido.

**Trade-offs Accepted**: Ninguno — los nombres de campo entre clases viejas y nuevas son idénticos (`type`, `number`, `areaCode`, `streetName`, etc.).

**Rationale**: El JSON de salida debe ser byte-idéntico para no romper integraciones ni tests.

## Data Model

### Campo agregado a `client/common/AddressRequest`

| Campo | Tipo | JSON | Estado |
|-------|------|------|--------|
| `country` | `String` | `country` | **NUEVO** |

Campos existentes (sin cambio): `zipCode`, `streetName`, `streetNumber`, `neighborhood`, `city`, `state`, `complement`, `floor`.

### Cambios de tipo en `OrderPayerRequest`

| Campo | Tipo ANTES | Tipo DESPUÉS |
|-------|-----------|--------------|
| `identification` | `resources.common.Identification` | `client.common.IdentificationRequest` |
| `phone` | `resources.common.Phone` | `client.common.PhoneRequest` |
| `address` | `OrderPayerAddressRequest` | `client.common.AddressRequest` |

## Testing Strategy

### Unit Tests
- Nuevo test `OrderPayerRequestSerializationTest` que valida:
  - `OrderPayerRequest` con `IdentificationRequest`, `PhoneRequest`, `AddressRequest` (con `country`) serializa a snake_case correcto.
  - Campos `null` se omiten.
  - Campos existentes producen las mismas keys JSON que antes.
- Los tests existentes (`OrderClientTest`, `OrderCreateRequestSerializationTest`) deben pasar sin modificación.

### Integration Tests
- `OrderClientIT` existente cubre el flujo end-to-end; no requiere cambios.

## Security

- N/A — refactor interno sin manejo de datos sensibles nuevos, sin secrets, sin cambios de auth.

## Performance

- Sin impacto. Cambio de tipos en tiempo de compilación.

## Deployment

- Parte del PR #386 existente. Sin migración, sin cambios de infraestructura.
