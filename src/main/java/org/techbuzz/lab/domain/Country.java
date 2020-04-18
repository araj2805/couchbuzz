package org.techbuzz.lab.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.IdPrefix;
import com.couchbase.client.java.repository.annotation.Field;

import java.io.Serializable;
import java.util.Objects;

import static org.techbuzz.lab.config.Constants.ID_DELIMITER;
import static org.springframework.data.couchbase.core.mapping.id.GenerationStrategy.UNIQUE;

/**
 * A Country.
 */
@Document
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String PREFIX = "country";

    @SuppressWarnings("unused")
    @IdPrefix
    private String prefix = PREFIX;

    @Id
    @GeneratedValue(strategy = UNIQUE, delimiter = ID_DELIMITER)
    private String id;

    @Field("country_name")
    private String countryName;

    @Field("gdp")
    private Integer gdp;

    @Field("updated")
    private String updated;

    @Field("region_number")
    private String regionNumber;

    @Field("name")
    private String name;

    @Field("type")
    private String type;

    @Field("population")
    private Integer population;

    @Field("country_code")
    private String countryCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public Country countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getGdp() {
        return gdp;
    }

    public Country gdp(Integer gdp) {
        this.gdp = gdp;
        return this;
    }

    public void setGdp(Integer gdp) {
        this.gdp = gdp;
    }

    public String getUpdated() {
        return updated;
    }

    public Country updated(String updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getRegionNumber() {
        return regionNumber;
    }

    public Country regionNumber(String regionNumber) {
        this.regionNumber = regionNumber;
        return this;
    }

    public void setRegionNumber(String regionNumber) {
        this.regionNumber = regionNumber;
    }

    public String getName() {
        return name;
    }

    public Country name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public Country type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPopulation() {
        return population;
    }

    public Country population(Integer population) {
        this.population = population;
        return this;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Country countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return id != null && id.equals(((Country) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", gdp=" + getGdp() +
            ", updated='" + getUpdated() + "'" +
            ", regionNumber='" + getRegionNumber() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", population=" + getPopulation() +
            ", countryCode='" + getCountryCode() + "'" +
            "}";
    }
}
