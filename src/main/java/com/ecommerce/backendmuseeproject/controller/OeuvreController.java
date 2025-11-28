package com.ecommerce.backendmuseeproject.controller;

import com.ecommerce.backendmuseeproject.dto.OeuvreDTO;
import com.ecommerce.backendmuseeproject.dto.SaveOeuvreDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateOeuvreDTO;
import com.ecommerce.backendmuseeproject.services.OeuvreService.OeuvreService;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * REST Controller pour la gestion du catalogue des œuvres du Musée des Civilisations Noires.
 *
 * Ce contrôleur expose les endpoints nécessaires pour les visiteurs (lecture) et
 * les administrateurs/éditeurs (écriture, mise à jour, suppression).
 */
@RestController
@RequestMapping("/api/v1/oeuvres")
@Tag(name = "Œuvres du Musée", description = "Gestion du catalogue des œuvres et de l'expérience visiteur via QR Code.")
@AllArgsConstructor
@CrossOrigin

public class OeuvreController {

    private final OeuvreService oeuvreService;


    // ========================================================================
    // ENDPOINTS PUBLICS (ACCES VISITEUR ET SCAN QR CODE)
    // ========================================================================

    @Operation(summary = "Récupère le catalogue complet des œuvres",
            description = "Fournit la liste des œuvres pour l'affichage du catalogue à distance et la recherche.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Catalogue récupéré avec succès",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OeuvreDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<OeuvreDTO>> getAllOeuvres() {
        List<OeuvreDTO> oeuvres = oeuvreService.getAllOeuvre();
        return new ResponseEntity<>(oeuvres, HttpStatus.OK);
    }





    @Operation(summary = "Récupère les détails d'une œuvre par son ID",
            description = "Endpoint utilisé après le scan d'un QR Code ou pour charger une page d'œuvre spécifique.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Œuvre trouvée et détails retournés"),
            @ApiResponse(responseCode = "404", description = "Œuvre non trouvée pour cet ID")
    })
    @GetMapping("/{oeuvreId}")
    public ResponseEntity<OeuvreDTO> getOeuvreById(
            @Parameter(description = "ID primaire de l'œuvre (souvent l'ID encodé dans le QR Code)")
            @PathVariable Long oeuvreId) {
        OeuvreDTO oeuvre = oeuvreService.getOeuvreById(oeuvreId);
        return new ResponseEntity<>(oeuvre, HttpStatus.OK);
    }

    // ========================================================================
    // ENDPOINTS PROTÉGÉS (ACCES ADMINISTRATEUR / CMS)
    // Sécurité gérée par JWT (TOKEN requis)
    // ========================================================================

    @Operation(summary = "Ajoute une nouvelle œuvre au catalogue",
            description = "Requiert le jeton JWT et l'autorité 'ADMIN' ou 'EDITOR'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Œuvre créée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non autorisé (Jeton manquant ou invalide)"),
            @ApiResponse(responseCode = "403", description = "Accès refusé (Rôle insuffisant)")
    })
    @PostMapping
    // Seuls les utilisateurs avec le rôle ADMIN ou EDITOR peuvent ajouter du contenu
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public ResponseEntity<OeuvreDTO> saveOeuvre(@RequestBody SaveOeuvreDTO saveOeuvreDTO) throws IOException, WriterException {
        OeuvreDTO savedOeuvre = oeuvreService.saveOeuvre(saveOeuvreDTO);
        return new ResponseEntity<>(savedOeuvre, HttpStatus.CREATED); // Code 201
    }





    @Operation(summary = "Met à jour une œuvre existante",
            description = "Requiert le jeton JWT et l'autorité 'ADMIN' ou 'EDITOR'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Œuvre mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Œuvre non trouvée"),
            @ApiResponse(responseCode = "401", description = "Non autorisé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PutMapping("/{oeuvreId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public ResponseEntity<OeuvreDTO> updateOeuvre(
            @Parameter(description = "ID de l'œuvre à modifier")
            @PathVariable Long oeuvreId,
            @RequestBody UpdateOeuvreDTO updateOeuvreDTO) {
        OeuvreDTO updatedOeuvre = oeuvreService.updateOeuvre(oeuvreId, updateOeuvreDTO);
        return new ResponseEntity<>(updatedOeuvre, HttpStatus.OK);
    }





    @Operation(summary = "Supprime une œuvre du catalogue",
            description = "Requiert le jeton JWT et l'autorité 'ADMIN'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Suppression réussie (Aucun contenu retourné)"),
            @ApiResponse(responseCode = "404", description = "Œuvre non trouvée"),
            @ApiResponse(responseCode = "401", description = "Non autorisé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé (Rôle insuffisant)")
    })
    @DeleteMapping("/{oeuvreId}")
    // Généralement, seuls les administrateurs peuvent supprimer
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteOeuvre(
            @Parameter(description = "ID de l'œuvre à supprimer")
            @PathVariable Long oeuvreId) {
        oeuvreService.deleteOeuvre(oeuvreId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Code 204 (Succès sans contenu)
    }


    @GetMapping("/by-access-token/{accessToken}")
    @Operation(
            summary = "Obtenir une oeuvre ",
            description = "Récupère les détails d'une oeuvre"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oeuvre Trouver avec Success"),
            @ApiResponse(responseCode = "404", description = "Ouvre non trouvé avec ce code QR")
    })
    public ResponseEntity <OeuvreDTO> getOeuvreByAccessToken(
            @Parameter(description = "Code QR de l'enfant") @PathVariable String accessToken) {

        OeuvreDTO oeuvreDTO = oeuvreService.getOeuvreByAccessToken(accessToken);
        return ResponseEntity.ok(oeuvreDTO);
    }
}
