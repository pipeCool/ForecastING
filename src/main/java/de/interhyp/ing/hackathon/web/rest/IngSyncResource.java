package de.interhyp.ing.hackathon.web.rest;

import de.interhyp.ing.hackathon.external.ing.IngApiHandler;
import de.interhyp.ing.hackathon.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Notebook-11 on 12/05/2017.
 */
@RestController
@RequestMapping("/ing/api")
public class IngSyncResource {
    public static ConcurrentHashMap<String, String> CACHE = new ConcurrentHashMap<>();

    private final Logger log = LoggerFactory.getLogger(IngSyncResource.class);

    private final IngApiHandler ingApiHandler;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final CalendarRepository calendarRepository;

    public IngSyncResource(IngApiHandler ingApiHandler, UserRepository userRepository, PasswordEncoder passwordEncoder,
                           AuthorityRepository authorityRepository,
                           BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository, CalendarRepository calendarRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.ingApiHandler = ingApiHandler;
        this.calendarRepository = calendarRepository;
    }

    @GetMapping("/start")
    public void doTestCall(HttpServletResponse response) throws IOException {
        log.info("called - will try handshake 1...");
        String url = ingApiHandler.init();
        log.info("what happend?? ;) ...");
        response.sendRedirect(url);

    }

    @GetMapping("/callback/{id}/")
    public void oauthCallback(@PathVariable(name = "id") String id, @RequestParam(name = "oauth_verifier") String token,
                              HttpServletResponse response) throws IOException {
        log.info("called - will try handshake 1.. got Token {}.. - lets Put in in Cache..", token);
        //TODO Later link to user...
        CACHE.put(id, token);
        ingApiHandler.handleIt(id, token, userRepository, passwordEncoder,
            authorityRepository,
            bankAccountRepository, transactionRepository, calendarRepository);
        response.sendRedirect("http://localhost:8080");

    }
}


