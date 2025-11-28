package com.ecommerce.backendmuseeproject.services.HistoriqueService;

import com.ecommerce.backendmuseeproject.dto.HistoriqueDTO;
import com.ecommerce.backendmuseeproject.dto.SaveHistoriqueDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateHistoriqueDTO;
import com.ecommerce.backendmuseeproject.entites.Historique;
import com.ecommerce.backendmuseeproject.entites.Oeuvre;
import com.ecommerce.backendmuseeproject.exception.ResourceNotFoundException;
import com.ecommerce.backendmuseeproject.mapper.HistoriqueMapper;
import com.ecommerce.backendmuseeproject.repository.HistoriqueRepository;
import com.ecommerce.backendmuseeproject.repository.OeuvreRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service gérant les enregistrements d'historique (Audit Log).
 * Gère les opérations CRUD pour la traçabilité des actions utilisateurs et administrateurs.
 */
@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HistoriqueServiceImpl implements HistoriqueService {

    private final HistoriqueRepository historiqueRepository;
    private final HistoriqueMapper historiqueMapper;
    private final OeuvreRepository oeuvreRepository;

    /**
     * Récupère un enregistrement d'historique (log d'audit) par son identifiant.
     */
    @Override
    public HistoriqueDTO getHistoriqueById(Long historiqueId) {
        log.info("Tentative de récupération de l'historique ID: {}", historiqueId);
        Historique historique = historiqueRepository.findById(historiqueId)
                .orElseThrow(() -> new ResourceNotFoundException("Historique non trouvé avec id: " + historiqueId));
        return historiqueMapper.fromEntity(historique);
    }

    /**
     * Récupère la liste complète des enregistrements d'historique.
     */
    @Override
    public List<HistoriqueDTO> getAllHistorique() {
        log.info("Récupération de la liste complète des enregistrements d'historique.");
        List<Historique> historiqueList = historiqueRepository.findAll();
        return historiqueList.stream()
                .map(historiqueMapper::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Enregistre un nouvel événement ou log dans l'historique.
     * Cette méthode serait principalement appelée par d'autres services après une action critique.
     */
    @Override
    public HistoriqueDTO saveHistorique(SaveHistoriqueDTO saveHistoriqueDTO) {
        // 1. Validation: L'œuvre doit exister
        Oeuvre oeuvre = oeuvreRepository.findById(saveHistoriqueDTO.getOeuvreId())
                .orElseThrow(() -> new ResourceNotFoundException("Œuvre parente non trouvée avec id: " + saveHistoriqueDTO.getOeuvreId()));


        Historique historique = historiqueMapper.fromSaveDTO(saveHistoriqueDTO,oeuvre);

        Historique savedHistorique = historiqueRepository.save(historique);

        log.info("Historique enregistré avec succès, ID: {}", savedHistorique.getId());
        return historiqueMapper.fromEntity(savedHistorique);
    }

    /**
     * Met à jour un enregistrement d'historique existant.
     * Cette opération est très sensible.
     */
    @Override
    public HistoriqueDTO updateHistorique(Long historiqueId, UpdateHistoriqueDTO updateHistoriqueDTO) {
        Historique historique = historiqueRepository.findById(historiqueId)
                .orElseThrow(() -> new ResourceNotFoundException("Historique non trouvé avec id: " + historiqueId));

        log.warn("Modification d'un enregistrement d'historique (log ID: {}). Cette opération est rare.", historiqueId);

        Historique updatedHistorique = historiqueMapper.partialUpdate(updateHistoriqueDTO, historique);
        Historique savedHistorique = historiqueRepository.save(updatedHistorique);

        return historiqueMapper.fromEntity(savedHistorique);
    }

    /**
     * Supprime un enregistrement d'historique.
     * L'opération de suppression de logs d'audit doit être strictement réservée.
     */
    @Override
    public void deleteHistorique(Long historiqueId) {
        Historique historique = historiqueRepository.findById(historiqueId)
                .orElseThrow(() -> new ResourceNotFoundException("Historique non trouvé avec id: " + historiqueId));

        historiqueRepository.delete(historique);
        log.warn("Enregistrement d'historique Supprimé Avec Succès, ID: {}. (HAUTE SENSIBILITÉ)", historiqueId);
    }
}
