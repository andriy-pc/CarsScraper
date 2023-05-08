package org.automotive.job;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.automotive.executor.ScrapingExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Slf4j
public class ScrapingJob {

  @Autowired
  private ScrapingExecutor scrapingExecutor;


  public void doJob() {
    log.info("Staring scraping...");
    String scrapingResults = scrapingExecutor.execute();
    log.info("Scraping terminated");
    log.info("Sending scraping results");
    //TODO: implement...
    System.out.println("Scraping results: " + scrapingResults);
    log.info("Scraping results sent successfully");
  }
}
