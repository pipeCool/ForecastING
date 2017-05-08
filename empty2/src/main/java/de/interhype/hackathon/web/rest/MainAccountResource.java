package de.interhype.hackathon.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.interhype.hackathon.domain.MainAccount;
import de.interhype.hackathon.service.MainAccountService;
import de.interhype.hackathon.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MainAccount.
 */
@RestController
@RequestMapping("/api")
public class MainAccountResource {

    private final Logger log = LoggerFactory.getLogger(MainAccountResource.class);

    private static final String ENTITY_NAME = "mainAccount";
        
    private final MainAccountService mainAccountService;

    public MainAccountResource(MainAccountService mainAccountService) {
        this.mainAccountService = mainAccountService;
    }

    /**
     * POST  /main-accounts : Create a new mainAccount.
     *
     * @param mainAccount the mainAccount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mainAccount, or with status 400 (Bad Request) if the mainAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/main-accounts")
    @Timed
    public ResponseEntity<MainAccount> createMainAccount(@RequestBody MainAccount mainAccount) throws URISyntaxException {
        log.debug("REST request to save MainAccount : {}", mainAccount);
        if (mainAccount.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mainAccount cannot already have an ID")).body(null);
        }
        MainAccount result = mainAccountService.save(mainAccount);
        return ResponseEntity.created(new URI("/api/main-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /main-accounts : Updates an existing mainAccount.
     *
     * @param mainAccount the mainAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mainAccount,
     * or with status 400 (Bad Request) if the mainAccount is not valid,
     * or with status 500 (Internal Server Error) if the mainAccount couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/main-accounts")
    @Timed
    public ResponseEntity<MainAccount> updateMainAccount(@RequestBody MainAccount mainAccount) throws URISyntaxException {
        log.debug("REST request to update MainAccount : {}", mainAccount);
        if (mainAccount.getId() == null) {
            return createMainAccount(mainAccount);
        }
        MainAccount result = mainAccountService.save(mainAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mainAccount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /main-accounts : get all the mainAccounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mainAccounts in body
     */
    @GetMapping("/main-accounts")
    @Timed
    public List<MainAccount> getAllMainAccounts() {
        log.debug("REST request to get all MainAccounts");
        return mainAccountService.findAll();
    }

    /**
     * GET  /main-accounts/:id : get the "id" mainAccount.
     *
     * @param id the id of the mainAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mainAccount, or with status 404 (Not Found)
     */
    @GetMapping("/main-accounts/{id}")
    @Timed
    public ResponseEntity<MainAccount> getMainAccount(@PathVariable Long id) {
        log.debug("REST request to get MainAccount : {}", id);
        MainAccount mainAccount = mainAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mainAccount));
    }

    /**
     * DELETE  /main-accounts/:id : delete the "id" mainAccount.
     *
     * @param id the id of the mainAccount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/main-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteMainAccount(@PathVariable Long id) {
        log.debug("REST request to delete MainAccount : {}", id);
        mainAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
