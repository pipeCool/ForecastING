package de.interhyp.ing.hackathon.repository;

import de.interhyp.ing.hackathon.domain.Label;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Label entity.
 * @deprecated only keep as example
 */
@SuppressWarnings("unused")
public interface LabelRepository extends JpaRepository<Label,Long> {

}
