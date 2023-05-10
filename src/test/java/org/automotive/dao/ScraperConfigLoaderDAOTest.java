package org.automotive.dao;

import static org.automotive.constants.StringConstants.AUTORIA_SITE_NAME;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.automotive.configuration.ApplicationConfiguration;
import org.automotive.dao.entity.ScraperConfig;
import org.automotive.dao.entity.Site;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@ActiveProfiles(profiles = "test")
class ScraperConfigLoaderDAOTest {

  @Autowired
  ScraperConfigLoader scraperConfigLoaderDAO;

  ScraperConfig stubConfig = ScraperConfig.builder()
          .id(1)
          .category("Легкові")
          .make("Volkswagen")
          .minYear("2009")
          .maxYear("2011")
          .site(Site.builder()
                  .id(1)
                  .url("auto.ria.com")
                  .build())
          .build();

  @Test
  void loadConfig() {
    Optional<ScraperConfig> test = scraperConfigLoaderDAO.loadConfig(AUTORIA_SITE_NAME);

    assertTrue(test.isPresent());
    assertEquals(stubConfig, test.get());
  }

  @Test
  @Transactional
  void loadAllConfigs() {
    List<ScraperConfig> test = scraperConfigLoaderDAO.loadAllConfigs();

    assertEquals(1, test.size());
    assertEquals(stubConfig, test.get(0));
  }
}
