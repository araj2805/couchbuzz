package org.techbuzz.lab.web.rest;

import org.techbuzz.lab.CouchbuzzApp;
import org.techbuzz.lab.domain.Country;
import org.techbuzz.lab.repository.CountryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CountryResource} REST controller.
 */
@SpringBootTest(classes = CouchbuzzApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CountryResourceIT {

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_GDP = 1;
    private static final Integer UPDATED_GDP = 2;

    private static final String DEFAULT_UPDATED = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED = "BBBBBBBBBB";

    private static final String DEFAULT_REGION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REGION_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_POPULATION = 1;
    private static final Integer UPDATED_POPULATION = 2;

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private MockMvc restCountryMockMvc;

    private Country country;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createEntity() {
        Country country = new Country()
            .countryName(DEFAULT_COUNTRY_NAME)
            .gdp(DEFAULT_GDP)
            .updated(DEFAULT_UPDATED)
            .regionNumber(DEFAULT_REGION_NUMBER)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .population(DEFAULT_POPULATION)
            .countryCode(DEFAULT_COUNTRY_CODE);
        return country;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createUpdatedEntity() {
        Country country = new Country()
            .countryName(UPDATED_COUNTRY_NAME)
            .gdp(UPDATED_GDP)
            .updated(UPDATED_UPDATED)
            .regionNumber(UPDATED_REGION_NUMBER)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .population(UPDATED_POPULATION)
            .countryCode(UPDATED_COUNTRY_CODE);
        return country;
    }

    @BeforeEach
    public void initTest() {
        countryRepository.deleteAll();
        country = createEntity();
    }

    @Test
    public void createCountry() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // Create the Country
        restCountryMockMvc.perform(post("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isCreated());

        // Validate the Country in the database
        SecurityContextHolder.setContext(TestSecurityContextHolder.getContext());
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate + 1);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testCountry.getGdp()).isEqualTo(DEFAULT_GDP);
        assertThat(testCountry.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testCountry.getRegionNumber()).isEqualTo(DEFAULT_REGION_NUMBER);
        assertThat(testCountry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCountry.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCountry.getPopulation()).isEqualTo(DEFAULT_POPULATION);
        assertThat(testCountry.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
    }

    @Test
    public void createCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // Create the Country with an existing ID
        country.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryMockMvc.perform(post("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        SecurityContextHolder.setContext(TestSecurityContextHolder.getContext());
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllCountries() throws Exception {
        // Initialize the database
        countryRepository.save(country);

        // Get all the countryList
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].gdp").value(hasItem(DEFAULT_GDP)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED)))
            .andExpect(jsonPath("$.[*].regionNumber").value(hasItem(DEFAULT_REGION_NUMBER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].population").value(hasItem(DEFAULT_POPULATION)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)));
    }
    
    @Test
    public void getCountry() throws Exception {
        // Initialize the database
        countryRepository.save(country);

        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", country.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(country.getId()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME))
            .andExpect(jsonPath("$.gdp").value(DEFAULT_GDP))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED))
            .andExpect(jsonPath("$.regionNumber").value(DEFAULT_REGION_NUMBER))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.population").value(DEFAULT_POPULATION))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE));
    }

    @Test
    public void getNonExistingCountry() throws Exception {
        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCountry() throws Exception {
        // Initialize the database
        countryRepository.save(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country
        Country updatedCountry = countryRepository.findById(country.getId()).get();
        updatedCountry
            .countryName(UPDATED_COUNTRY_NAME)
            .gdp(UPDATED_GDP)
            .updated(UPDATED_UPDATED)
            .regionNumber(UPDATED_REGION_NUMBER)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .population(UPDATED_POPULATION)
            .countryCode(UPDATED_COUNTRY_CODE);

        restCountryMockMvc.perform(put("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCountry)))
            .andExpect(status().isOk());

        // Validate the Country in the database
        SecurityContextHolder.setContext(TestSecurityContextHolder.getContext());
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCountry.getGdp()).isEqualTo(UPDATED_GDP);
        assertThat(testCountry.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testCountry.getRegionNumber()).isEqualTo(UPDATED_REGION_NUMBER);
        assertThat(testCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountry.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCountry.getPopulation()).isEqualTo(UPDATED_POPULATION);
        assertThat(testCountry.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
    }

    @Test
    public void updateNonExistingCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Create the Country

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryMockMvc.perform(put("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        SecurityContextHolder.setContext(TestSecurityContextHolder.getContext());
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCountry() throws Exception {
        // Initialize the database
        countryRepository.save(country);

        int databaseSizeBeforeDelete = countryRepository.findAll().size();

        // Delete the country
        restCountryMockMvc.perform(delete("/api/countries/{id}", country.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        SecurityContextHolder.setContext(TestSecurityContextHolder.getContext());
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
