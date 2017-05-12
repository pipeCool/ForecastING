package de.interhyp.ing.hackathon.service.impl;

import de.interhyp.ing.hackathon.service.CalendarService;
import de.interhyp.ing.hackathon.domain.Calendar;
import de.interhyp.ing.hackathon.repository.CalendarRepository;
import de.interhyp.ing.hackathon.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Calendar.
 */
@Service
@Transactional
public class CalendarServiceImpl implements CalendarService{

    @Autowired
    LocationService locationService;

    private final Logger log = LoggerFactory.getLogger(CalendarServiceImpl.class);

    private final CalendarRepository calendarRepository;

    public CalendarServiceImpl(CalendarRepository calendarRepository, LocationService locationService) {
        this.calendarRepository = calendarRepository;
        this.locationService = locationService;
    }

    /**
     * Save a calendar.
     *
     * @param calendar the entity to save
     * @return the persisted entity
     */
    @Override
    public Calendar save(Calendar calendar) {
        log.debug("Request to save Calendar : {}", calendar);
        if (calendar.getLocation() != null) {
            calendar.setLocation(locationService.save(calendar.getLocation()));
        }
        Calendar result = calendarRepository.save(calendar);
        return result;
    }

    /**
     *  Get all the calendars.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Calendar> findAll() {
        log.debug("Request to get all Calendars");
        List<Calendar> result = calendarRepository.findAll();

        return result;
    }

    /**
     *  Get one calendar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)

    public Calendar findOne(Long id) {
        log.debug("Request to get Calendar : {}", id);
        Calendar calendar = calendarRepository.findOne(id);
        return calendar;
    }

    /**
     *  Delete the  calendar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Calendar : {}", id);
        calendarRepository.delete(id);
    }
}
