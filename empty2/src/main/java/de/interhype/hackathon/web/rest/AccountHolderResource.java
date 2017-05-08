package de.interhype.hackathon.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.interhype.hackathon.domain.AccountHolder;
import de.interhype.hackathon.service.AccountHolderService;
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
 * REST controller for managing AccountHolder.
 */
@RestController
@RequestMapping("/api")
public class AccountHolderResource {

    private final Logger log = LoggerFactory.getLogger(AccountHolderResource.class);

    private static final String ENTITY_NAME = "accountHolder";
        
    private final AccountHolderService accountHolderService;

    public AccountHolderResource(AccountHolderService accountHolderService) {
        this.accountHolderService = accountHolderService;
    }

    /**
     * POST  /account-holders : Create a new accountHolder.
     *
     * @param accountHolder the accountHolder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accountHolder, or with status 400 (Bad Request) if the accountHolder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/account-holders")
    @Timed
    public ResponseEntity<AccountHolder> createAccountHolder(@RequestBody AccountHolder accountHolder) throws URISyntaxException {
        log.debug("REST request to save AccountHolder : {}", accountHolder);
        if (accountHolder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new accountHolder cannot already have an ID")).body(null);
        }
        AccountHolder result = accountHolderService.save(accountHolder);
        return ResponseEntity.created(new URI("/api/account-holders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /account-holders : Updates an existing accountHolder.
     *
     * @param accountHolder the accountHolder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accountHolder,
     * or with status 400 (Bad Request) if the accountHolder is not valid,
     * or with status 500 (Internal Server Error) if the accountHolder couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/account-holders")
    @Timed
    public ResponseEntity<AccountHolder> updateAccountHolder(@RequestBody AccountHolder accountHolder) throws URISyntaxException {
        log.debug("REST request to update AccountHolder : {}", accountHolder);
        if (accountHolder.getId() == null) {
            return createAccountHolder(accountHolder);
        }
        AccountHolder result = accountHolderService.save(accountHolder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accountHolder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /account-holders : get all the accountHolders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of accountHolders in body
     */
    @GetMapping("/account-holders")
    @Timed
    public List<AccountHolder> getAllAccountHolders() {
        log.debug("REST request to get all AccountHolders");
        return accountHolderService.findAll();
    }

    /**
     * GET  /account-holders/:id : get the "id" accountHolder.
     *
     * @param id the id of the accountHolder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accountHolder, or with status 404 (Not Found)
     */
    @GetMapping("/account-holders/{id}")
    @Timed
    public ResponseEntity<AccountHolder> getAccountHolder(@PathVariable Long id) {
        log.debug("REST request to get AccountHolder : {}", id);
        AccountHolder accountHolder = accountHolderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(accountHolder));
    }

    /**
     * DELETE  /account-holders/:id : delete the "id" accountHolder.
     *
     * @param id the id of the accountHolder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/account-holders/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccountHolder(@PathVariable Long id) {
        log.debug("REST request to delete AccountHolder : {}", id);
        accountHolderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
