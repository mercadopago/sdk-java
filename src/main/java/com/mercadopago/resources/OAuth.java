package com.mercadopago.resources;

import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPBase;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.core.annotations.rest.POST;
import com.mercadopago.exceptions.MPException;

public class OAuth extends MPBase {

    private String clientSecret = null;
    private String grantType = null;
    private String code = null;
    private String redirectUri = null;
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

    public static void setClientSecret(String clientSecret) {
        clientSecret = clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public static void setGrantType(String grantType) {
        grantType = grantType;
    }

    public String getCode() {
        return code;
    }

    public static void setCode(String code) {
        code = code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public static void setRedirectUri(String redirectUri) {
        redirectUri = redirectUri;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public static void setRefreshToken(String refreshToken) {
        refreshToken = refreshToken;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getLiveMode() {
        return liveMode;
    }

    public void setLiveMode(String liveMode) {
        this.liveMode = liveMode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public static String getAuthorizationURL(String appId, String redirectUri) throws MPException {
        return "https://auth.mercadopago.com.".concat(User.find().getCountryId().toLowerCase()).concat("/authorization?client_id=").concat(appId).concat("&response_type=code&platform_id=mp&redirect_uri=").concat(redirectUri);
    }


    public static OAuth getOAuthCredentials(String authorizationCode, String redirectUri) throws MPException {
        OAuth oAuth = new OAuth();
        oAuth.clientSecret = MercadoPago.SDK.getAccessToken();
        oAuth.grantType = "authorization_code";
        oAuth.code = authorizationCode;
        oAuth.redirectUri = redirectUri;
        return save(oAuth);
    }

    public static OAuth refreshOAuthCredentials(String refreshToken) throws MPException {
        OAuth oAuth = new OAuth();
        oAuth.clientSecret = MercadoPago.SDK.getAccessToken();
        oAuth.grantType = "refresh_token";
        oAuth.refreshToken = refreshToken;

        return save(oAuth);
    }

    @POST(path = "/oauth/token")
    public static OAuth save(OAuth oAuth) throws MPException {
        return processMethod(OAuth.class, oAuth, "save", null, WITHOUT_CACHE, MPRequestOptions.createDefault());
    }

}
