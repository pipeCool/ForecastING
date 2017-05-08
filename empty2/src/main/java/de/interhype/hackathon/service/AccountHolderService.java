package de.interhype.hackathon.service;

import de.interhype.hackathon.domain.AccountHolder;
import java.util.List;

/**
 * Service Interface for managing AccountHolder.
 */
public interface AccountHolderService {

    /**
     * Save a accountHolder.
     *
     * @param accountHolder the entity to save
     * @return the persisted entity
     */
    AccountHolder save(AccountHolder accountHolder);

    /**
     *  Get all the accountHolders.
     *  
     *  @return the list of entities
     */
    List<AccountHolder> findAll();

    /**
     *  Get the "id" accountHolder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AccountHolder findOne(Long id);

    /**
     *  Delete the "id" accountHolder.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
