package de.interhyp.ing.hackathon.repository;

import de.interhyp.ing.hackathon.domain.Transaction;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Transaction entity.
 */
@SuppressWarnings("unused")
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

}
