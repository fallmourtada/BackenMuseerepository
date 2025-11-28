package com.ecommerce.backendmuseeproject.dto;

import com.ecommerce.backendmuseeproject.enumeration.TypeMedia;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class MediaDTO {
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeMedia type;

    private String url;

    private OeuvreDTO oeuvreDTO;
}
