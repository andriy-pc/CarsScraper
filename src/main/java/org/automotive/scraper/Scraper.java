package org.automotive.scraper;

import java.util.Map;
import org.automotive.javabean.CarInfo;

public interface Scraper<T extends CarInfo> {

  String scrape();

  void openSite();

  void applyFilters();

  void search();

  Map<String, T> extractCarsInfo();

  void nextPage();

  boolean proceedSearching();
}
