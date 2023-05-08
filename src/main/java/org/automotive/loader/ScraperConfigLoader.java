package org.automotive.loader;

import java.util.List;
import org.automotive.javabean.ScraperConfig;

public interface ScraperConfigLoader extends ConfigLoader<ScraperConfig> {

  ScraperConfig loadConfig(String site);

  List<ScraperConfig> loadAllConfigs();
}
