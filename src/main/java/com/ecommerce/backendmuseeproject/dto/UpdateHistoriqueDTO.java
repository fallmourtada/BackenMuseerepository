package com.ecommerce.backendmuseeproject.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateHistoriqueDTO {
    private String createur;
    private LocalDate dateCreation;
}
