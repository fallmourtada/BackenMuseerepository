package com.ecommerce.backendmuseeproject.controller;

import com.ecommerce.backendmuseeproject.services.MediaService.MediaService;
import com.ecommerce.backendmuseeproject.dto.MediaDTO;
import com.ecommerce.backendmuseeproject.dto.SaveMediaDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateMediaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller pour la gestion des ressources multimédias (Media) des œuvres.
 * Gère les liens vers les fichiers audio, vidéo et images 360, répondant au besoin immersif.
 */
@RestController
@RequestMapping("/api/v1/media")
@Tag(name = "Ressources Multimédias (Media)", description = "Gestion des liens de fichiers (audio, vidéo, image) pour les œuvres.")
@AllArgsConstructor
@CrossOrigin
public class MediaController {

    private final MediaService mediaService;



    // ========================================================================
    // ENDPOINTS PUBLICS (ACCES VISITEUR - LECTURE)
    // ========================================================================

    @Operation(summary = "Récupère toutes les ressources multimédias",
            description = "Utile pour le CMS. Pour les visiteurs, il est préférable d'utiliser une route filtrée par ID d'Œuvre (ex: dans l'OeuvreController).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste complète récupérée")
    })
    @GetMapping
    public ResponseEntity<List<MediaDTO>> getAllMedia() {
        List<MediaDTO> mediaList = mediaService.getAllMedia();
        return new ResponseEntity<>(mediaList, HttpStatus.OK);
    }




    @Operation(summary = "Récupère une ressource multimédia par son ID",
            description = "Point d'accès direct pour obtenir le lien URL d'un fichier spécifique (audio, vidéo).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Média trouvé"),
            @ApiResponse(responseCode = "404", description = "Média non trouvé pour cet ID")
    })
    @GetMapping("/{mediaId}")
    public ResponseEntity<MediaDTO> getMediaById(
            @Parameter(description = "ID de la ressource multimédia (Clé primaire)")
            @PathVariable Long mediaId) {
        MediaDTO media = mediaService.getMediaById(mediaId);
        return new ResponseEntity<>(media, HttpStatus.OK);
    }



    // ========================================================================
    // ENDPOINTS PROTÉGÉS (ACCES ADMINISTRATEUR / CMS)
    // ========================================================================

    @Operation(summary = "Ajoute une nouvelle ressource multimédia",
            description = "Crée un nouveau lien (URL) vers un fichier audio/vidéo/image lié à une œuvre. Requiert 'ADMIN' ou 'EDITOR'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Média créé avec succès"),
            @ApiResponse(responseCode = "404", description = "Œuvre parente non trouvée"),
            @ApiResponse(responseCode = "403", description = "Accès refusé (Rôle insuffisant)")
    })
    @PostMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public ResponseEntity<MediaDTO> saveMedia(@RequestBody SaveMediaDTO saveMediaDTO) {
        MediaDTO savedMedia = mediaService.saveMedia(saveMediaDTO);
        return new ResponseEntity<>(savedMedia, HttpStatus.CREATED);
    }





    @Operation(summary = "Met à jour une ressource multimédia existante",
            description = "Modifie l'URL ou le type d'un fichier multimédia. Requiert 'ADMIN' ou 'EDITOR'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Média mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Média non trouvé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PutMapping("/{mediaId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public ResponseEntity<MediaDTO> updateMedia(
            @Parameter(description = "ID de la ressource multimédia à modifier")
            @PathVariable Long mediaId,
            @RequestBody UpdateMediaDTO updateMediaDTO) {
        MediaDTO updatedMedia = mediaService.updateMedia(mediaId, updateMediaDTO);
        return new ResponseEntity<>(updatedMedia, HttpStatus.OK);
    }



    @Operation(summary = "Supprime une ressource multimédia",
            description = "Supprime un lien de fichier multimédia. Requiert l'autorité 'ADMIN'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Suppression réussie"),
            @ApiResponse(responseCode = "404", description = "Média non trouvé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé (Rôle insuffisant)")
    })
    @DeleteMapping("/{mediaId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteMedia(
            @Parameter(description = "ID de la ressource multimédia à supprimer")
            @PathVariable Long mediaId) {
        mediaService.deleteMedia(mediaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
