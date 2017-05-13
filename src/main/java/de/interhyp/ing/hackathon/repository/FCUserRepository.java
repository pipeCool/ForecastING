package de.interhyp.ing.hackathon.repository;

import de.interhyp.ing.hackathon.domain.FCUser;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FCUser entity.
 */
@SuppressWarnings("unused")
public interface FCUserRepository extends JpaRepository<FCUser,Long> {

}
