package de.interhyp.ing.hackathon.service;

import de.interhyp.ing.hackathon.domain.Calendar;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
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

    List<Calendar> findSome(String from, String till);

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
