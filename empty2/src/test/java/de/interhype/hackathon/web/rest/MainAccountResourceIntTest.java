package de.interhype.hackathon.web.rest;

import de.interhype.hackathon.Empty2App;

import de.interhype.hackathon.domain.MainAccount;
import de.interhype.hackathon.repository.MainAccountRepository;
import de.interhype.hackathon.service.MainAccountService;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MainAccountResource REST controller.
 *
 * @see MainAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Empty2App.class)
public class MainAccountResourceIntTest {

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    @Autowired
    private MainAccountRepository mainAccountRepository;

    @Autowired
    private MainAccountService mainAccountService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMainAccountMockMvc;

    private MainAccount mainAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MainAccountResource mainAccountResource = new MainAccountResource(mainAccountService);
        this.restMainAccountMockMvc = MockMvcBuilders.standaloneSetup(mainAccountResource)
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
    public static MainAccount createEntity(EntityManager em) {
        MainAccount mainAccount = new MainAccount()
            .accountName(DEFAULT_ACCOUNT_NAME);
        return mainAccount;
    }

    @Before
    public void initTest() {
        mainAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createMainAccount() throws Exception {
        int databaseSizeBeforeCreate = mainAccountRepository.findAll().size();

        // Create the MainAccount
        restMainAccountMockMvc.perform(post("/api/main-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mainAccount)))
            .andExpect(status().isCreated());

        // Validate the MainAccount in the database
        List<MainAccount> mainAccountList = mainAccountRepository.findAll();
        assertThat(mainAccountList).hasSize(databaseSizeBeforeCreate + 1);
        MainAccount testMainAccount = mainAccountList.get(mainAccountList.size() - 1);
        assertThat(testMainAccount.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void createMainAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mainAccountRepository.findAll().size();

        // Create the MainAccount with an existing ID
        mainAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMainAccountMockMvc.perform(post("/api/main-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mainAccount)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MainAccount> mainAccountList = mainAccountRepository.findAll();
        assertThat(mainAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMainAccounts() throws Exception {
        // Initialize the database
        mainAccountRepository.saveAndFlush(mainAccount);

        // Get all the mainAccountList
        restMainAccountMockMvc.perform(get("/api/main-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mainAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMainAccount() throws Exception {
        // Initialize the database
        mainAccountRepository.saveAndFlush(mainAccount);

        // Get the mainAccount
        restMainAccountMockMvc.perform(get("/api/main-accounts/{id}", mainAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mainAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMainAccount() throws Exception {
        // Get the mainAccount
        restMainAccountMockMvc.perform(get("/api/main-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMainAccount() throws Exception {
        // Initialize the database
        mainAccountService.save(mainAccount);

        int databaseSizeBeforeUpdate = mainAccountRepository.findAll().size();

        // Update the mainAccount
        MainAccount updatedMainAccount = mainAccountRepository.findOne(mainAccount.getId());
        updatedMainAccount
            .accountName(UPDATED_ACCOUNT_NAME);

        restMainAccountMockMvc.perform(put("/api/main-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMainAccount)))
            .andExpect(status().isOk());

        // Validate the MainAccount in the database
        List<MainAccount> mainAccountList = mainAccountRepository.findAll();
        assertThat(mainAccountList).hasSize(databaseSizeBeforeUpdate);
        MainAccount testMainAccount = mainAccountList.get(mainAccountList.size() - 1);
        assertThat(testMainAccount.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMainAccount() throws Exception {
        int databaseSizeBeforeUpdate = mainAccountRepository.findAll().size();

        // Create the MainAccount

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMainAccountMockMvc.perform(put("/api/main-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mainAccount)))
            .andExpect(status().isCreated());

        // Validate the MainAccount in the database
        List<MainAccount> mainAccountList = mainAccountRepository.findAll();
        assertThat(mainAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMainAccount() throws Exception {
        // Initialize the database
        mainAccountService.save(mainAccount);

        int databaseSizeBeforeDelete = mainAccountRepository.findAll().size();

        // Get the mainAccount
        restMainAccountMockMvc.perform(delete("/api/main-accounts/{id}", mainAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MainAccount> mainAccountList = mainAccountRepository.findAll();
        assertThat(mainAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MainAccount.class);
    }
}
