package org.automotive.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jvnet.hk2.annotations.Service;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScraperConfig {

    private String category;
    private String make;
    private String model;
    private String minYear;
    private String maxYear;
    private String minPrice;
    private String maxPrice;
}
