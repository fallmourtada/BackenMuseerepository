package com.ecommerce.backendmuseeproject.services.DescriptionService;

import com.ecommerce.backendmuseeproject.dto.DescriptionDTO;
import com.ecommerce.backendmuseeproject.dto.SaveDescriptionDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateDescriptionDTO;
import com.ecommerce.backendmuseeproject.entites.Description;
import com.ecommerce.backendmuseeproject.exception.ResourceNotFoundException;
import com.ecommerce.backendmuseeproject.mapper.DescriptionMapper;
import com.ecommerce.backendmuseeproject.repository.DescriptionRepository;
import com.ecommerce.backendmuseeproject.entites.Oeuvre;
import com.ecommerce.backendmuseeproject.repository.OeuvreRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service gérant les descriptions détaillées (texte, pistes audio/vidéo) des œuvres.
 * Gère les opérations CRUD et assure la cohérence des données dans la base.
 */
@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class DescriptionServiceImpl implements DescriptionService {

    // Dépendances injectées (Repository et Mapper)
    private final DescriptionRepository descriptionRepository;
    private final DescriptionMapper descriptionMapper;
    private final OeuvreRepository oeuvreRepository; // Nécessaire pour vérifier l'existence de l'Oeuvre parente

    /**
     * Récupère une description détaillée par son ID.
     */
    @Override
    public DescriptionDTO getDescriptionById(Long descriptionId) {
        log.info("Tentative de récupération de la description ID: {}", descriptionId);
        Description description = descriptionRepository.findById(descriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Description non trouvée avec id: " + descriptionId));

        return descriptionMapper.fromEntity(description);
    }

    /**
     * Enregistre une nouvelle description pour une œuvre existante.
     * Assure que l'œuvre parente existe avant l'enregistrement.
     */
    @Override
    public DescriptionDTO saveDescription(SaveDescriptionDTO saveDescriptionDTO) {
        // 1. Vérification de l'existence de l'Œuvre parente
        Oeuvre oeuvre = oeuvreRepository.findById(saveDescriptionDTO.getOeuvreId())
                .orElseThrow(() -> new ResourceNotFoundException("Œuvre parente non trouvée avec id: " + saveDescriptionDTO.getOeuvreId()));

        log.info("Enregistrement d'une nouvelle description en langue {} pour l'oeuvre ID: {}",
                saveDescriptionDTO.getLangue(), saveDescriptionDTO.getOeuvreId());

        // 2. Mapping DTO vers Entité (en utilisant l'œuvre trouvée)
        Description description = descriptionMapper.fromSaveDTO(saveDescriptionDTO, oeuvre);

        // 3. Sauvegarde
        Description savedDescription = descriptionRepository.save(description);

        log.info("Description enregistrée avec succès, nouvel ID: {}", savedDescription.getId());
        return descriptionMapper.fromEntity(savedDescription);
    }

    /**
     * Supprime une description du système.
     */
    @Override
    public void deleteDescription(Long descriptionId) {
        Description description = descriptionRepository.findById(descriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Description non trouvée avec id: " + descriptionId));

        descriptionRepository.delete(description);
        log.info("Description Supprimée Avec Succès, ID: {}", descriptionId);
    }

    /**
     * Met à jour le contenu (texte ou lien) d'une description existante.
     */
    @Override
    public DescriptionDTO updateDescription(Long descriptionId, UpdateDescriptionDTO updateDescriptionDTO) {
        Description description = descriptionRepository.findById(descriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Description non trouvée avec id: " + descriptionId));

        log.info("Mise à jour de la description ID: {}", descriptionId);

        // Utilisation du mapper pour mettre à jour les champs (langue et texte/lien)
        Description updatedEntity = descriptionMapper.partialUpdate(updateDescriptionDTO, description);

        Description descriptionSaved = descriptionRepository.save(updatedEntity);

        return descriptionMapper.fromEntity(descriptionSaved);
    }


    /**
     * Récupère la liste de toutes les descriptions existantes.
     */
    @Override
    public List<DescriptionDTO> getAllDescription() {
        log.info("Récupération de toutes les descriptions pour l'administration");
        List<Description> descriptionList = descriptionRepository.findAll();

        return descriptionList.stream()
                .map(descriptionMapper::fromEntity)
                .collect(Collectors.toList());
    }
}
