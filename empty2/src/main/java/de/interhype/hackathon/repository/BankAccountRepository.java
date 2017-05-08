package de.interhype.hackathon.repository;

import de.interhype.hackathon.domain.BankAccount;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BankAccount entity.
 */
@SuppressWarnings("unused")
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {

}
