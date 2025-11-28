package com.ecommerce.backendmuseeproject.dto;

import com.ecommerce.backendmuseeproject.enumeration.UserRole;
import lombok.Data;

@Data
public class SaveUserDTO {

    private String nom;

    private String prenom;

    private String email;

    private String motDePasse;

    //private UserRole role;

}
