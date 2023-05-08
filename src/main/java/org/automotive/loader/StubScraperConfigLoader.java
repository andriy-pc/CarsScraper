package org.automotive.loader;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.automotive.javabean.ScraperConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("pseudo-profiling")
public class StubScraperConfigLoader implements ScraperConfigLoader {
    @Override
    public ScraperConfig loadConfig(String siteName) {
        return ScraperConfig.builder()
                .category("Легкові")
                .make("Volkswagen")
                .minYear("2009")
                .maxYear("2011")
                .build();
    }

    @Override
    public List<ScraperConfig> loadAllConfigs() {
        return new ArrayList<ScraperConfig>() {{
            add(loadConfig(RandomStringUtils.random(10)));
        }};
    }
}
