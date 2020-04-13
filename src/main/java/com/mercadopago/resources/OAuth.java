package com.mercadopago.resources;

import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.exceptions.MPException;

public class OAuth extends MPBase {

    private String clientSecret;
    private String grantType;
    private String code;
    private String redirectUri;
    private String accessToken;
    private String refreshToken;
    private String publicKey;
    private String liveMode;
    private String userId;
    private String tokenType;
    private String expiresIn;
    private String scope;


    public String getClientSecret() {
        return clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getCode() {
        return code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }


    public String getAccessToken() {
        return accessToken;
    }


    public String getRefreshToken() {
        return refreshToken;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getLiveMode() {
        return liveMode;
    }

    public String getUserId() {
        return userId;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public static String getAuthorizationURL(String appId, String redirectUri) throws MPException {
        Users users = new Users();
        return "https://auth.mercadopago.com.".concat(users.getCountry().getCountryId().toLowerCase()).concat("/authorization?client_id=").concat(appId).concat("&response_type=code&platform_id=mp&redirect_uri=").concat(redirectUri);
    }


    public OAuth getOAuthCredentials(String authorizationCode, String redirectUri) throws MPException {
        this.clientSecret = MercadoPago.SDK.getAccessToken();
        this.grantType = "authorization_code";
        this.code = authorizationCode;
        this.redirectUri = redirectUri;

        return save(MPRequestOptions.createDefault());
    }

    public OAuth refreshOAuthCredentials(String refreshToken) throws MPException {
        this.clientSecret = MercadoPago.SDK.getAccessToken();
        this.grantType = "refresh_token";
        this.refreshToken = refreshToken;

        return save(MPRequestOptions.createDefault());
    }

    @POST(path = "/oauth/token")
    public OAuth save(MPRequestOptions requestOptions) throws MPException {
        if (requestOptions == null) {
            requestOptions = MPRequestOptions.createDefault();
        }
        addTrackingHeaders(requestOptions);
        return processMethod("save", WITHOUT_CACHE, requestOptions);
    }
}
