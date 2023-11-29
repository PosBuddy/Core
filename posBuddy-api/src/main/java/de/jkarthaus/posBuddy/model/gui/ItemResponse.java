package de.jkarthaus.posBuddy.model.gui;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Serdeable
public class ItemResponse {

    private String id;
    private String unit;
    private Integer minAge;
    private String dispensingStation;
    private Double price;

}
