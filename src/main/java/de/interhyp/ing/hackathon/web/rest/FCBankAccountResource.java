package de.interhyp.ing.hackathon.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.interhyp.ing.hackathon.domain.FCBankAccount;

import de.interhyp.ing.hackathon.repository.FCBankAccountRepository;
import de.interhyp.ing.hackathon.web.rest.util.HeaderUtil;
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
 * REST controller for managing FCBankAccount.
 */
@RestController
@RequestMapping("/api")
public class FCBankAccountResource {

    private final Logger log = LoggerFactory.getLogger(FCBankAccountResource.class);

    private static final String ENTITY_NAME = "fCBankAccount";
        
    private final FCBankAccountRepository fCBankAccountRepository;

    public FCBankAccountResource(FCBankAccountRepository fCBankAccountRepository) {
        this.fCBankAccountRepository = fCBankAccountRepository;
    }

    /**
     * POST  /f-c-bank-accounts : Create a new fCBankAccount.
     *
     * @param fCBankAccount the fCBankAccount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fCBankAccount, or with status 400 (Bad Request) if the fCBankAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/f-c-bank-accounts")
    @Timed
    public ResponseEntity<FCBankAccount> createFCBankAccount(@RequestBody FCBankAccount fCBankAccount) throws URISyntaxException {
        log.debug("REST request to save FCBankAccount : {}", fCBankAccount);
        if (fCBankAccount.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fCBankAccount cannot already have an ID")).body(null);
        }
        FCBankAccount result = fCBankAccountRepository.save(fCBankAccount);
        return ResponseEntity.created(new URI("/api/f-c-bank-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /f-c-bank-accounts : Updates an existing fCBankAccount.
     *
     * @param fCBankAccount the fCBankAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fCBankAccount,
     * or with status 400 (Bad Request) if the fCBankAccount is not valid,
     * or with status 500 (Internal Server Error) if the fCBankAccount couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/f-c-bank-accounts")
    @Timed
    public ResponseEntity<FCBankAccount> updateFCBankAccount(@RequestBody FCBankAccount fCBankAccount) throws URISyntaxException {
        log.debug("REST request to update FCBankAccount : {}", fCBankAccount);
        if (fCBankAccount.getId() == null) {
            return createFCBankAccount(fCBankAccount);
        }
        FCBankAccount result = fCBankAccountRepository.save(fCBankAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fCBankAccount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /f-c-bank-accounts : get all the fCBankAccounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fCBankAccounts in body
     */
    @GetMapping("/f-c-bank-accounts")
    @Timed
    public List<FCBankAccount> getAllFCBankAccounts() {
        log.debug("REST request to get all FCBankAccounts");
        List<FCBankAccount> fCBankAccounts = fCBankAccountRepository.findAll();
        return fCBankAccounts;
    }

    /**
     * GET  /f-c-bank-accounts/:id : get the "id" fCBankAccount.
     *
     * @param id the id of the fCBankAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fCBankAccount, or with status 404 (Not Found)
     */
    @GetMapping("/f-c-bank-accounts/{id}")
    @Timed
    public ResponseEntity<FCBankAccount> getFCBankAccount(@PathVariable Long id) {
        log.debug("REST request to get FCBankAccount : {}", id);
        FCBankAccount fCBankAccount = fCBankAccountRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fCBankAccount));
    }

    /**
     * DELETE  /f-c-bank-accounts/:id : delete the "id" fCBankAccount.
     *
     * @param id the id of the fCBankAccount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/f-c-bank-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteFCBankAccount(@PathVariable Long id) {
        log.debug("REST request to delete FCBankAccount : {}", id);
        fCBankAccountRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
