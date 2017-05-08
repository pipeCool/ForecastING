package de.interhype.hackathon.service;

import de.interhype.hackathon.domain.MainAccount;
import java.util.List;

/**
 * Service Interface for managing MainAccount.
 */
public interface MainAccountService {

    /**
     * Save a mainAccount.
     *
     * @param mainAccount the entity to save
     * @return the persisted entity
     */
    MainAccount save(MainAccount mainAccount);

    /**
     *  Get all the mainAccounts.
     *  
     *  @return the list of entities
     */
    List<MainAccount> findAll();

    /**
     *  Get the "id" mainAccount.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MainAccount findOne(Long id);

    /**
     *  Delete the "id" mainAccount.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
