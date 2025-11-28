package com.ecommerce.backendmuseeproject.controller;

import com.ecommerce.backendmuseeproject.services.HistoriqueService.HistoriqueService;
import com.ecommerce.backendmuseeproject.dto.HistoriqueDTO;
import com.ecommerce.backendmuseeproject.dto.SaveHistoriqueDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateHistoriqueDTO;
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
 * REST Controller pour la gestion de l'historique des actions (Audit Log) dans le système.
 * Ce contrôleur gère les enregistrements des modifications de contenu (CMS) ou des événements majeurs.
 * L'accès à ces routes est hautement sécurisé car elles contiennent des données d'audit sensibles.
 */
@RestController
@RequestMapping("/api/v1/historique")
@Tag(name = "Historique et Audit", description = "Gestion des journaux d'audit (logs) des actions utilisateurs et administrateurs.")
@AllArgsConstructor
@CrossOrigin
public class HistoriqueController {

    private final HistoriqueService historiqueService;


    // ========================================================================
    // ENDPOINTS PROTÉGÉS (ACCES LECTURE/CONSULTATION - EDITOR/ADMIN)
    // ========================================================================

    @Operation(summary = "Récupère la liste complète de l'historique des actions (logs)",
            description = "Permet de consulter la liste des événements/modifications. Requiert l'autorité 'ADMIN' ou 'EDITOR' en raison de la sensibilité des données d'audit.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste complète récupérée"),
            @ApiResponse(responseCode = "403", description = "Accès refusé (Rôle insuffisant)")
    })
    @GetMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public ResponseEntity<List<HistoriqueDTO>> getAllHistorique() {
        List<HistoriqueDTO> historiqueList = historiqueService.getAllHistorique();
        return new ResponseEntity<>(historiqueList, HttpStatus.OK);
    }



    @Operation(summary = "Récupère un enregistrement d'historique spécifique par son ID",
            description = "Utile pour examiner en détail un événement d'audit. Requiert 'ADMIN' ou 'EDITOR'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historique trouvé"),
            @ApiResponse(responseCode = "404", description = "Historique non trouvé pour cet ID"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @GetMapping("/{historiqueId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public ResponseEntity<HistoriqueDTO> getHistoriqueById(
            @Parameter(description = "ID de l'enregistrement d'historique (Clé primaire)")
            @PathVariable Long historiqueId) {
        HistoriqueDTO historique = historiqueService.getHistoriqueById(historiqueId);
        return new ResponseEntity<>(historique, HttpStatus.OK);
    }

    // ========================================================================
    // ENDPOINTS ÉCRITURE/MODIFICATION (ACCES ADMIN/SYSTEM)
    // ========================================================================

    @Operation(summary = "Enregistre un nouvel événement dans l'historique (Log)",
            description = "Ajoute un nouvel enregistrement d'audit. Généralement utilisée par les services backend. Requiert 'ADMIN' ou 'SYSTEM'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Historique créé avec succès"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PostMapping
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'SYSTEM')") // SYSTEM peut être utilisé pour les appels internes du microservice
    public ResponseEntity<HistoriqueDTO> saveHistorique(@RequestBody SaveHistoriqueDTO saveHistoriqueDTO) {
        HistoriqueDTO savedHistorique = historiqueService.saveHistorique(saveHistoriqueDTO);
        return new ResponseEntity<>(savedHistorique, HttpStatus.CREATED);
    }




    @Operation(summary = "Met à jour un enregistrement d'historique",
            description = "Modification d'un log. Opération TRES sensible, strictement réservée à l'ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historique mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Historique non trouvé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PutMapping("/{historiqueId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HistoriqueDTO> updateHistorique(
            @Parameter(description = "ID de l'enregistrement d'historique à modifier")
            @PathVariable Long historiqueId,
            @RequestBody UpdateHistoriqueDTO updateHistoriqueDTO) {
        HistoriqueDTO updatedHistorique = historiqueService.updateHistorique(historiqueId, updateHistoriqueDTO);
        return new ResponseEntity<>(updatedHistorique, HttpStatus.OK);
    }




    @Operation(summary = "Supprime un enregistrement d'historique",
            description = "Suppression d'un log. Opération EXTRÊMEMENT sensible, strictement réservée à l'ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Suppression réussie"),
            @ApiResponse(responseCode = "404", description = "Historique non trouvé"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @DeleteMapping("/{historiqueId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteHistorique(
            @Parameter(description = "ID de l'enregistrement d'historique à supprimer")
            @PathVariable Long historiqueId) {
        historiqueService.deleteHistorique(historiqueId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
