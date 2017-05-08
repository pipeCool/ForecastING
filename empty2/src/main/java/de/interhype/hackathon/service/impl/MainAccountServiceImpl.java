package de.interhype.hackathon.service.impl;

import de.interhype.hackathon.service.MainAccountService;
import de.interhype.hackathon.domain.MainAccount;
import de.interhype.hackathon.repository.MainAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing MainAccount.
 */
@Service
@Transactional
public class MainAccountServiceImpl implements MainAccountService{

    private final Logger log = LoggerFactory.getLogger(MainAccountServiceImpl.class);
    
    private final MainAccountRepository mainAccountRepository;

    public MainAccountServiceImpl(MainAccountRepository mainAccountRepository) {
        this.mainAccountRepository = mainAccountRepository;
    }

    /**
     * Save a mainAccount.
     *
     * @param mainAccount the entity to save
     * @return the persisted entity
     */
    @Override
    public MainAccount save(MainAccount mainAccount) {
        log.debug("Request to save MainAccount : {}", mainAccount);
        MainAccount result = mainAccountRepository.save(mainAccount);
        return result;
    }

    /**
     *  Get all the mainAccounts.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MainAccount> findAll() {
        log.debug("Request to get all MainAccounts");
        List<MainAccount> result = mainAccountRepository.findAll();

        return result;
    }

    /**
     *  Get one mainAccount by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MainAccount findOne(Long id) {
        log.debug("Request to get MainAccount : {}", id);
        MainAccount mainAccount = mainAccountRepository.findOne(id);
        return mainAccount;
    }

    /**
     *  Delete the  mainAccount by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MainAccount : {}", id);
        mainAccountRepository.delete(id);
    }
}
