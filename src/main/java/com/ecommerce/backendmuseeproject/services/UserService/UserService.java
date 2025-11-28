package com.ecommerce.backendmuseeproject.services.UserService;

import com.ecommerce.backendmuseeproject.dto.SaveUserDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateUserDTO;
import com.ecommerce.backendmuseeproject.dto.UserDTO;
import com.ecommerce.backendmuseeproject.enumeration.UserRole;

import java.util.List;

public interface UserService {

    public void deleteUser(Long userId);

    public List<UserDTO> getALlUsers();

    public UserDTO getUserById(Long userId);


    public UserDTO saveUser(SaveUserDTO saveUserDTO, UserRole userRole);

    public UserDTO updateUser(Long userId, UpdateUserDTO updateUserDTO);





}
