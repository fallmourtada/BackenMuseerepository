package com.ecommerce.backendmuseeproject.controller;

import com.ecommerce.backendmuseeproject.dto.SaveUserDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateUserDTO;
import com.ecommerce.backendmuseeproject.dto.UserDTO;
import com.ecommerce.backendmuseeproject.enumeration.UserRole;
import com.ecommerce.backendmuseeproject.services.UserService.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Tag(name = "Gestion des utilisateurs", description = "Endpoints pour gérer les utilisateurs de la plateforme.")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Operation(summary = "Obtenir tous les utilisateurs", description = "Récupère la liste complète des utilisateurs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getALlUsers();
        return ResponseEntity.ok(users);
    }




    @Operation(summary = "Obtenir un utilisateur par ID", description = "Récupère les détails d'un utilisateur spécifique.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }



    @Operation(summary = "Créer un nouvel utilisateur", description = "Ajoute un utilisateur à la plateforme.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping("/Visiteur")
    public ResponseEntity<UserDTO> saveVisiteur(@RequestBody SaveUserDTO saveUserDTO) {
        UserDTO user = userService.saveUser(saveUserDTO,UserRole.VISITEUR);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }



    @Operation(summary = "Créer un nouvel utilisateur", description = "Ajoute un utilisateur à la plateforme.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping("/Chercheur")
    public ResponseEntity<UserDTO> saveChercheur(@RequestBody SaveUserDTO saveUserDTO) {
        UserDTO user = userService.saveUser(saveUserDTO,UserRole.CHERCHEUR);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }



    @Operation(summary = "Créer un nouvel utilisateur", description = "Ajoute un utilisateur à la plateforme.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping("/Admin")
    public ResponseEntity<UserDTO> saveAdmin(@RequestBody SaveUserDTO saveUserDTO) {
        UserDTO user = userService.saveUser(saveUserDTO,UserRole.ADMIN);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }



    @Operation(summary = "Mettre à jour un utilisateur", description = "Modifie les informations d'un utilisateur existant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO) {
        UserDTO user = userService.updateUser(id, updateUserDTO);
        return ResponseEntity.ok(user);
    }



    @Operation(summary = "Supprimer un utilisateur", description = "Supprime un utilisateur de la plateforme.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}