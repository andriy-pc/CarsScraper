package org.automotive.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//TODO: ignore null fields

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CarInfo {

    private String title;
    private String price;
    private String mileage;
    private String location;
    private String fuelType;
    private String transmissionType;
    private String link;

}
