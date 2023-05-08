package org.automotive.scraper;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.automotive.constants.StringConstants.FAILED_TO_STRINGIFY_SCRAPING_RESULTS;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PreDestroy;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.automotive.javabean.CarInfo;
import org.automotive.javabean.ScraperConfig;
import org.automotive.loader.ScraperConfigLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@Slf4j
@NoArgsConstructor
public abstract class AbstractScraper implements Scraper {

  protected final AtomicInteger scrapedPages = new AtomicInteger(1);

  protected ScraperConfig scraperConfig;
  protected WebDriver webDriver;
  private ObjectMapper pureObjectMapper;

  public AbstractScraper(ScraperConfigLoader scraperConfigLoader, ObjectMapper pureObjectMapper) {
    this.pureObjectMapper = pureObjectMapper;
    this.scraperConfig = scraperConfigLoader.loadConfig("auto.ria.com");
  }

  @Override
  public String scrape() {
    StringBuilder searchResult = new StringBuilder(EMPTY);
    try {
      initSession();
      openSite();
      applyFilters();
      search();
      searchResult = new StringBuilder(extractCarsInfo());
      while (proceedSearching()) {
        scrapedPages.incrementAndGet();
        nextPage();
        searchResult.append(extractCarsInfo());
      }
    } catch (Exception e) {
     log.error("Exception occurred during scraping. Exception: ", e);
    } finally {
      log.warn("Terminating selenium session");
      terminateSession();
    }
    return searchResult.toString();
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

  protected String stringifyCarInfo(CarInfo carInfo) {
    try {
      return pureObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carInfo);
    } catch (Exception e) {
      log.error(
          "Exception occurred during stringification of the car info (title: {})." + "Exception: ",
          carInfo.getTitle(),
          e);
      return FAILED_TO_STRINGIFY_SCRAPING_RESULTS;
    }
  }
}
