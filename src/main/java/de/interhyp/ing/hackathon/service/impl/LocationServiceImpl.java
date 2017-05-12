package de.interhyp.ing.hackathon.service.impl;

import de.interhyp.ing.hackathon.service.LocationService;
import de.interhyp.ing.hackathon.domain.Location;
import de.interhyp.ing.hackathon.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Location.
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService{

    private final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);
    
    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Save a location.
     *
     * @param location the entity to save
     * @return the persisted entity
     */
    @Override
    public Location save(Location location) {
        log.debug("Request to save Location : {}", location);
        Location result = locationRepository.save(location);
        return result;
    }

    /**
     *  Get all the locations.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Location> findAll() {
        log.debug("Request to get all Locations");
        List<Location> result = locationRepository.findAll();

        return result;
    }

    /**
     *  Get one location by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Location findOne(Long id) {
        log.debug("Request to get Location : {}", id);
        Location location = locationRepository.findOne(id);
        return location;
    }

    /**
     *  Delete the  location by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.delete(id);
    }
}
