package de.interhyp.ing.hackathon.web.rest;

import de.interhyp.ing.hackathon.external.ing.IngApiHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Notebook-11 on 12/05/2017.
 */
@RestController
@RequestMapping("/ing/api")
public class TestResource {
    public static ConcurrentHashMap<String, String> CACHE = new ConcurrentHashMap<>();

    private final Logger log = LoggerFactory.getLogger(TestResource.class);

    private final IngApiHandler ingApiHandler;

    public TestResource(IngApiHandler ingApiHandler) {

        this.ingApiHandler = ingApiHandler;
    }

    @GetMapping("/start")
    public void doTestCall() throws IOException {
        log.info("called - will try handshake 1...");
        ingApiHandler.init();
        log.info("what happend?? ;) ...");

    }

    @GetMapping("/callback/{id}/")
    public void oauthCallback(@PathVariable(name = "id") String id, @RequestParam(name = "oauth_verifier") String token) throws IOException {
        log.info("called - will try handshake 1.. got Token {}.. - lets Put in in Cache..", token);
        //TODO Later link to user...
        CACHE.put(id, token);
        ingApiHandler.handleIt(id, token);

    }
}
