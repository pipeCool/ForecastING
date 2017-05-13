package de.interhyp.ing.hackathon.external.ing

import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.model.OAuth1RequestToken
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Response
import com.github.scribejava.core.model.Verb
import com.github.scribejava.core.oauth.OAuth10aService
import de.interhyp.ing.hackathon.domain.BankAccount
import de.interhyp.ing.hackathon.domain.Transaction
import de.interhyp.ing.hackathon.domain.User
import de.interhyp.ing.hackathon.domain.enumeration.TransactionStatus
import de.interhyp.ing.hackathon.repository.AuthorityRepository
import de.interhyp.ing.hackathon.repository.BankAccountRepository
import de.interhyp.ing.hackathon.repository.TransactionRepository
import de.interhyp.ing.hackathon.repository.UserRepository
import groovy.json.JsonSlurper
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

import java.time.Instant
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

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


    String init() throws IOException {
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
        authorizationUrl

    }

    void handleIt(String id,
                  String token, UserRepository userRepository, PasswordEncoder passwordEncoder,
                  AuthorityRepository authorityRepository,
                  BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository) {

        if (service == null) {
            service = new ServiceBuilder()
                .apiKey(consumerKey)
                .apiSecret(consumerSecret)
                .callback(callbackUrl)
                .build(OBPApi.instance());
        }
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

        def uresult = new JsonSlurper().parseText(user);
        //{"user_id":"eef62e3a-8290-4774-bc5f-be74972ae052","email":"maksymilian.markowski@example.com","provider_id":null,"provider":"http://127.0.0.1:8080","username":"Maksymilian Markowski"}
        def email = uresult.email
        User usr;
        Optional<User> usrResult = userRepository.findOneByEmail(email)
        if (usrResult.present) {
            usr = usrResult.get()
        } else {
            usr = new User();
            usr.setEmail(email)
            usr.setFirstName(getFirstName(email))
            usr.setFirstName(getLastName(email))
            usr.setLogin(email)
            usr.setActivated(true)
            usr.setPassword(passwordEncoder.encode(email));
            usr = userRepository.save(usr)
        }
        def accounts = doCallService(service, accessToken, request, "/my/accounts")
        println accounts

        def result = new JsonSlurper().parseText(accounts);
        result.each { account ->
            //println doCallService(service, accessToken, request, "/my/banks")
            String bankId = account.bank_id  //or ing
            String accountId = account.id
            // detail http://bank-api-49.24hcoding.pl/obp/v2.0.0/my/banks/ing/accounts/03175604-d6be-6254-6dac-9b2d750f481a/account
            def accountDetail = doCallServiceWHeader(service, accessToken, request, "/my/banks/$bankId/accounts/$accountId/account", 0)
            println accountDetail;
            def adJson = new JsonSlurper().parseText(accountDetail);
            def iban = adJson.IBAN
            BankAccount ba;
            Optional<BankAccount> oBA = bankAccountRepository.findByIban(iban);
            if (oBA.isPresent()) {
                ba = oBA.get();
            } else {
                ba = new BankAccount();
                ba.setIban(iban);
                ba.setBank(adJson.bank_id)
                ba.setUser(usr);
                ba = bankAccountRepository.save(ba)
            }

            getSomeTransactions(bankId, accountId, ba, transactionRepository, request, accessToken)
        }
        println "num results:" + result.size()
        // /my/banks/BANK_ID/accounts/ACCOUNT_ID/transactions


    }

    void getSomeTransactions(String bankId, String accountId, BankAccount ba, TransactionRepository transactionRepository,
                             OAuthRequest request,
                             OAuth1AccessToken accessToken) {

        def trx = doCallServiceWHeader(service, accessToken, request, "/my/banks/$bankId/accounts/$accountId/transactions", 0)

        def trxResults = new JsonSlurper().parseText(trx);
        trxResults.transactions.each {
            Optional<Transaction> oTrx = transactionRepository.findByTrxId(it.id);
            if (!oTrx.isPresent()) {
                Transaction toSave = new Transaction()
                toSave.setBankaccount(ba)
                toSave.setTrxId(it.id)
                def completedTime1 = ZonedDateTime.parse(it.details.completed)
                toSave.setDate(completedTime1);
                def amount1 = Double.parseDouble(it.details.value.amount)
                toSave.setAmount(amount1)
                toSave.setStatus(TransactionStatus.FIXED)
                transactionRepository.save(toSave)
                //simple predict now: copy last 30Days + rand Factor
                def between = ChronoUnit.DAYS.between(Instant.now(), completedTime1)c
                if (between < 0 && between > -30) {
                    //Clone!

                    Transaction toSave2 = new Transaction()
                    toSave2.setBankaccount(ba)
                    toSave2.setTrxId("s" + it.id)
                    def completedTime = ZonedDateTime.parse(it.details.completed)
                    toSave2.setDate(completedTime.plusDays(30L));
                    def amount = Double.parseDouble(it.details.value.amount)
                    toSave2.setAmount(amount)
                    toSave2.setStatus(TransactionStatus.PREDICTED)
                    transactionRepository.save(toSave2)
                }
            }
        }
        println doCallService(service, accessToken, request, "/banks/$bankId/accounts/$accountId/owner/transactions")
    }

    String getLastName(String email) {
        email.split("\\@").first().split("\\.").last().capitalize()
    }

    String getFirstName(String email) {
        email.split("\\.").first().capitalize()
    }

    private String doCallService(OAuth10aService service, OAuth1AccessToken accessToken, OAuthRequest request, String url) {
        final OAuthRequest request2 = new OAuthRequest(Verb.GET, baseUrl + baseApi + url, service);
        service.signRequest(accessToken, request2);
        Response res2 = request2.send();
        res2.body

    }

    private String doCallServiceWHeader(OAuth10aService service, OAuth1AccessToken accessToken, OAuthRequest request, String url, int offset) {
        final OAuthRequest request2 = new OAuthRequest(Verb.GET, baseUrl + baseApi + url, service);
        request2.addHeader("obp_limit", "100")
        request2.addHeader("obp_offset", "" + offset)
        service.signRequest(accessToken, request2);
        Response res2 = request2.send();
        res2.body

    }
}
