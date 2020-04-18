package org.techbuzz.lab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {

    @Autowired
    ApplicationProperties properties;

    @Override
    protected List<String> getBootstrapHosts() {
        return Arrays.asList(properties.getCouchbase().getHostname());
    }

    @Override
    protected String getBucketName() {
        return properties.getCouchbase().getBucket();
    }

    @Override
    protected String getBucketPassword() {
        return properties.getCouchbase().getPassword();
    }

    @Override
    protected String getUsername() {
        return properties.getCouchbase().getUsername();
    }
}

