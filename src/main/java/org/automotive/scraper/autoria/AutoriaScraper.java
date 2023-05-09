package org.automotive.scraper.autoria;

import static org.automotive.constants.EnvVarNames.AUTORIA_PAGES_TO_SCRAPE_ENV_VAR_NAME;
import static org.automotive.constants.StringConstants.*;
import static org.automotive.scraper.autoria.AutoriaStringConstants.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.automotive.javabean.CarInfo;
import org.automotive.loader.ScraperConfigLoader;
import org.automotive.scraper.AbstractScraper;
import org.automotive.utils.EnvVarUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class AutoriaScraper extends AbstractScraper {

  public AutoriaScraper(ScraperConfigLoader scraperConfigLoader, ObjectMapper pureObjectMapper) {
    super(scraperConfigLoader, pureObjectMapper, AUTORIA_SITE_NAME);
  }

  @Override
  public void openSite() {
    webDriver.get(AUTORIA_URL);
  }

  @Override
  public void applyFilters() {
    filterCategory();
    filterMake();
    filterModel();
    filterYears();
    filterPrice();
  }

  private void filterCategory() {
    if (StringUtils.isEmpty(scraperConfig.getCategory())) {
      return;
    }
    Select drpCategories = new Select(webDriver.findElement(By.id(ID_OF_CATEGORIES_HTML_ELEMENT)));
    drpCategories.selectByVisibleText(scraperConfig.getCategory());
  }

  private void filterMake() {
    if (StringUtils.isEmpty(scraperConfig.getMake())) {
      return;
    }
    webDriver.findElement(By.id(ID_OF_MAKE_HTML_ELEMENT)).sendKeys(scraperConfig.getMake());
    new WebDriverWait(webDriver, Duration.of(5, ChronoUnit.SECONDS))
        .until(
            driver ->
                driver
                    .findElement(By.className(CLASSNAME_OF_AUTOCOMPLETE_SELECT_HTML_ELEMENT))
                    .findElement(By.className(BOLD_CLASSNAME))
                    .isDisplayed());

    webDriver
        .findElement(By.className(CLASSNAME_OF_AUTOCOMPLETE_SELECT_HTML_ELEMENT))
        .findElement(By.className(BOLD_CLASSNAME))
        .click();
  }

  private void filterModel() {
    if (StringUtils.isEmpty(scraperConfig.getModel())) {
      return;
    }
    throw new NotImplementedException();
  }

  private void filterYears() {
    if (StringUtils.isEmpty(scraperConfig.getMinYear())
        && StringUtils.isEmpty(scraperConfig.getMaxYear())) {
      return;
    }
    WebElement yearElement =
        webDriver.findElement(By.className(CLASSNAME_OF_YEAR_SELECTOR_HTML_ELEMENT));
    yearElement.click();
    if (StringUtils.isEmpty(scraperConfig.getMinYear())) {
      new Select(webDriver.findElement(By.id(ID_OF_MIN_YEAR_HTML_ELEMENT)))
          .selectByVisibleText(scraperConfig.getMinYear());
    }
    if (StringUtils.isEmpty(scraperConfig.getMaxYear())) {
      new Select(webDriver.findElement(By.id(ID_OF_MAX_YEAR_HTML_ELEMENT)))
          .selectByVisibleText(scraperConfig.getMaxYear());
    }
    yearElement.click();
  }

  private void filterPrice() {
    if (StringUtils.isEmpty(scraperConfig.getMinPrice())
        && StringUtils.isEmpty(scraperConfig.getMaxPrice())) {
      return;
    }
    if (StringUtils.isEmpty(scraperConfig.getMinPrice())) {
      // TODO: implement
    }
    if (StringUtils.isEmpty(scraperConfig.getMaxPrice())) {
      // TODO: implement
    }
  }

  @Override
  public void search() {
    webDriver
        .findElement(By.className(CLASSNAME_OF_SEARCH_BUTTON_ON_MAIN_HTML_PAGE))
        .findElement(By.tagName(BUTTON_TAG_NAME))
        .click();

    new WebDriverWait(webDriver, Duration.of(5, ChronoUnit.SECONDS))
        .until(
            driver -> driver.findElement(By.id(ID_OF_SEARCH_RESULTS_HTML_ELEMENT)).isDisplayed());
  }

  @Override
  public String extractCarsInfo() {
    List<WebElement> cars = webDriver.findElements(By.className(CLASSNAME_OF_SINGLE_RESULT_ENTRY));
    return cars.stream()
        .map(this::extractCarInfo)
        .collect(Collectors.joining(System.lineSeparator()));
  }

  private String extractCarInfo(WebElement carWebElement) {
    String title =
        carWebElement
            .findElement(By.className(CLASSNAME_OF_TITLE_OF_SINGLE_RESULT))
            .findElement(By.className(BOLD_CLASSNAME))
            .getText();

    String price =
        carWebElement
            .findElement(By.className(CLASSNAME_OF_PRICE_OF_SINGLE_RESULT))
            .findElements(By.className(BOLD_CLASSNAME))
            .stream()
            .map(WebElement::getText)
            .collect(Collectors.joining(StringUtils.SPACE));

    WebElement definitionData =
        webDriver.findElement(By.className(CLASSNAME_OF_DETAILS_OF_SNIGLE_SEARCH_RESULT));

    String mileage =
        definitionData.findElement(By.xpath(MILEAGE_XPATH_FROM_DEFINITION_DATA)).getText();
    String location =
        definitionData.findElement(By.xpath(LOCATION_XPATH_FROM_DEFINITION_DATA)).getText();
    String fuelType =
        definitionData.findElement(By.xpath(FUEL_XPATH_FROM_DEFINITION_DATA)).getText();
    String transmissionType =
        definitionData.findElement(By.xpath(TRANSMISSION_XPATH_FROM_DEFINITION_DATA)).getText();

    String link =
        carWebElement
            .findElement(By.className(CLASSNAME_OF_ELEMENT_WITH_HREF_OF_SINGLE_RESULT))
            .getAttribute(HREF_ATTRIBUTE_NAME);

    return stringifyCarInfo(
        CarInfo.builder()
            .title(title)
            .price(price)
            .mileage(mileage)
            .location(location)
            .fuelType(fuelType)
            .transmissionType(transmissionType)
            .link(link)
            .build());
  }

  @Override
  public void nextPage() {
    webDriver.get(
        webDriver
            .findElement(By.className(NEXT_PAGE_BUTTON_CLASSNAME))
            .getAttribute(HREF_ATTRIBUTE_NAME));
  }

  @Override
  public boolean proceedSearching() {
    return scrapedPages.get()
        < Integer.parseInt(
            EnvVarUtils.getStringOrDefault(
                AUTORIA_PAGES_TO_SCRAPE_ENV_VAR_NAME, DEFAULT_COUNT_OF_PAGES_TO_SCRAPE));
  }
}
