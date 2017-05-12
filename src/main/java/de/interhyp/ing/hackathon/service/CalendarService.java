package de.interhyp.ing.hackathon.service;

import de.interhyp.ing.hackathon.domain.Calendar;
import java.util.List;

/**
 * Service Interface for managing Calendar.
 */
public interface CalendarService {

    /**
     * Save a calendar.
     *
     * @param calendar the entity to save
     * @return the persisted entity
     */
    Calendar save(Calendar calendar);

    /**
     *  Get all the calendars.
     *  
     *  @return the list of entities
     */
    List<Calendar> findAll();

    /**
     *  Get the "id" calendar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Calendar findOne(Long id);

    /**
     *  Delete the "id" calendar.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
