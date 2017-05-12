package de.interhyp.ing.hackathon.service;

import de.interhyp.ing.hackathon.domain.BankAccount;
import java.util.List;

/**
 * Service Interface for managing BankAccount.
 */
public interface BankAccountService {

    /**
     * Save a bankAccount.
     *
     * @param bankAccount the entity to save
     * @return the persisted entity
     */
    BankAccount save(BankAccount bankAccount);

    /**
     *  Get all the bankAccounts.
     *  
     *  @return the list of entities
     */
    List<BankAccount> findAll();

    /**
     *  Get the "id" bankAccount.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BankAccount findOne(Long id);

    /**
     *  Delete the "id" bankAccount.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
