package org.automotive.loader;

import org.apache.commons.lang3.NotImplementedException;
import org.automotive.javabean.ScraperConfig;
import org.springframework.stereotype.Component;

@Component
public class MongoDBConfigLoader implements DBConfigLoader<ScraperConfig> {
  @Override
  public ScraperConfig loadConfig() {
    throw new NotImplementedException();
  }
}
