package org.automotive.dao;



import org.automotive.dao.entity.ScrapingHistory;

import java.util.Set;

public interface ScrapingHistoryDAO {
    
    Set<String> getLastScrapedKeysForConfig(Integer configId);

    void removeScrapedKeysForConfig(Integer configId);

    void saveScrapingHistory(ScrapingHistory scrapingHistory);

}
