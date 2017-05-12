package de.interhyp.ing.hackathon.external.ing

import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.model.OAuth1RequestToken
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Response
import com.github.scribejava.core.model.Verb
import com.github.scribejava.core.oauth.OAuth10aService
import org.springframework.stereotype.Service

/**
 * Created by Notebook-11 on 12/05/2017.
 */
@Service
class IngApiHandler {

    public String baseUrl = System.getenv("baseUrl");
    public String consumerKey = System.getenv("consumerKey");
    public String consumerSecret = System.getenv("consumerSecret");
    public String callbackUrl = "http://127.0.0.1:8080/ing/api/callback/qwertzu/";
    public String baseApi = "/obp/v2.1.0"

    OAuth10aService service;
    //TODO: some session local
    OAuth1RequestToken requestToken



    void init() throws IOException {
        OBPApi oauthVerifier = new OBPApi();
//Create oauth service
        service = new ServiceBuilder()
            .apiKey(consumerKey)
            .apiSecret(consumerSecret)
            .callback(callbackUrl)
            .build(OBPApi.instance());

//Obtain the Request Token
        requestToken = service.getRequestToken();

//Go and authorize APP here:
        String authorizationUrl = service.getAuthorizationUrl(requestToken);
        println "user should login here: $authorizationUrl"

        //println 'http://bank-api-49.24hcoding.pl/obp/v2.1.0/banks'.toURL().text
//Trade the Request Token and Verfier for the Access Token


    }

    void handleIt(String id,
                  String token) {
        //debug here and manually set TOken for now...
        if (service == null) {
            service = new ServiceBuilder()
                .apiKey(consumerKey)
                .apiSecret(consumerSecret)
                .callback(callbackUrl)
                .build(OBPApi.instance());
        }
        println "might modify here... " + token
        println "might modify here... " + token
        println "might modify here... " + token
        final OAuth1AccessToken accessToken = service.getAccessToken(requestToken, token);
        println "accessToken .. " + accessToken
//Now to access a protected resource...
        final OAuthRequest request = new OAuthRequest(Verb.GET, baseUrl + "/obp/v2.1.0/users/current", service);
        service.signRequest(accessToken, request);
        Response res = request.send();
        println res.body
        def user = doCallService(service, accessToken, request, "/users/current")
        println user

        def accounts = doCallService(service, accessToken, request, "/my/accounts")
        println accounts
        def slurper = new groovy.json.JsonSlurper()
        def result = slurper.parseText(accounts);

        //println doCallService(service, accessToken, request, "/my/banks")
        String bankId = result[0].bank_id  //or ing
        String accountId = result[0].id
        //String accountId = "a2fd161d-de1e-4518-b1c8-a0481e5b529d"
        println doCallService(service, accessToken, request, "/my/banks/$bankId/accounts/$accountId/transactions")

        // /my/banks/BANK_ID/accounts/ACCOUNT_ID/transactions
    }

    private String doCallService(OAuth10aService service, OAuth1AccessToken accessToken, OAuthRequest request, String url) {
        final OAuthRequest request2 = new OAuthRequest(Verb.GET, baseUrl + baseApi + url, service);
        service.signRequest(accessToken, request2);
        Response res2 = request2.send();
        res2.body

    }
}
