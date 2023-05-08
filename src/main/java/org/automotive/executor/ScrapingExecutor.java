package org.automotive.executor;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.automotive.scraper.Scraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScrapingExecutor {

  private List<Scraper> scrapers;

  public ScrapingExecutor() {
  }

  @Autowired
  public ScrapingExecutor(List<Scraper> scrapers) {
    this.scrapers = scrapers;
  }

  public String execute() {
    log.info("Scraping in progress...");
    return scrapers.stream()
        .map(Scraper::scrape)
        .collect(Collectors.joining(System.lineSeparator()));
  }
}
