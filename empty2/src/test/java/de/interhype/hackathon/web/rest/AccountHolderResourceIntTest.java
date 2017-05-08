package de.interhype.hackathon.web.rest;

import de.interhype.hackathon.Empty2App;

import de.interhype.hackathon.domain.AccountHolder;
import de.interhype.hackathon.repository.AccountHolderRepository;
import de.interhype.hackathon.service.AccountHolderService;
import de.interhype.hackathon.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static de.interhype.hackathon.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AccountHolderResource REST controller.
 *
 * @see AccountHolderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Empty2App.class)
public class AccountHolderResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DOB = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DOB = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AccountHolderService accountHolderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAccountHolderMockMvc;

    private AccountHolder accountHolder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AccountHolderResource accountHolderResource = new AccountHolderResource(accountHolderService);
        this.restAccountHolderMockMvc = MockMvcBuilders.standaloneSetup(accountHolderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountHolder createEntity(EntityManager em) {
        AccountHolder accountHolder = new AccountHolder()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .dob(DEFAULT_DOB);
        return accountHolder;
    }

    @Before
    public void initTest() {
        accountHolder = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountHolder() throws Exception {
        int databaseSizeBeforeCreate = accountHolderRepository.findAll().size();

        // Create the AccountHolder
        restAccountHolderMockMvc.perform(post("/api/account-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountHolder)))
            .andExpect(status().isCreated());

        // Validate the AccountHolder in the database
        List<AccountHolder> accountHolderList = accountHolderRepository.findAll();
        assertThat(accountHolderList).hasSize(databaseSizeBeforeCreate + 1);
        AccountHolder testAccountHolder = accountHolderList.get(accountHolderList.size() - 1);
        assertThat(testAccountHolder.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAccountHolder.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAccountHolder.getDob()).isEqualTo(DEFAULT_DOB);
    }

    @Test
    @Transactional
    public void createAccountHolderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountHolderRepository.findAll().size();

        // Create the AccountHolder with an existing ID
        accountHolder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountHolderMockMvc.perform(post("/api/account-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountHolder)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AccountHolder> accountHolderList = accountHolderRepository.findAll();
        assertThat(accountHolderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAccountHolders() throws Exception {
        // Initialize the database
        accountHolderRepository.saveAndFlush(accountHolder);

        // Get all the accountHolderList
        restAccountHolderMockMvc.perform(get("/api/account-holders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountHolder.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(sameInstant(DEFAULT_DOB))));
    }

    @Test
    @Transactional
    public void getAccountHolder() throws Exception {
        // Initialize the database
        accountHolderRepository.saveAndFlush(accountHolder);

        // Get the accountHolder
        restAccountHolderMockMvc.perform(get("/api/account-holders/{id}", accountHolder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountHolder.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.dob").value(sameInstant(DEFAULT_DOB)));
    }

    @Test
    @Transactional
    public void getNonExistingAccountHolder() throws Exception {
        // Get the accountHolder
        restAccountHolderMockMvc.perform(get("/api/account-holders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountHolder() throws Exception {
        // Initialize the database
        accountHolderService.save(accountHolder);

        int databaseSizeBeforeUpdate = accountHolderRepository.findAll().size();

        // Update the accountHolder
        AccountHolder updatedAccountHolder = accountHolderRepository.findOne(accountHolder.getId());
        updatedAccountHolder
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB);

        restAccountHolderMockMvc.perform(put("/api/account-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccountHolder)))
            .andExpect(status().isOk());

        // Validate the AccountHolder in the database
        List<AccountHolder> accountHolderList = accountHolderRepository.findAll();
        assertThat(accountHolderList).hasSize(databaseSizeBeforeUpdate);
        AccountHolder testAccountHolder = accountHolderList.get(accountHolderList.size() - 1);
        assertThat(testAccountHolder.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAccountHolder.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAccountHolder.getDob()).isEqualTo(UPDATED_DOB);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountHolder() throws Exception {
        int databaseSizeBeforeUpdate = accountHolderRepository.findAll().size();

        // Create the AccountHolder

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAccountHolderMockMvc.perform(put("/api/account-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountHolder)))
            .andExpect(status().isCreated());

        // Validate the AccountHolder in the database
        List<AccountHolder> accountHolderList = accountHolderRepository.findAll();
        assertThat(accountHolderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAccountHolder() throws Exception {
        // Initialize the database
        accountHolderService.save(accountHolder);

        int databaseSizeBeforeDelete = accountHolderRepository.findAll().size();

        // Get the accountHolder
        restAccountHolderMockMvc.perform(delete("/api/account-holders/{id}", accountHolder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AccountHolder> accountHolderList = accountHolderRepository.findAll();
        assertThat(accountHolderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountHolder.class);
    }
}
