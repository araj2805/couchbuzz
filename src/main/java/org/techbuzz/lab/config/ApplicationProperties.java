package org.techbuzz.lab.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Couchbuzz.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private Couchbase couchbase;

    public Couchbase getCouchbase() {
        return couchbase;
    }

    public void setCouchbase(Couchbase couchbase) {
        this.couchbase = couchbase;
    }
}
