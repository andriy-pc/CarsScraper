package org.automotive.executor;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.automotive.scraper.Scraper;
import org.jvnet.hk2.annotations.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ScrapingExecutor {

  private List<Scraper> scrapers;

  public String execute() {
    log.info("Scraping in progress...");
    return scrapers.stream()
        .map(Scraper::scrape)
        .collect(Collectors.joining(System.lineSeparator()));
  }
}
