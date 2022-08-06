package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Employe;
import com.mycompany.myapp.repository.EmployeRepository;
import com.mycompany.myapp.service.dto.EmployeDTO;
import com.mycompany.myapp.service.mapper.EmployeMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link EmployeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeResourceIT {

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

    private static final String DEFAULT_POSTE = "AAAAAAAAAA";
    private static final String UPDATED_POSTE = "BBBBBBBBBB";

    private static final Float DEFAULT_SALAIRE = 1F;
    private static final Float UPDATED_SALAIRE = 2F;

    private static final LocalDate DEFAULT_DATE_EMBAUCHE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EMBAUCHE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/employes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private EmployeMapper employeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeMockMvc;

    private Employe employe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employe createEntity(EntityManager em) {
        Employe employe = new Employe()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL)
            .adresse(DEFAULT_ADRESSE)
            .age(DEFAULT_AGE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .poste(DEFAULT_POSTE)
            .salaire(DEFAULT_SALAIRE)
            .dateEmbauche(DEFAULT_DATE_EMBAUCHE);
        return employe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employe createUpdatedEntity(EntityManager em) {
        Employe employe = new Employe()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .adresse(UPDATED_ADRESSE)
            .age(UPDATED_AGE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .poste(UPDATED_POSTE)
            .salaire(UPDATED_SALAIRE)
            .dateEmbauche(UPDATED_DATE_EMBAUCHE);
        return employe;
    }

    @BeforeEach
    public void initTest() {
        employe = createEntity(em);
    }

    @Test
    @Transactional
    void createEmploye() throws Exception {
        int databaseSizeBeforeCreate = employeRepository.findAll().size();
        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);
        restEmployeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeCreate + 1);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEmploye.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testEmploye.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testEmploye.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmploye.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testEmploye.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testEmploye.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testEmploye.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testEmploye.getPoste()).isEqualTo(DEFAULT_POSTE);
        assertThat(testEmploye.getSalaire()).isEqualTo(DEFAULT_SALAIRE);
        assertThat(testEmploye.getDateEmbauche()).isEqualTo(DEFAULT_DATE_EMBAUCHE);
    }

    @Test
    @Transactional
    void createEmployeWithExistingId() throws Exception {
        // Create the Employe with an existing ID
        employe.setId(1L);
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        int databaseSizeBeforeCreate = employeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployes() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList
        restEmployeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].poste").value(hasItem(DEFAULT_POSTE)))
            .andExpect(jsonPath("$.[*].salaire").value(hasItem(DEFAULT_SALAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateEmbauche").value(hasItem(DEFAULT_DATE_EMBAUCHE.toString())));
    }

    @Test
    @Transactional
    void getEmploye() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get the employe
        restEmployeMockMvc
            .perform(get(ENTITY_API_URL_ID, employe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employe.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.poste").value(DEFAULT_POSTE))
            .andExpect(jsonPath("$.salaire").value(DEFAULT_SALAIRE.doubleValue()))
            .andExpect(jsonPath("$.dateEmbauche").value(DEFAULT_DATE_EMBAUCHE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEmploye() throws Exception {
        // Get the employe
        restEmployeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmploye() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        int databaseSizeBeforeUpdate = employeRepository.findAll().size();

        // Update the employe
        Employe updatedEmploye = employeRepository.findById(employe.getId()).get();
        // Disconnect from session so that the updates on updatedEmploye are not directly saved in db
        em.detach(updatedEmploye);
        updatedEmploye
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .adresse(UPDATED_ADRESSE)
            .age(UPDATED_AGE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .poste(UPDATED_POSTE)
            .salaire(UPDATED_SALAIRE)
            .dateEmbauche(UPDATED_DATE_EMBAUCHE);
        EmployeDTO employeDTO = employeMapper.toDto(updatedEmploye);

        restEmployeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEmploye.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testEmploye.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testEmploye.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmploye.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEmploye.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testEmploye.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testEmploye.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testEmploye.getPoste()).isEqualTo(UPDATED_POSTE);
        assertThat(testEmploye.getSalaire()).isEqualTo(UPDATED_SALAIRE);
        assertThat(testEmploye.getDateEmbauche()).isEqualTo(UPDATED_DATE_EMBAUCHE);
    }

    @Test
    @Transactional
    void putNonExistingEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeWithPatch() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        int databaseSizeBeforeUpdate = employeRepository.findAll().size();

        // Update the employe using partial update
        Employe partialUpdatedEmploye = new Employe();
        partialUpdatedEmploye.setId(employe.getId());

        partialUpdatedEmploye
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .poste(UPDATED_POSTE)
            .salaire(UPDATED_SALAIRE);

        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploye.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploye))
            )
            .andExpect(status().isOk());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEmploye.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testEmploye.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testEmploye.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmploye.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testEmploye.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testEmploye.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testEmploye.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testEmploye.getPoste()).isEqualTo(UPDATED_POSTE);
        assertThat(testEmploye.getSalaire()).isEqualTo(UPDATED_SALAIRE);
        assertThat(testEmploye.getDateEmbauche()).isEqualTo(DEFAULT_DATE_EMBAUCHE);
    }

    @Test
    @Transactional
    void fullUpdateEmployeWithPatch() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        int databaseSizeBeforeUpdate = employeRepository.findAll().size();

        // Update the employe using partial update
        Employe partialUpdatedEmploye = new Employe();
        partialUpdatedEmploye.setId(employe.getId());

        partialUpdatedEmploye
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .adresse(UPDATED_ADRESSE)
            .age(UPDATED_AGE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .poste(UPDATED_POSTE)
            .salaire(UPDATED_SALAIRE)
            .dateEmbauche(UPDATED_DATE_EMBAUCHE);

        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploye.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploye))
            )
            .andExpect(status().isOk());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEmploye.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testEmploye.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testEmploye.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmploye.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEmploye.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testEmploye.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testEmploye.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testEmploye.getPoste()).isEqualTo(UPDATED_POSTE);
        assertThat(testEmploye.getSalaire()).isEqualTo(UPDATED_SALAIRE);
        assertThat(testEmploye.getDateEmbauche()).isEqualTo(UPDATED_DATE_EMBAUCHE);
    }

    @Test
    @Transactional
    void patchNonExistingEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // Create the Employe
        EmployeDTO employeDTO = employeMapper.toDto(employe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmploye() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        int databaseSizeBeforeDelete = employeRepository.findAll().size();

        // Delete the employe
        restEmployeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
