package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Bti;
import com.mycompany.myapp.repository.BtiRepository;
import com.mycompany.myapp.service.dto.BtiDTO;
import com.mycompany.myapp.service.mapper.BtiMapper;
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
 * Integration tests for the {@link BtiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BtiResourceIT {

    private static final Long DEFAULT_NUM_ORDRE = 1L;
    private static final Long UPDATED_NUM_ORDRE = 2L;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_REF = 1L;
    private static final Long UPDATED_REF = 2L;

    private static final Integer DEFAULT_QTE = 1;
    private static final Integer UPDATED_QTE = 2;

    private static final String ENTITY_API_URL = "/api/btis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BtiRepository btiRepository;

    @Autowired
    private BtiMapper btiMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBtiMockMvc;

    private Bti bti;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bti createEntity(EntityManager em) {
        Bti bti = new Bti().numOrdre(DEFAULT_NUM_ORDRE).date(DEFAULT_DATE).ref(DEFAULT_REF).qte(DEFAULT_QTE);
        return bti;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bti createUpdatedEntity(EntityManager em) {
        Bti bti = new Bti().numOrdre(UPDATED_NUM_ORDRE).date(UPDATED_DATE).ref(UPDATED_REF).qte(UPDATED_QTE);
        return bti;
    }

    @BeforeEach
    public void initTest() {
        bti = createEntity(em);
    }

    @Test
    @Transactional
    void createBti() throws Exception {
        int databaseSizeBeforeCreate = btiRepository.findAll().size();
        // Create the Bti
        BtiDTO btiDTO = btiMapper.toDto(bti);
        restBtiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(btiDTO)))
            .andExpect(status().isCreated());

        // Validate the Bti in the database
        List<Bti> btiList = btiRepository.findAll();
        assertThat(btiList).hasSize(databaseSizeBeforeCreate + 1);
        Bti testBti = btiList.get(btiList.size() - 1);
        assertThat(testBti.getNumOrdre()).isEqualTo(DEFAULT_NUM_ORDRE);
        assertThat(testBti.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBti.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testBti.getQte()).isEqualTo(DEFAULT_QTE);
    }

    @Test
    @Transactional
    void createBtiWithExistingId() throws Exception {
        // Create the Bti with an existing ID
        bti.setId(1L);
        BtiDTO btiDTO = btiMapper.toDto(bti);

        int databaseSizeBeforeCreate = btiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBtiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(btiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bti in the database
        List<Bti> btiList = btiRepository.findAll();
        assertThat(btiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBtis() throws Exception {
        // Initialize the database
        btiRepository.saveAndFlush(bti);

        // Get all the btiList
        restBtiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bti.getId().intValue())))
            .andExpect(jsonPath("$.[*].numOrdre").value(hasItem(DEFAULT_NUM_ORDRE.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.intValue())))
            .andExpect(jsonPath("$.[*].qte").value(hasItem(DEFAULT_QTE)));
    }

    @Test
    @Transactional
    void getBti() throws Exception {
        // Initialize the database
        btiRepository.saveAndFlush(bti);

        // Get the bti
        restBtiMockMvc
            .perform(get(ENTITY_API_URL_ID, bti.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bti.getId().intValue()))
            .andExpect(jsonPath("$.numOrdre").value(DEFAULT_NUM_ORDRE.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF.intValue()))
            .andExpect(jsonPath("$.qte").value(DEFAULT_QTE));
    }

    @Test
    @Transactional
    void getNonExistingBti() throws Exception {
        // Get the bti
        restBtiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBti() throws Exception {
        // Initialize the database
        btiRepository.saveAndFlush(bti);

        int databaseSizeBeforeUpdate = btiRepository.findAll().size();

        // Update the bti
        Bti updatedBti = btiRepository.findById(bti.getId()).get();
        // Disconnect from session so that the updates on updatedBti are not directly saved in db
        em.detach(updatedBti);
        updatedBti.numOrdre(UPDATED_NUM_ORDRE).date(UPDATED_DATE).ref(UPDATED_REF).qte(UPDATED_QTE);
        BtiDTO btiDTO = btiMapper.toDto(updatedBti);

        restBtiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, btiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(btiDTO))
            )
            .andExpect(status().isOk());

        // Validate the Bti in the database
        List<Bti> btiList = btiRepository.findAll();
        assertThat(btiList).hasSize(databaseSizeBeforeUpdate);
        Bti testBti = btiList.get(btiList.size() - 1);
        assertThat(testBti.getNumOrdre()).isEqualTo(UPDATED_NUM_ORDRE);
        assertThat(testBti.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBti.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testBti.getQte()).isEqualTo(UPDATED_QTE);
    }

    @Test
    @Transactional
    void putNonExistingBti() throws Exception {
        int databaseSizeBeforeUpdate = btiRepository.findAll().size();
        bti.setId(count.incrementAndGet());

        // Create the Bti
        BtiDTO btiDTO = btiMapper.toDto(bti);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBtiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, btiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(btiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bti in the database
        List<Bti> btiList = btiRepository.findAll();
        assertThat(btiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBti() throws Exception {
        int databaseSizeBeforeUpdate = btiRepository.findAll().size();
        bti.setId(count.incrementAndGet());

        // Create the Bti
        BtiDTO btiDTO = btiMapper.toDto(bti);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBtiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(btiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bti in the database
        List<Bti> btiList = btiRepository.findAll();
        assertThat(btiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBti() throws Exception {
        int databaseSizeBeforeUpdate = btiRepository.findAll().size();
        bti.setId(count.incrementAndGet());

        // Create the Bti
        BtiDTO btiDTO = btiMapper.toDto(bti);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBtiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(btiDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bti in the database
        List<Bti> btiList = btiRepository.findAll();
        assertThat(btiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBtiWithPatch() throws Exception {
        // Initialize the database
        btiRepository.saveAndFlush(bti);

        int databaseSizeBeforeUpdate = btiRepository.findAll().size();

        // Update the bti using partial update
        Bti partialUpdatedBti = new Bti();
        partialUpdatedBti.setId(bti.getId());

        restBtiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBti.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBti))
            )
            .andExpect(status().isOk());

        // Validate the Bti in the database
        List<Bti> btiList = btiRepository.findAll();
        assertThat(btiList).hasSize(databaseSizeBeforeUpdate);
        Bti testBti = btiList.get(btiList.size() - 1);
        assertThat(testBti.getNumOrdre()).isEqualTo(DEFAULT_NUM_ORDRE);
        assertThat(testBti.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBti.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testBti.getQte()).isEqualTo(DEFAULT_QTE);
    }

    @Test
    @Transactional
    void fullUpdateBtiWithPatch() throws Exception {
        // Initialize the database
        btiRepository.saveAndFlush(bti);

        int databaseSizeBeforeUpdate = btiRepository.findAll().size();

        // Update the bti using partial update
        Bti partialUpdatedBti = new Bti();
        partialUpdatedBti.setId(bti.getId());

        partialUpdatedBti.numOrdre(UPDATED_NUM_ORDRE).date(UPDATED_DATE).ref(UPDATED_REF).qte(UPDATED_QTE);

        restBtiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBti.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBti))
            )
            .andExpect(status().isOk());

        // Validate the Bti in the database
        List<Bti> btiList = btiRepository.findAll();
        assertThat(btiList).hasSize(databaseSizeBeforeUpdate);
        Bti testBti = btiList.get(btiList.size() - 1);
        assertThat(testBti.getNumOrdre()).isEqualTo(UPDATED_NUM_ORDRE);
        assertThat(testBti.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBti.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testBti.getQte()).isEqualTo(UPDATED_QTE);
    }

    @Test
    @Transactional
    void patchNonExistingBti() throws Exception {
        int databaseSizeBeforeUpdate = btiRepository.findAll().size();
        bti.setId(count.incrementAndGet());

        // Create the Bti
        BtiDTO btiDTO = btiMapper.toDto(bti);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBtiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, btiDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(btiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bti in the database
        List<Bti> btiList = btiRepository.findAll();
        assertThat(btiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBti() throws Exception {
        int databaseSizeBeforeUpdate = btiRepository.findAll().size();
        bti.setId(count.incrementAndGet());

        // Create the Bti
        BtiDTO btiDTO = btiMapper.toDto(bti);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBtiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(btiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bti in the database
        List<Bti> btiList = btiRepository.findAll();
        assertThat(btiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBti() throws Exception {
        int databaseSizeBeforeUpdate = btiRepository.findAll().size();
        bti.setId(count.incrementAndGet());

        // Create the Bti
        BtiDTO btiDTO = btiMapper.toDto(bti);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBtiMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(btiDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bti in the database
        List<Bti> btiList = btiRepository.findAll();
        assertThat(btiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBti() throws Exception {
        // Initialize the database
        btiRepository.saveAndFlush(bti);

        int databaseSizeBeforeDelete = btiRepository.findAll().size();

        // Delete the bti
        restBtiMockMvc.perform(delete(ENTITY_API_URL_ID, bti.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bti> btiList = btiRepository.findAll();
        assertThat(btiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
