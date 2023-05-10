package org.automotive.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
  private String lastScrapedKey;
}
