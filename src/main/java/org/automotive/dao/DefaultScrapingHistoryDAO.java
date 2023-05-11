package org.automotive.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

import org.automotive.dao.entity.ScrapingHistory;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultScrapingHistoryDAO implements ScrapingHistoryDAO {

  @PersistenceContext private EntityManager entityManager;

  @Override
  public Set<String> getLastScrapedKeysForConfig(Integer configId) {
    return new HashSet<>(
        entityManager
            .createQuery(
                "SELECT h.lastScrapedKey FROM ScrapingHistory h JOIN FETCH h.scraperConfig c WHERE c.id = :configId",
                String.class)
            .setParameter("configId", configId)
            .getResultList());
  }

  @Override
  public void removeScrapedKeysForConfig(Integer configId) {
    entityManager
        .createQuery("DELETE ScrapingHistory h WHERE h.scraperConfig.id = :configId")
        .executeUpdate();
  }

  @Override
  public void saveScrapingHistory(ScrapingHistory scrapingHistory) {
    entityManager.persist(scrapingHistory);
  }
}
