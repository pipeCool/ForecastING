package de.interhyp.ing.hackathon.repository;

import de.interhyp.ing.hackathon.domain.FCBankAccount;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FCBankAccount entity.
 */
@SuppressWarnings("unused")
public interface FCBankAccountRepository extends JpaRepository<FCBankAccount,Long> {

}
