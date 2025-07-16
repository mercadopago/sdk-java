package com.mercadopago.client.order;


import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
public class AdditionalInfoRequest {

    private String payerAuthenticationType;


    private String payerRegistrationDate;


    private Boolean payerIsPrimeUser;


    private Boolean payerIsFirstPurchaseOnLine;


    private String payerLastPurchase;


    private Boolean shipmentExpress;


    private Boolean shipmentLocalPickup;


    private String platformShipmentDeliveryPromise;


    private String platformShipmentDropShipping;

    private String platformShipmentSafety;

    private String platformShipmentTrackingCode;

    private String platformShipmentTrackingStatus;


    private Boolean platformShipmentWithdrawn;


    private String platformSellerId;


    private String platformSellerName;


    private String platformSellerEmail;


    private String platformSellerStatus;


    private String platformSellerReferralUrl;


    private String platformSellerRegistrationDate;


    private String platformSellerHiredPlan;


    private String platformSellerBusinessType;


    private String platformSellerAddressZipCode;


    private String platformSellerAddressStreetName;


    private String platformSellerAddressStreetNumber;


    private String platformSellerAddressCity;


    private String platformSellerAddressState;


    private String platformSellerAddressComplement;


    private String platformSellerAddressCountry;


    private String platformSellerIdentificationType;


    private String platformSellerIdentificationNumber;


    private String platformSellerPhoneAreaCode;


    private String platformSellerPhoneNumber;


    private String platformAuthentication;


    private List<TravelPassengerRequest> travelPassengers;

    private List<TravelRouteRequest> travelRoutes;
}
