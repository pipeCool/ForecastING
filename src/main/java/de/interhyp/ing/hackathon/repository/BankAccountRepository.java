package de.interhyp.ing.hackathon.repository;

import de.interhyp.ing.hackathon.domain.BankAccount;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the BankAccount entity.
 */
@SuppressWarnings("unused")
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {

    @Query("select bankAccount from BankAccount bankAccount where bankAccount.user.login = ?#{principal.username}")
    List<BankAccount> findByUserIsCurrentUser();

    Optional<BankAccount> findByIban(String iban);
}
