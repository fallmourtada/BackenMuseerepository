package com.ecommerce.backendmuseeproject.dto;

import com.ecommerce.backendmuseeproject.enumeration.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String nom;

    private String prenom;

    private String email;

    private UserRole role;
}
