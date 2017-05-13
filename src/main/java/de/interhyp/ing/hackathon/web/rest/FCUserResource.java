package de.interhyp.ing.hackathon.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.interhyp.ing.hackathon.domain.FCUser;

import de.interhyp.ing.hackathon.repository.FCUserRepository;
import de.interhyp.ing.hackathon.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FCUser.
 */
@RestController
@RequestMapping("/api")
public class FCUserResource {

    private final Logger log = LoggerFactory.getLogger(FCUserResource.class);

    private static final String ENTITY_NAME = "fCUser";
        
    private final FCUserRepository fCUserRepository;

    public FCUserResource(FCUserRepository fCUserRepository) {
        this.fCUserRepository = fCUserRepository;
    }

    /**
     * POST  /f-c-users : Create a new fCUser.
     *
     * @param fCUser the fCUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fCUser, or with status 400 (Bad Request) if the fCUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/f-c-users")
    @Timed
    public ResponseEntity<FCUser> createFCUser(@Valid @RequestBody FCUser fCUser) throws URISyntaxException {
        log.debug("REST request to save FCUser : {}", fCUser);
        if (fCUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fCUser cannot already have an ID")).body(null);
        }
        FCUser result = fCUserRepository.save(fCUser);
        return ResponseEntity.created(new URI("/api/f-c-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /f-c-users : Updates an existing fCUser.
     *
     * @param fCUser the fCUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fCUser,
     * or with status 400 (Bad Request) if the fCUser is not valid,
     * or with status 500 (Internal Server Error) if the fCUser couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/f-c-users")
    @Timed
    public ResponseEntity<FCUser> updateFCUser(@Valid @RequestBody FCUser fCUser) throws URISyntaxException {
        log.debug("REST request to update FCUser : {}", fCUser);
        if (fCUser.getId() == null) {
            return createFCUser(fCUser);
        }
        FCUser result = fCUserRepository.save(fCUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fCUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /f-c-users : get all the fCUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fCUsers in body
     */
    @GetMapping("/f-c-users")
    @Timed
    public List<FCUser> getAllFCUsers() {
        log.debug("REST request to get all FCUsers");
        List<FCUser> fCUsers = fCUserRepository.findAll();
        return fCUsers;
    }

    /**
     * GET  /f-c-users/:id : get the "id" fCUser.
     *
     * @param id the id of the fCUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fCUser, or with status 404 (Not Found)
     */
    @GetMapping("/f-c-users/{id}")
    @Timed
    public ResponseEntity<FCUser> getFCUser(@PathVariable Long id) {
        log.debug("REST request to get FCUser : {}", id);
        FCUser fCUser = fCUserRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fCUser));
    }

    /**
     * DELETE  /f-c-users/:id : delete the "id" fCUser.
     *
     * @param id the id of the fCUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/f-c-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteFCUser(@PathVariable Long id) {
        log.debug("REST request to delete FCUser : {}", id);
        fCUserRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
