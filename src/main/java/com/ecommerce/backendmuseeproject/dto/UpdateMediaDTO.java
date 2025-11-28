package com.ecommerce.backendmuseeproject.dto;


import com.ecommerce.backendmuseeproject.enumeration.TypeMedia;
import lombok.Data;

@Data
public class UpdateMediaDTO {
    private TypeMedia type;

    private String url;
}
