# Changelog Order Response

## Comparação de Estruturas

### JSON de Exemplo
```json
{
  "id": "ORD01HRYFWNYRE1MR1E60MW3X0T2P",
  "type": "online",
  "processing_mode": "automatic",
  "external_reference": "ext_ref_1234",
  "description": "some description",
  "marketplace": "NONE",
  "marketplace_fee": "10.00",
  "total_amount": "1000.00",
  "total_paid_amount": "1000.00",
  "country_code": "BRA",
  "user_id": "1245621468",
  "status": "processed",
  "status_detail": "accredited",
  "capture_mode": "automatic_async",
  "created_date": "2024-11-21T14:19:14.727Z",
  "last_updated_date": "2024-11-21T14:19:18.489Z",
  "integration_data": {
    "application_id": "130106526144588"
  },
  "transactions": {
    "payments": [
      {
        "id": "PAY01JD7HETD7WX4W31VA60R1KC8E",
        "amount": "1000.00",
        "paid_amount": "1000.00",
        "expiration_time": "P1D",
        "date_of_expiration": "2024-01-01T00:00:00.000-03:00",
        "reference_id": "22dvqmsf4yc",
        "status": "processed",
        "status_detail": "accredited",
        "payment_method": {
          "id": "master",
          "type": "credit_card",
          "token": "677859ef5f18ea7e3a87c41d02c3fbe3",
          "statement_descriptor": "LOJA X",
          "installments": 1
        }
      }
    ]
  },
  "items":[
    {
      "external_code": "item_external_code",
      "category_id": "category_id",
      "title": "Some item title",
      "description": "Some item description",
      "unit_price": "1000.00",
      "type": "item type",
      "picture_url": "https://mysite.com/img/item.jpg",
      "quantity": 1
    }
  ]
}
```

### Análise das Classes Existentes

#### Classe Order
- Propriedades presentes e corretas:
  - id
  - type
  - processingMode
  - externalReference
  - description
  - marketplace
  - marketplaceFee
  - totalAmount
  - totalPaidAmount
  - countryCode
  - userId
  - status
  - statusDetail
  - captureMode
  - createdDate
  - lastUpdatedDate
  - integrationData
  - items
  - transactions

- Propriedades obsoletas (presentes na classe mas não no JSON):
  - checkoutAvailableAt
  - expirationTime
  - clientToken
  - config
  - payer
  - additionalInfo

#### Classe OrderPayment
- Propriedades presentes e corretas:
  - id
  - amount
  - paidAmount
  - expirationTime
  - dateOfExpiration
  - referenceId
  - status
  - statusDetail
  - paymentMethod

- Propriedades obsoletas:
  - attemptNumber
  - attempts
  - automaticPayments
  - storedCredential
  - subscriptionData

#### Classe OrderPaymentMethod
- Propriedades presentes e corretas:
  - id
  - type
  - token
  - statementDescriptor
  - installments

- Propriedades obsoletas:
  - cardId
  - barcodeContent
  - reference
  - verificationCode
  - financialInstitution
  - qrCode
  - qrCodeBase64
  - digitableLine
  - ticketUrl

#### Classe OrderItem
- Propriedades presentes e corretas:
  - title
  - unitPrice
  - quantity
  - categoryId
  - type
  - description
  - pictureUrl
  - externalCode

- Propriedades obsoletas:
  - id
  - warranty
  - eventDate

## Conclusão

As classes existentes já possuem a maioria das propriedades necessárias para representar o JSON de exemplo. Não é necessário adicionar novas propriedades, apenas documentar as propriedades obsoletas que não são mais utilizadas no contrato atual.

As propriedades obsoletas foram mantidas para manter a compatibilidade com versões anteriores do SDK, seguindo as boas práticas de desenvolvimento. 