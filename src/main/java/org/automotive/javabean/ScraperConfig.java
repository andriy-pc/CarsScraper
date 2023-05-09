package org.automotive.javabean;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ScraperConfig {

  private String category;
  private String make;
  private String model;
  private String minYear;
  private String maxYear;
  private String minPrice;
  private String maxPrice;
}
