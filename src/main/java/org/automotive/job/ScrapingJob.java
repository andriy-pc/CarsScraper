package org.automotive.job;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.automotive.executor.SingleThreadScrapingExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Slf4j
public class ScrapingJob {

  @Autowired
  private SingleThreadScrapingExecutor singleThreadScrapingExecutor;

  public void doJob() {
    log.info("Staring scraping...");
    String scrapingResults = singleThreadScrapingExecutor.execute();
    log.info("Scraping terminated");
    log.info("Scraping results: " + scrapingResults);
  }
}
