package org.automotive.loader;

import org.automotive.javabean.ScraperConfig;

import java.util.List;

public interface ScraperConfigLoader extends ConfigLoader<ScraperConfig> {

  ScraperConfig loadConfig(String site);

  List<ScraperConfig> loadAllConfigs();
}
