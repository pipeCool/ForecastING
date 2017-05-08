package de.interhype.hackathon.service.impl;

import de.interhype.hackathon.service.AccountHolderService;
import de.interhype.hackathon.domain.AccountHolder;
import de.interhype.hackathon.repository.AccountHolderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing AccountHolder.
 */
@Service
@Transactional
public class AccountHolderServiceImpl implements AccountHolderService{

    private final Logger log = LoggerFactory.getLogger(AccountHolderServiceImpl.class);
    
    private final AccountHolderRepository accountHolderRepository;

    public AccountHolderServiceImpl(AccountHolderRepository accountHolderRepository) {
        this.accountHolderRepository = accountHolderRepository;
    }

    /**
     * Save a accountHolder.
     *
     * @param accountHolder the entity to save
     * @return the persisted entity
     */
    @Override
    public AccountHolder save(AccountHolder accountHolder) {
        log.debug("Request to save AccountHolder : {}", accountHolder);
        AccountHolder result = accountHolderRepository.save(accountHolder);
        return result;
    }

    /**
     *  Get all the accountHolders.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountHolder> findAll() {
        log.debug("Request to get all AccountHolders");
        List<AccountHolder> result = accountHolderRepository.findAll();

        return result;
    }

    /**
     *  Get one accountHolder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AccountHolder findOne(Long id) {
        log.debug("Request to get AccountHolder : {}", id);
        AccountHolder accountHolder = accountHolderRepository.findOne(id);
        return accountHolder;
    }

    /**
     *  Delete the  accountHolder by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountHolder : {}", id);
        accountHolderRepository.delete(id);
    }
}
