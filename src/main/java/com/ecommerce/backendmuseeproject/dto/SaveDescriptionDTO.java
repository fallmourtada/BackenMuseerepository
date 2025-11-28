package com.ecommerce.backendmuseeproject.dto;

import lombok.Data;

@Data
public class SaveDescriptionDTO {
    private String langue;

    private String texte;

    private Long oeuvreId;
}
