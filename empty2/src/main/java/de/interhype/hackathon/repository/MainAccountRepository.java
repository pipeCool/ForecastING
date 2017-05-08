package de.interhype.hackathon.repository;

import de.interhype.hackathon.domain.MainAccount;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MainAccount entity.
 */
@SuppressWarnings("unused")
public interface MainAccountRepository extends JpaRepository<MainAccount,Long> {

}
