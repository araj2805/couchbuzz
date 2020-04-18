package org.techbuzz.lab.repository;

import org.techbuzz.lab.domain.Country;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Couchbase repository for the Country entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryRepository extends N1qlCouchbaseRepository<Country, String> {
}
