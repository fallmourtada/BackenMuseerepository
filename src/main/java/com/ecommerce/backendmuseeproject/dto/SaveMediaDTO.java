package com.ecommerce.backendmuseeproject.dto;

import com.ecommerce.backendmuseeproject.enumeration.TypeMedia;
import lombok.Data;

@Data
public class SaveMediaDTO {
    private TypeMedia type;

    private String url;

    private Long oeuvreId;
}
