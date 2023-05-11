package org.automotive.scraper;

import static java.lang.System.lineSeparator;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.automotive.constants.StringConstants.FAILED_TO_STRINGIFY_SCRAPING_RESULTS;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.annotation.PreDestroy;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.automotive.dao.ScraperConfigLoader;
import org.automotive.dao.ScrapingHistoryDAO;
import org.automotive.dao.entity.ScraperConfig;
import org.automotive.dao.entity.ScrapingHistory;
import org.automotive.javabean.CarInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@NoArgsConstructor
@Profile("!test")
public abstract class AbstractScraper<T extends CarInfo> implements Scraper<T> {

  protected final AtomicInteger scrapedPagesCount = new AtomicInteger(1);
  protected ScraperConfig scraperConfig;
  protected WebDriver webDriver;
  private ObjectMapper pureObjectMapper;
  ScrapingHistoryDAO scrapingHistoryDAO;
  protected Set<String> lastScrapedKeys;
  protected Set<String> scrapedKeys = new HashSet<>();
  protected HashMap<String, T> keyPetScrapingResult = new HashMap<>();

  public AbstractScraper(
      ScraperConfigLoader scraperConfigLoader,
      ObjectMapper pureObjectMapper,
      String siteName,
      ScrapingHistoryDAO scrapingHistoryDAO) {
    this.pureObjectMapper = pureObjectMapper;
    this.scraperConfig = scraperConfigLoader.loadConfig(siteName).orElse(null);
    this.scrapingHistoryDAO = scrapingHistoryDAO;
    this.lastScrapedKeys = scrapingHistoryDAO.getLastScrapedKeysForConfig(scraperConfig.getId());
  }

  @Override
  public String scrape() {
    clearScrapedKeys();
    clearScrapedResults();
    try {
      initSession();
      openSite();
      applyFilters();
      search();
      keyPetScrapingResult.putAll(extractCarsInfo());
      while (proceedSearching()) {
        scrapedPagesCount.incrementAndGet();
        nextPage();
        keyPetScrapingResult.putAll(extractCarsInfo());
      }
      removeScrapedDuplicates();
      saveScrapedKeys();
    } catch (Exception e) {
      log.error("Exception occurred during scraping. Exception: ", e);
    } finally {
      log.warn("Terminating selenium session");
      terminateSession();
    }
    return stringifyScrapingResults();
  }

  @Override
  public boolean proceedSearching() {
    return firstScraping() ? maxPagesWereScraped() : duplicateOrMaxPagesScraped();
  }

  private boolean duplicateOrMaxPagesScraped() {
    lastScrapedKeys.retainAll(
        scrapedKeys); // computes the join of the two sets, as it removes all elements from
    // lastScrapedKeys that
    // aren't in scrapedKeys.
    return !lastScrapedKeys.isEmpty() || maxPagesWereScraped();
  }

  private void clearScrapedKeys() {
    scrapedKeys.clear();
  }

  private void clearScrapedResults() {
    keyPetScrapingResult.clear();
  }

  protected void initSession() {
    log.info("Initiating selenium session");
    webDriver = new ChromeDriver();
    log.info("Selenium session initialized successfully");
  }

  @PreDestroy
  private void terminateSession() {
    try {
      log.info("Terminating selenium session");
      webDriver.close();
      webDriver.quit();
      log.info("Selenium session terminated successfully");
    } catch (Exception e) {
      log.error("Exception occurred during terminating selenium session. Exception: ", e);
    }
  }

  protected abstract void fillScrapedKeys(String... keys);

  protected void removeScrapedDuplicates() {
    lastScrapedKeys.retainAll(scrapedKeys);
    lastScrapedKeys.forEach(keyPetScrapingResult::remove);
  }

  @Transactional
  void saveScrapedKeys() {
    scrapingHistoryDAO.removeScrapedKeysForConfig(scraperConfig.getId());
    scrapedKeys.stream()
        .map(this::buildScrapingHistory)
        .forEach(scrapingHistoryDAO::saveScrapingHistory);
  }

  private ScrapingHistory buildScrapingHistory(String lastScrapedKey) {
    return ScrapingHistory.builder()
        .lastScrapedKey(lastScrapedKey)
        .scraperConfig(ScraperConfig.builder().id(scraperConfig.getId()).build())
        .build();
  }

  protected boolean firstScraping() {
    return isEmpty(lastScrapedKeys);
  }

  protected boolean maxPagesWereScraped() {
    return scrapedPagesCount.get() >= scraperConfig.getFirstRunPagesCount();
  }

  private String stringifyScrapingResults() {
    return keyPetScrapingResult.values().stream()
        .map(this::stringifyCarInfo)
        .collect(Collectors.joining(lineSeparator()));
  }

  protected String stringifyCarInfo(T carInfo) {
    try {
      return pureObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carInfo);
    } catch (Exception e) {
      log.error(
          "Exception occurred during stringification of the car info (title: {}). Exception: ",
          carInfo.getTitle(),
          e);
      return FAILED_TO_STRINGIFY_SCRAPING_RESULTS;
    }
  }
}
