package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Marque;
import com.mycompany.myapp.repository.MarqueRepository;
import com.mycompany.myapp.service.dto.MarqueDTO;
import com.mycompany.myapp.service.mapper.MarqueMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link MarqueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MarqueResourceIT {

    private static final String DEFAULT_NOM_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MARQUE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/marques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MarqueRepository marqueRepository;

    @Autowired
    private MarqueMapper marqueMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMarqueMockMvc;

    private Marque marque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Marque createEntity(EntityManager em) {
        Marque marque = new Marque().nomMarque(DEFAULT_NOM_MARQUE).logo(DEFAULT_LOGO).logoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        return marque;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Marque createUpdatedEntity(EntityManager em) {
        Marque marque = new Marque().nomMarque(UPDATED_NOM_MARQUE).logo(UPDATED_LOGO).logoContentType(UPDATED_LOGO_CONTENT_TYPE);
        return marque;
    }

    @BeforeEach
    public void initTest() {
        marque = createEntity(em);
    }

    @Test
    @Transactional
    void createMarque() throws Exception {
        int databaseSizeBeforeCreate = marqueRepository.findAll().size();
        // Create the Marque
        MarqueDTO marqueDTO = marqueMapper.toDto(marque);
        restMarqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marqueDTO)))
            .andExpect(status().isCreated());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeCreate + 1);
        Marque testMarque = marqueList.get(marqueList.size() - 1);
        assertThat(testMarque.getNomMarque()).isEqualTo(DEFAULT_NOM_MARQUE);
        assertThat(testMarque.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testMarque.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createMarqueWithExistingId() throws Exception {
        // Create the Marque with an existing ID
        marque.setId(1L);
        MarqueDTO marqueDTO = marqueMapper.toDto(marque);

        int databaseSizeBeforeCreate = marqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMarques() throws Exception {
        // Initialize the database
        marqueRepository.saveAndFlush(marque);

        // Get all the marqueList
        restMarqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marque.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomMarque").value(hasItem(DEFAULT_NOM_MARQUE)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }

    @Test
    @Transactional
    void getMarque() throws Exception {
        // Initialize the database
        marqueRepository.saveAndFlush(marque);

        // Get the marque
        restMarqueMockMvc
            .perform(get(ENTITY_API_URL_ID, marque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(marque.getId().intValue()))
            .andExpect(jsonPath("$.nomMarque").value(DEFAULT_NOM_MARQUE))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)));
    }

    @Test
    @Transactional
    void getNonExistingMarque() throws Exception {
        // Get the marque
        restMarqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMarque() throws Exception {
        // Initialize the database
        marqueRepository.saveAndFlush(marque);

        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();

        // Update the marque
        Marque updatedMarque = marqueRepository.findById(marque.getId()).get();
        // Disconnect from session so that the updates on updatedMarque are not directly saved in db
        em.detach(updatedMarque);
        updatedMarque.nomMarque(UPDATED_NOM_MARQUE).logo(UPDATED_LOGO).logoContentType(UPDATED_LOGO_CONTENT_TYPE);
        MarqueDTO marqueDTO = marqueMapper.toDto(updatedMarque);

        restMarqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marqueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marqueDTO))
            )
            .andExpect(status().isOk());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
        Marque testMarque = marqueList.get(marqueList.size() - 1);
        assertThat(testMarque.getNomMarque()).isEqualTo(UPDATED_NOM_MARQUE);
        assertThat(testMarque.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testMarque.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingMarque() throws Exception {
        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();
        marque.setId(count.incrementAndGet());

        // Create the Marque
        MarqueDTO marqueDTO = marqueMapper.toDto(marque);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marqueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMarque() throws Exception {
        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();
        marque.setId(count.incrementAndGet());

        // Create the Marque
        MarqueDTO marqueDTO = marqueMapper.toDto(marque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMarque() throws Exception {
        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();
        marque.setId(count.incrementAndGet());

        // Create the Marque
        MarqueDTO marqueDTO = marqueMapper.toDto(marque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarqueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marqueDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMarqueWithPatch() throws Exception {
        // Initialize the database
        marqueRepository.saveAndFlush(marque);

        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();

        // Update the marque using partial update
        Marque partialUpdatedMarque = new Marque();
        partialUpdatedMarque.setId(marque.getId());

        restMarqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarque))
            )
            .andExpect(status().isOk());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
        Marque testMarque = marqueList.get(marqueList.size() - 1);
        assertThat(testMarque.getNomMarque()).isEqualTo(DEFAULT_NOM_MARQUE);
        assertThat(testMarque.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testMarque.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateMarqueWithPatch() throws Exception {
        // Initialize the database
        marqueRepository.saveAndFlush(marque);

        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();

        // Update the marque using partial update
        Marque partialUpdatedMarque = new Marque();
        partialUpdatedMarque.setId(marque.getId());

        partialUpdatedMarque.nomMarque(UPDATED_NOM_MARQUE).logo(UPDATED_LOGO).logoContentType(UPDATED_LOGO_CONTENT_TYPE);

        restMarqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarque))
            )
            .andExpect(status().isOk());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
        Marque testMarque = marqueList.get(marqueList.size() - 1);
        assertThat(testMarque.getNomMarque()).isEqualTo(UPDATED_NOM_MARQUE);
        assertThat(testMarque.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testMarque.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingMarque() throws Exception {
        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();
        marque.setId(count.incrementAndGet());

        // Create the Marque
        MarqueDTO marqueDTO = marqueMapper.toDto(marque);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, marqueDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMarque() throws Exception {
        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();
        marque.setId(count.incrementAndGet());

        // Create the Marque
        MarqueDTO marqueDTO = marqueMapper.toDto(marque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marqueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMarque() throws Exception {
        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();
        marque.setId(count.incrementAndGet());

        // Create the Marque
        MarqueDTO marqueDTO = marqueMapper.toDto(marque);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarqueMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(marqueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMarque() throws Exception {
        // Initialize the database
        marqueRepository.saveAndFlush(marque);

        int databaseSizeBeforeDelete = marqueRepository.findAll().size();

        // Delete the marque
        restMarqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, marque.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
