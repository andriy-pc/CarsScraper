package org.automotive.dao;

import java.util.List;
import java.util.Optional;
import org.automotive.dao.entity.ScraperConfig;

public interface ScraperConfigLoader extends ConfigLoader<ScraperConfig> {

  Optional<ScraperConfig> loadConfig(String site);

  List<ScraperConfig> loadAllConfigs();
}
