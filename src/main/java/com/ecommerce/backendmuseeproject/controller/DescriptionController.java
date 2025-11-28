package com.ecommerce.backendmuseeproject.controller;

import com.ecommerce.backendmuseeproject.services.DescriptionService.DescriptionService;
import com.ecommerce.backendmuseeproject.dto.DescriptionDTO;
import com.ecommerce.backendmuseeproject.dto.SaveDescriptionDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateDescriptionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller pour la gestion des descriptions détaillées des œuvres.
 * Gère les contenus multilingues (texte, audio/vidéo) et est le point d'accès pour l'expérience immersive.
 */
@RestController
@RequestMapping("/api/v1/descriptions")
@Tag(name = "Descriptions des Œuvres", description = "Gestion du contenu multilingue et des textes pour l'expérience immersive.")
@AllArgsConstructor
@CrossOrigin
public class DescriptionController {

    private final DescriptionService descriptionService;



    // ========================================================================
    // ENDPOINTS PUBLICS (ACCES VISITEUR - LECTURE)
    // ========================================================================

    @Operation(summary = "Récupère toutes les descriptions",
            description = "Utile pour le CMS ou le débogage. L'accès est typiquement restreint aux administrateurs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste complète récupérée")
    })
    @GetMapping
    public ResponseEntity<List<DescriptionDTO>> getAllDescriptions() {
        List<DescriptionDTO> descriptions = descriptionService.getAllDescription();
          return new ResponseEntity<>(descriptions, HttpStatus.OK);
    }




    @Operation(summary = "Récupère une description par son ID",
            description = "Point d'accès essentiel pour afficher le contenu détaillé d'une œuvre (texte, audio) après un scan QR Code ou via le catalogue.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Description trouvée"),
            @ApiResponse(responseCode = "404", description = "Description non trouvée pour cet ID")
    })
    @GetMapping("/{descriptionId}")
    public ResponseEntity<DescriptionDTO> getDescriptionById(
            @Parameter(description = "ID de la description (Clé primaire)")
            @PathVariable Long descriptionId) {
        DescriptionDTO description = descriptionService.getDescriptionById(descriptionId);
        return new ResponseEntity<>(description, HttpStatus.OK);
    }




    // ========================================================================
    // ENDPOINTS PROTÉGÉS (ACCES ADMINISTRATEUR / CMS)
    // ========================================================================

    @Operation(summary = "Ajoute une nouvelle description (contenu multilingue)",
            description = "Crée une nouvelle description liée à une œuvre existante. Requiert un jeton JWT valide et l'autorité 'ADMIN' ou 'EDITOR'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Description créée avec succès"),
            @ApiResponse(responseCode = "404", description = "Œuvre parente non trouvée"),
            @ApiResponse(responseCode = "403", description = "Accès refusé (Rôle insuffisant)")
    })
    @PostMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public ResponseEntity<DescriptionDTO> saveDescription(@RequestBody SaveDescriptionDTO saveDescriptionDTO) {
        DescriptionDTO savedDescription = descriptionService.saveDescription(saveDescriptionDTO);
        return new ResponseEntity<>(savedDescription, HttpStatus.CREATED);
    }




    @Operation(summary = "Met à jour le contenu d'une description existante",
            description = "Met à jour le texte ou les liens associés à une langue spécifique. Requiert 'ADMIN' ou 'EDITOR'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Description mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Description non trouvée"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PutMapping("/{descriptionId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public ResponseEntity<DescriptionDTO> updateDescription(
            @Parameter(description = "ID de la description à modifier")
            @PathVariable Long descriptionId,
            @RequestBody UpdateDescriptionDTO updateDescriptionDTO) {
        DescriptionDTO updatedDescription = descriptionService.updateDescription(descriptionId, updateDescriptionDTO);
        return new ResponseEntity<>(updatedDescription, HttpStatus.OK);
    }




    @Operation(summary = "Supprime une description",
            description = "Supprime une description multilingue. Requiert l'autorité 'ADMIN'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Suppression réussie"),
            @ApiResponse(responseCode = "404", description = "Description non trouvée"),
            @ApiResponse(responseCode = "403", description = "Accès refusé (Rôle insuffisant)")
    })
    @DeleteMapping("/{descriptionId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteDescription(
            @Parameter(description = "ID de la description à supprimer")
            @PathVariable Long descriptionId) {
        descriptionService.deleteDescription(descriptionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
