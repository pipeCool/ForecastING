package de.interhype.hackathon.service.impl;

import de.interhype.hackathon.service.BankAccountService;
import de.interhype.hackathon.domain.BankAccount;
import de.interhype.hackathon.repository.BankAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing BankAccount.
 */
@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService{

    private final Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);
    
    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Save a bankAccount.
     *
     * @param bankAccount the entity to save
     * @return the persisted entity
     */
    @Override
    public BankAccount save(BankAccount bankAccount) {
        log.debug("Request to save BankAccount : {}", bankAccount);
        BankAccount result = bankAccountRepository.save(bankAccount);
        return result;
    }

    /**
     *  Get all the bankAccounts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BankAccount> findAll(Pageable pageable) {
        log.debug("Request to get all BankAccounts");
        Page<BankAccount> result = bankAccountRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one bankAccount by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BankAccount findOne(Long id) {
        log.debug("Request to get BankAccount : {}", id);
        BankAccount bankAccount = bankAccountRepository.findOne(id);
        return bankAccount;
    }

    /**
     *  Delete the  bankAccount by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BankAccount : {}", id);
        bankAccountRepository.delete(id);
    }
}
