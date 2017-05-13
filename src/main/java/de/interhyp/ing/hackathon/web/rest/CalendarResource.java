package de.interhyp.ing.hackathon.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.interhyp.ing.hackathon.domain.Calendar;
import de.interhyp.ing.hackathon.service.CalendarService;
import de.interhyp.ing.hackathon.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Calendar.
 */
@RestController
@RequestMapping("/api")
public class CalendarResource {

    private final Logger log = LoggerFactory.getLogger(CalendarResource.class);

    private static final String ENTITY_NAME = "calendar";

    private final CalendarService calendarService;

    public CalendarResource(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    /**
     * POST  /calendars : Create a new calendar.
     *
     * @param calendar the calendar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calendar, or with status 400 (Bad Request) if the calendar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calendars")
    @Timed
    public ResponseEntity<Calendar> createCalendar(@RequestBody Calendar calendar) throws URISyntaxException {
        log.debug("REST request to save Calendar : {}", calendar);
        if (calendar.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new calendar cannot already have an ID")).body(null);
        }
        Calendar result = calendarService.save(calendar);
        return ResponseEntity.created(new URI("/api/calendars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calendars : Updates an existing calendar.
     *
     * @param calendar the calendar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calendar,
     * or with status 400 (Bad Request) if the calendar is not valid,
     * or with status 500 (Internal Server Error) if the calendar couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calendars")
    @Timed
    public ResponseEntity<Calendar> updateCalendar(@RequestBody Calendar calendar) throws URISyntaxException {
        log.debug("REST request to update Calendar : {}", calendar);
        if (calendar.getId() == null) {
            return createCalendar(calendar);
        }
        Calendar result = calendarService.save(calendar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, calendar.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calendars : get all the calendars.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of calendars in body
     */
    /*@GetMapping("/calendars")
    @Timed
    public List<Calendar> getAllCalendars() {
        log.debug("REST request to get all Calendars");
        return calendarService.findAll();
    }*/

    @GetMapping("/calendars/{from}/{till}")
    @Timed
    public List<Calendar> getSomeCalendars(
        @RequestParam("from") String from,
        @RequestParam("till") String till
    ) {
        log.debug("REST request to get all Calendars");
        return calendarService.findSome(from, till);
    }

    /**
     * GET  /calendars/:id : get the "id" calendar.
     *
     * @param id the id of the calendar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calendar, or with status 404 (Not Found)
     */
    @GetMapping("/calendars/{id}")
    @Timed
    public ResponseEntity<Calendar> getCalendar(@PathVariable Long id) {
        log.debug("REST request to get Calendar : {}", id);
        Calendar calendar = calendarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(calendar));
    }

    /**
     * DELETE  /calendars/:id : delete the "id" calendar.
     *
     * @param id the id of the calendar to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calendars/{id}")
    @Timed
    public ResponseEntity<Void> deleteCalendar(@PathVariable Long id) {
        log.debug("REST request to delete Calendar : {}", id);
        calendarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
