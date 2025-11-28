package com.ecommerce.backendmuseeproject.services.UserService;

import com.ecommerce.backendmuseeproject.dto.SaveUserDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateUserDTO;
import com.ecommerce.backendmuseeproject.dto.UserDTO;
import com.ecommerce.backendmuseeproject.entites.User;
import com.ecommerce.backendmuseeproject.enumeration.UserRole;
import com.ecommerce.backendmuseeproject.mapper.UserMapper;
import com.ecommerce.backendmuseeproject.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private JdbcTemplate jdbcTemplate;
    private PasswordEncoder passwordEncoder;
    private JdbcUserDetailsManager jdbcUserDetailsManager;


    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User non Trouver Avec Id"+userId));
        userRepository.delete(user);
        log.info("Delete User with Id"+userId);

    }

    @Override
    public List<UserDTO> getALlUsers() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = userList.stream().
                map(userMapper::fromEntity)
                .collect(Collectors.toList());

        return userDTOList;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User non Trouver Avec Id"+userId));
        UserDTO userDTO = userMapper.fromEntity(user);

        return userDTO;
    }

    @Override
    public UserDTO saveUser(SaveUserDTO saveUserDTO, UserRole userRole) {
        // 1. Création dans Spring Security JDBC
        if (!jdbcUserDetailsManager.userExists(saveUserDTO.getEmail())) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(saveUserDTO.getEmail())
                    .password(passwordEncoder.encode(saveUserDTO.getMotDePasse()))
                    .roles(userRole.name())
                    .build();
            jdbcUserDetailsManager.createUser(userDetails);
        } else {
            throw new RuntimeException("L'utilisateur existe déjà dans la base d'authentification.");
        }

        User user = userMapper.fromSaveDTO(saveUserDTO);
        user.setRole(userRole);
        User user1 = userRepository.save(user);
        return userMapper.fromEntity(user1);
    }



    @Override
    public UserDTO updateUser(Long userId, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User non Trouver Avec Id"+userId));
        User user1 = userMapper.partialUpdate(updateUserDTO,user);
        User updatedUser = userRepository.save(user1);
        return userMapper.fromEntity(updatedUser);
    }
}
