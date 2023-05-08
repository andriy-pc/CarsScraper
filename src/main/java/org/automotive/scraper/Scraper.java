package org.automotive.scraper;

public interface Scraper {

  String scrape();

  void openSite();

  void applyFilters();

  void search();

  String extractCarsInfo();

  void nextPage();

  boolean proceedSearching();
}
