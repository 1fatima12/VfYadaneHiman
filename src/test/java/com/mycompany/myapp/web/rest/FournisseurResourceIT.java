package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Fournisseur;
import com.mycompany.myapp.repository.FournisseurRepository;
import com.mycompany.myapp.service.dto.FournisseurDTO;
import com.mycompany.myapp.service.mapper.FournisseurMapper;
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
 * Integration tests for the {@link FournisseurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FournisseurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ENTREPRISE = "AAAAAAAAAA";
    private static final String UPDATED_ENTREPRISE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_ICE = "AAAAAAAAAA";
    private static final String UPDATED_ICE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fournisseurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private FournisseurMapper fournisseurMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFournisseurMockMvc;

    private Fournisseur fournisseur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fournisseur createEntity(EntityManager em) {
        Fournisseur fournisseur = new Fournisseur()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL)
            .adresse(DEFAULT_ADRESSE)
            .age(DEFAULT_AGE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .entreprise(DEFAULT_ENTREPRISE)
            .fax(DEFAULT_FAX)
            .ice(DEFAULT_ICE);
        return fournisseur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fournisseur createUpdatedEntity(EntityManager em) {
        Fournisseur fournisseur = new Fournisseur()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .adresse(UPDATED_ADRESSE)
            .age(UPDATED_AGE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .entreprise(UPDATED_ENTREPRISE)
            .fax(UPDATED_FAX)
            .ice(UPDATED_ICE);
        return fournisseur;
    }

    @BeforeEach
    public void initTest() {
        fournisseur = createEntity(em);
    }

    @Test
    @Transactional
    void createFournisseur() throws Exception {
        int databaseSizeBeforeCreate = fournisseurRepository.findAll().size();
        // Create the Fournisseur
        FournisseurDTO fournisseurDTO = fournisseurMapper.toDto(fournisseur);
        restFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseurDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeCreate + 1);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFournisseur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testFournisseur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testFournisseur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFournisseur.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testFournisseur.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testFournisseur.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testFournisseur.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testFournisseur.getEntreprise()).isEqualTo(DEFAULT_ENTREPRISE);
        assertThat(testFournisseur.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testFournisseur.getIce()).isEqualTo(DEFAULT_ICE);
    }

    @Test
    @Transactional
    void createFournisseurWithExistingId() throws Exception {
        // Create the Fournisseur with an existing ID
        fournisseur.setId(1L);
        FournisseurDTO fournisseurDTO = fournisseurMapper.toDto(fournisseur);

        int databaseSizeBeforeCreate = fournisseurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFournisseurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFournisseurs() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList
        restFournisseurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].entreprise").value(hasItem(DEFAULT_ENTREPRISE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].ice").value(hasItem(DEFAULT_ICE)));
    }

    @Test
    @Transactional
    void getFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get the fournisseur
        restFournisseurMockMvc
            .perform(get(ENTITY_API_URL_ID, fournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fournisseur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.entreprise").value(DEFAULT_ENTREPRISE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.ice").value(DEFAULT_ICE));
    }

    @Test
    @Transactional
    void getNonExistingFournisseur() throws Exception {
        // Get the fournisseur
        restFournisseurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur
        Fournisseur updatedFournisseur = fournisseurRepository.findById(fournisseur.getId()).get();
        // Disconnect from session so that the updates on updatedFournisseur are not directly saved in db
        em.detach(updatedFournisseur);
        updatedFournisseur
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .adresse(UPDATED_ADRESSE)
            .age(UPDATED_AGE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .entreprise(UPDATED_ENTREPRISE)
            .fax(UPDATED_FAX)
            .ice(UPDATED_ICE);
        FournisseurDTO fournisseurDTO = fournisseurMapper.toDto(updatedFournisseur);

        restFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fournisseurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseurDTO))
            )
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFournisseur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testFournisseur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testFournisseur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFournisseur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testFournisseur.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testFournisseur.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testFournisseur.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testFournisseur.getEntreprise()).isEqualTo(UPDATED_ENTREPRISE);
        assertThat(testFournisseur.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testFournisseur.getIce()).isEqualTo(UPDATED_ICE);
    }

    @Test
    @Transactional
    void putNonExistingFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // Create the Fournisseur
        FournisseurDTO fournisseurDTO = fournisseurMapper.toDto(fournisseur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fournisseurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // Create the Fournisseur
        FournisseurDTO fournisseurDTO = fournisseurMapper.toDto(fournisseur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fournisseurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // Create the Fournisseur
        FournisseurDTO fournisseurDTO = fournisseurMapper.toDto(fournisseur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fournisseurDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFournisseurWithPatch() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur using partial update
        Fournisseur partialUpdatedFournisseur = new Fournisseur();
        partialUpdatedFournisseur.setId(fournisseur.getId());

        partialUpdatedFournisseur
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .fax(UPDATED_FAX)
            .ice(UPDATED_ICE);

        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFournisseur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testFournisseur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testFournisseur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFournisseur.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testFournisseur.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testFournisseur.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testFournisseur.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testFournisseur.getEntreprise()).isEqualTo(DEFAULT_ENTREPRISE);
        assertThat(testFournisseur.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testFournisseur.getIce()).isEqualTo(UPDATED_ICE);
    }

    @Test
    @Transactional
    void fullUpdateFournisseurWithPatch() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur using partial update
        Fournisseur partialUpdatedFournisseur = new Fournisseur();
        partialUpdatedFournisseur.setId(fournisseur.getId());

        partialUpdatedFournisseur
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .adresse(UPDATED_ADRESSE)
            .age(UPDATED_AGE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .entreprise(UPDATED_ENTREPRISE)
            .fax(UPDATED_FAX)
            .ice(UPDATED_ICE);

        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFournisseur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFournisseur))
            )
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFournisseur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testFournisseur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testFournisseur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFournisseur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testFournisseur.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testFournisseur.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testFournisseur.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testFournisseur.getEntreprise()).isEqualTo(UPDATED_ENTREPRISE);
        assertThat(testFournisseur.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testFournisseur.getIce()).isEqualTo(UPDATED_ICE);
    }

    @Test
    @Transactional
    void patchNonExistingFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // Create the Fournisseur
        FournisseurDTO fournisseurDTO = fournisseurMapper.toDto(fournisseur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fournisseurDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fournisseurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // Create the Fournisseur
        FournisseurDTO fournisseurDTO = fournisseurMapper.toDto(fournisseur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fournisseurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();
        fournisseur.setId(count.incrementAndGet());

        // Create the Fournisseur
        FournisseurDTO fournisseurDTO = fournisseurMapper.toDto(fournisseur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFournisseurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fournisseurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeDelete = fournisseurRepository.findAll().size();

        // Delete the fournisseur
        restFournisseurMockMvc
            .perform(delete(ENTITY_API_URL_ID, fournisseur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
