package de.interhype.hackathon.service;

import de.interhype.hackathon.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Transaction.
 */
public interface TransactionService {

    /**
     * Save a transaction.
     *
     * @param transaction the entity to save
     * @return the persisted entity
     */
    Transaction save(Transaction transaction);

    /**
     *  Get all the transactions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Transaction> findAll(Pageable pageable);

    /**
     *  Get the "id" transaction.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Transaction findOne(Long id);

    /**
     *  Delete the "id" transaction.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
