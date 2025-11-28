package com.ecommerce.backendmuseeproject.mapper;


import com.ecommerce.backendmuseeproject.dto.SaveUserDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateUserDTO;
import com.ecommerce.backendmuseeproject.dto.UserDTO;
import com.ecommerce.backendmuseeproject.entites.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserMapper {

    public UserDTO fromEntity(User user) {
        if(user == null) return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setNom(user.getNom());
        userDTO.setPrenom(user.getPrenom());
        userDTO.setRole(user.getRole());
        return userDTO;
    }


    public User fromSaveDTO(SaveUserDTO saveUserDTO) {
        if(saveUserDTO == null) return null;
        User user = new User();
        user.setEmail(saveUserDTO.getEmail());
        user.setNom(saveUserDTO.getNom());
        user.setPrenom(saveUserDTO.getPrenom());
        user.setMotDePasse(saveUserDTO.getMotDePasse());

        return user;
    }


    public User partialUpdate(UpdateUserDTO updateUserDTO,User user ) {
        if(updateUserDTO == null || user == null) return null;

        user.setEmail(updateUserDTO.getEmail());
        user.setNom(updateUserDTO.getNom());
        user.setPrenom(updateUserDTO.getPrenom());
        user.setMotDePasse(updateUserDTO.getMotDePasse());
        user.setRole(updateUserDTO.getRole());
        return user;
    }
}
