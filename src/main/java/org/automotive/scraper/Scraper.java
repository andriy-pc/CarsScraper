package org.automotive.scraper;

public interface Scraper {

    String scrape();

    void openSite();

    void applyFilters();

    String search();

    void nextPage();

    boolean proceedSearching();

}
