package de.interhyp.ing.hackathon.repository;

import de.interhyp.ing.hackathon.domain.Calendar;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Calendar entity.
 */
@SuppressWarnings("unused")
public interface CalendarRepository extends JpaRepository<Calendar,Long> {

}
