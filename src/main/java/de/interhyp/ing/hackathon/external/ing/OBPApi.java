package de.interhyp.ing.hackathon.external.ing;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Notebook-11 on 12/05/2017.
 */
public class OBPApi extends DefaultApi10a {

    private String API_ENDPOINT = "http://bank-api-49.24hcoding.pl";
    private String REQUEST_TOKEN_RESOURCE = "/oauth/initiate";
    private String AUTHORIZE_URL = "/oauth_ing?oauth_token=%s";
    private String ACCESS_TOKEN_RESOURCE = "/oauth/token";

    @Override
    public String getAccessTokenEndpoint() {
        return API_ENDPOINT + ACCESS_TOKEN_RESOURCE;
    }

    @Override
    public String getRequestTokenEndpoint() {
        return API_ENDPOINT + REQUEST_TOKEN_RESOURCE;
    }

    @Override
    public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return String.format(API_ENDPOINT + AUTHORIZE_URL, requestToken.getToken());
    }

    private static class InstanceHolder {
        private static final OBPApi INSTANCE = new OBPApi();
    }

    public static OBPApi instance() {
        return InstanceHolder.INSTANCE;
    }
}
