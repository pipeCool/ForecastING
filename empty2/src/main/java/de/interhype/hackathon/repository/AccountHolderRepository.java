package de.interhype.hackathon.repository;

import de.interhype.hackathon.domain.AccountHolder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AccountHolder entity.
 */
@SuppressWarnings("unused")
public interface AccountHolderRepository extends JpaRepository<AccountHolder,Long> {

}
