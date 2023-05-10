package org.automotive.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.automotive.dao.entity.ScraperConfig;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ScraperConfigLoaderDAO implements ScraperConfigLoader {

  @PersistenceContext(unitName = "org.automotive.CarsScraper")
  EntityManager entityManager;

  @Override
  @Transactional
  public Optional<ScraperConfig> loadConfig(String site) {
    return entityManager
        .createQuery(
            "SELECT sc FROM ScraperConfig sc JOIN FETCH sc.site s WHERE s.url = :site",
            ScraperConfig.class)
        .setParameter("site", site)
        .getResultStream()
        .findFirst();
  }

  @Override
  public List<ScraperConfig> loadAllConfigs() {
    return entityManager
        .createQuery("SELECT sc FROM ScraperConfig sc", ScraperConfig.class)
        .getResultList();
  }
}
