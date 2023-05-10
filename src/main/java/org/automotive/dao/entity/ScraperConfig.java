package org.automotive.dao.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "scraper_config")
public class ScraperConfig {

  @Id
  private Integer id;
  private String category;
  private String make;
  private String model;
  private String minYear;
  private String maxYear;
  private String minPrice;
  private String maxPrice;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "site_id")
  private Site site;
}
