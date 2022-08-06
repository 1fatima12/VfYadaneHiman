package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Arrivage;
import com.mycompany.myapp.repository.ArrivageRepository;
import com.mycompany.myapp.service.dto.ArrivageDTO;
import com.mycompany.myapp.service.mapper.ArrivageMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ArrivageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArrivageResourceIT {

    private static final LocalDate DEFAULT_DATE_ARRIVAGE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ARRIVAGE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_PRIX_ACHAT = 1F;
    private static final Float UPDATED_PRIX_ACHAT = 2F;

    private static final String ENTITY_API_URL = "/api/arrivages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArrivageRepository arrivageRepository;

    @Autowired
    private ArrivageMapper arrivageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArrivageMockMvc;

    private Arrivage arrivage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arrivage createEntity(EntityManager em) {
        Arrivage arrivage = new Arrivage().dateArrivage(DEFAULT_DATE_ARRIVAGE).prixAchat(DEFAULT_PRIX_ACHAT);
        return arrivage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arrivage createUpdatedEntity(EntityManager em) {
        Arrivage arrivage = new Arrivage().dateArrivage(UPDATED_DATE_ARRIVAGE).prixAchat(UPDATED_PRIX_ACHAT);
        return arrivage;
    }

    @BeforeEach
    public void initTest() {
        arrivage = createEntity(em);
    }

    @Test
    @Transactional
    void createArrivage() throws Exception {
        int databaseSizeBeforeCreate = arrivageRepository.findAll().size();
        // Create the Arrivage
        ArrivageDTO arrivageDTO = arrivageMapper.toDto(arrivage);
        restArrivageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arrivageDTO)))
            .andExpect(status().isCreated());

        // Validate the Arrivage in the database
        List<Arrivage> arrivageList = arrivageRepository.findAll();
        assertThat(arrivageList).hasSize(databaseSizeBeforeCreate + 1);
        Arrivage testArrivage = arrivageList.get(arrivageList.size() - 1);
        assertThat(testArrivage.getDateArrivage()).isEqualTo(DEFAULT_DATE_ARRIVAGE);
        assertThat(testArrivage.getPrixAchat()).isEqualTo(DEFAULT_PRIX_ACHAT);
    }

    @Test
    @Transactional
    void createArrivageWithExistingId() throws Exception {
        // Create the Arrivage with an existing ID
        arrivage.setId(1L);
        ArrivageDTO arrivageDTO = arrivageMapper.toDto(arrivage);

        int databaseSizeBeforeCreate = arrivageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArrivageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arrivageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Arrivage in the database
        List<Arrivage> arrivageList = arrivageRepository.findAll();
        assertThat(arrivageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArrivages() throws Exception {
        // Initialize the database
        arrivageRepository.saveAndFlush(arrivage);

        // Get all the arrivageList
        restArrivageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arrivage.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateArrivage").value(hasItem(DEFAULT_DATE_ARRIVAGE.toString())))
            .andExpect(jsonPath("$.[*].prixAchat").value(hasItem(DEFAULT_PRIX_ACHAT.doubleValue())));
    }

    @Test
    @Transactional
    void getArrivage() throws Exception {
        // Initialize the database
        arrivageRepository.saveAndFlush(arrivage);

        // Get the arrivage
        restArrivageMockMvc
            .perform(get(ENTITY_API_URL_ID, arrivage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(arrivage.getId().intValue()))
            .andExpect(jsonPath("$.dateArrivage").value(DEFAULT_DATE_ARRIVAGE.toString()))
            .andExpect(jsonPath("$.prixAchat").value(DEFAULT_PRIX_ACHAT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingArrivage() throws Exception {
        // Get the arrivage
        restArrivageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewArrivage() throws Exception {
        // Initialize the database
        arrivageRepository.saveAndFlush(arrivage);

        int databaseSizeBeforeUpdate = arrivageRepository.findAll().size();

        // Update the arrivage
        Arrivage updatedArrivage = arrivageRepository.findById(arrivage.getId()).get();
        // Disconnect from session so that the updates on updatedArrivage are not directly saved in db
        em.detach(updatedArrivage);
        updatedArrivage.dateArrivage(UPDATED_DATE_ARRIVAGE).prixAchat(UPDATED_PRIX_ACHAT);
        ArrivageDTO arrivageDTO = arrivageMapper.toDto(updatedArrivage);

        restArrivageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, arrivageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arrivageDTO))
            )
            .andExpect(status().isOk());

        // Validate the Arrivage in the database
        List<Arrivage> arrivageList = arrivageRepository.findAll();
        assertThat(arrivageList).hasSize(databaseSizeBeforeUpdate);
        Arrivage testArrivage = arrivageList.get(arrivageList.size() - 1);
        assertThat(testArrivage.getDateArrivage()).isEqualTo(UPDATED_DATE_ARRIVAGE);
        assertThat(testArrivage.getPrixAchat()).isEqualTo(UPDATED_PRIX_ACHAT);
    }

    @Test
    @Transactional
    void putNonExistingArrivage() throws Exception {
        int databaseSizeBeforeUpdate = arrivageRepository.findAll().size();
        arrivage.setId(count.incrementAndGet());

        // Create the Arrivage
        ArrivageDTO arrivageDTO = arrivageMapper.toDto(arrivage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArrivageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, arrivageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arrivageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arrivage in the database
        List<Arrivage> arrivageList = arrivageRepository.findAll();
        assertThat(arrivageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArrivage() throws Exception {
        int databaseSizeBeforeUpdate = arrivageRepository.findAll().size();
        arrivage.setId(count.incrementAndGet());

        // Create the Arrivage
        ArrivageDTO arrivageDTO = arrivageMapper.toDto(arrivage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArrivageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arrivageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arrivage in the database
        List<Arrivage> arrivageList = arrivageRepository.findAll();
        assertThat(arrivageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArrivage() throws Exception {
        int databaseSizeBeforeUpdate = arrivageRepository.findAll().size();
        arrivage.setId(count.incrementAndGet());

        // Create the Arrivage
        ArrivageDTO arrivageDTO = arrivageMapper.toDto(arrivage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArrivageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arrivageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Arrivage in the database
        List<Arrivage> arrivageList = arrivageRepository.findAll();
        assertThat(arrivageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArrivageWithPatch() throws Exception {
        // Initialize the database
        arrivageRepository.saveAndFlush(arrivage);

        int databaseSizeBeforeUpdate = arrivageRepository.findAll().size();

        // Update the arrivage using partial update
        Arrivage partialUpdatedArrivage = new Arrivage();
        partialUpdatedArrivage.setId(arrivage.getId());

        partialUpdatedArrivage.dateArrivage(UPDATED_DATE_ARRIVAGE).prixAchat(UPDATED_PRIX_ACHAT);

        restArrivageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArrivage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArrivage))
            )
            .andExpect(status().isOk());

        // Validate the Arrivage in the database
        List<Arrivage> arrivageList = arrivageRepository.findAll();
        assertThat(arrivageList).hasSize(databaseSizeBeforeUpdate);
        Arrivage testArrivage = arrivageList.get(arrivageList.size() - 1);
        assertThat(testArrivage.getDateArrivage()).isEqualTo(UPDATED_DATE_ARRIVAGE);
        assertThat(testArrivage.getPrixAchat()).isEqualTo(UPDATED_PRIX_ACHAT);
    }

    @Test
    @Transactional
    void fullUpdateArrivageWithPatch() throws Exception {
        // Initialize the database
        arrivageRepository.saveAndFlush(arrivage);

        int databaseSizeBeforeUpdate = arrivageRepository.findAll().size();

        // Update the arrivage using partial update
        Arrivage partialUpdatedArrivage = new Arrivage();
        partialUpdatedArrivage.setId(arrivage.getId());

        partialUpdatedArrivage.dateArrivage(UPDATED_DATE_ARRIVAGE).prixAchat(UPDATED_PRIX_ACHAT);

        restArrivageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArrivage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArrivage))
            )
            .andExpect(status().isOk());

        // Validate the Arrivage in the database
        List<Arrivage> arrivageList = arrivageRepository.findAll();
        assertThat(arrivageList).hasSize(databaseSizeBeforeUpdate);
        Arrivage testArrivage = arrivageList.get(arrivageList.size() - 1);
        assertThat(testArrivage.getDateArrivage()).isEqualTo(UPDATED_DATE_ARRIVAGE);
        assertThat(testArrivage.getPrixAchat()).isEqualTo(UPDATED_PRIX_ACHAT);
    }

    @Test
    @Transactional
    void patchNonExistingArrivage() throws Exception {
        int databaseSizeBeforeUpdate = arrivageRepository.findAll().size();
        arrivage.setId(count.incrementAndGet());

        // Create the Arrivage
        ArrivageDTO arrivageDTO = arrivageMapper.toDto(arrivage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArrivageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, arrivageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arrivageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arrivage in the database
        List<Arrivage> arrivageList = arrivageRepository.findAll();
        assertThat(arrivageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArrivage() throws Exception {
        int databaseSizeBeforeUpdate = arrivageRepository.findAll().size();
        arrivage.setId(count.incrementAndGet());

        // Create the Arrivage
        ArrivageDTO arrivageDTO = arrivageMapper.toDto(arrivage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArrivageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arrivageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arrivage in the database
        List<Arrivage> arrivageList = arrivageRepository.findAll();
        assertThat(arrivageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArrivage() throws Exception {
        int databaseSizeBeforeUpdate = arrivageRepository.findAll().size();
        arrivage.setId(count.incrementAndGet());

        // Create the Arrivage
        ArrivageDTO arrivageDTO = arrivageMapper.toDto(arrivage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArrivageMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(arrivageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Arrivage in the database
        List<Arrivage> arrivageList = arrivageRepository.findAll();
        assertThat(arrivageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArrivage() throws Exception {
        // Initialize the database
        arrivageRepository.saveAndFlush(arrivage);

        int databaseSizeBeforeDelete = arrivageRepository.findAll().size();

        // Delete the arrivage
        restArrivageMockMvc
            .perform(delete(ENTITY_API_URL_ID, arrivage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Arrivage> arrivageList = arrivageRepository.findAll();
        assertThat(arrivageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
