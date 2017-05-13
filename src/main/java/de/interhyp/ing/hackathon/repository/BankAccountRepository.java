package de.interhyp.ing.hackathon.repository;

import de.interhyp.ing.hackathon.domain.BankAccount;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the BankAccount entity.
 */
@SuppressWarnings("unused")
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {

}
