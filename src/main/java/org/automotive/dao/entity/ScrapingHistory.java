package org.automotive.dao.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "scraping_history")
public class ScrapingHistory {

  @Id
  private Integer id;
  
  @ManyToOne
  @JoinColumn(name = "scraper_config_id", referencedColumnName = "id")
  private ScraperConfig scraperConfig;
  private String lastScrapedKey;
}
