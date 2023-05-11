package org.automotive.javabean;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CarInfo {

  private String key;
  private String title;
  private String price;
  private String mileage;
  private String location;
  private String fuelType;
  private String transmissionType;
  private String link;
}
