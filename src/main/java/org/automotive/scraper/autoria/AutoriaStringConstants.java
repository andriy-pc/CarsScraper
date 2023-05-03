package org.automotive.scraper.autoria;

public interface AutoriaStringConstants {

  String AUTORIA_URL = "https://auto.ria.com/uk/";
  String ID_OF_MIN_YEAR_HTML_ELEMENT = "yearFrom";
  String ID_OF_MAX_YEAR_HTML_ELEMENT = "yearTo";
  String CLASSNAME_OF_SEARCH_BUTTON_ON_MAIN_HTML_PAGE = "footer-form";
  String ID_OF_SEARCH_RESULTS_HTML_ELEMENT = "searchResults";
  String CLASSNAME_OF_SINGLE_RESULT_ENTRY = "ticket-item";
  String CLASSNAME_OF_TITLE_OF_SINGLE_RESULT = "head-ticket";
  String CLASSNAME_OF_PRICE_OF_SINGLE_RESULT = "price-ticket";
  String CLASSNAME_OF_DETAILS_OF_SNIGLE_SEARCH_RESULT = "definition-data";
  String MILEAGE_XPATH_FROM_DEFINITION_DATA = ".//li[1]";
  String LOCATION_XPATH_FROM_DEFINITION_DATA = ".//li[2]";
  String FUEL_XPATH_FROM_DEFINITION_DATA = ".//li[3]";
  String TRANSMISSION_XPATH_FROM_DEFINITION_DATA = ".//li[4]";
  String CLASSNAME_OF_ELEMENT_WITH_HREF_OF_SINGLE_RESULT = "m-link-ticket";
  String ID_OF_CATEGORIES_HTML_ELEMENT = "categories";
  String ID_OF_MAKE_HTML_ELEMENT = "brandTooltipBrandAutocompleteInput-brand";
  String CLASSNAME_OF_AUTOCOMPLETE_SELECT_HTML_ELEMENT = "autocomplete-select";
  String BOLD_CLASSNAME = "bold";
  String CLASSNAME_OF_YEAR_SELECTOR_HTML_ELEMENT = "e-year";
}
