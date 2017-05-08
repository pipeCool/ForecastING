package de.interhype.hackathon.service;

import de.interhype.hackathon.domain.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BankAccount> findAll(Pageable pageable);

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
