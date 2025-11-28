package com.ecommerce.backendmuseeproject.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class DescriptionDTO {

    private Long id;

    private String langue;


    private String texte;

    private OeuvreDTO oeuvreDTO;

    //private Date date;
}
