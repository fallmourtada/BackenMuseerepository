package com.ecommerce.backendmuseeproject.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HistoriqueDTO {
    private Long id;

    private LocalDate dateCreation;

    private String createur;

    private OeuvreDTO oeuvreDTO;
}
