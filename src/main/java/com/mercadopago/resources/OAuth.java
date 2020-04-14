package com.mercadopago.resources;

import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.exceptions.MPException;

public class OAuth extends MPBase {

    private static String clientSecret;
    private static String grantType;
    private static String code;
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
        User user = new User();
        return "https://auth.mercadopago.com.".concat(user.find().getCountryId().toLowerCase()).concat("/authorization?client_id=").concat(appId).concat("&response_type=code&platform_id=mp&redirect_uri=").concat(redirectUri);
    }


    public static OAuth getOAuthCredentials(String authorizationCode, String redirectUri) throws MPException {
        clientSecret = MercadoPago.SDK.getAccessToken();
        grantType = "authorization_code";
        code = authorizationCode;
        redirectUri = redirectUri;
        return save();
    }

    public static OAuth refreshOAuthCredentials(String refreshToken) throws MPException {
        clientSecret = MercadoPago.SDK.getAccessToken();
        grantType = "refresh_token";
        refreshToken = refreshToken;

        return save();
    }

    @POST(path = "/oauth/token")
    public static OAuth save() throws MPException {
        return processMethod(OAuth.class,"save", WITHOUT_CACHE, MPRequestOptions.createDefault());

    }
}
