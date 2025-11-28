package com.ecommerce.backendmuseeproject.services.MediaService;

import com.ecommerce.backendmuseeproject.dto.MediaDTO;
import com.ecommerce.backendmuseeproject.dto.SaveMediaDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateMediaDTO;
import com.ecommerce.backendmuseeproject.entites.Media;
import com.ecommerce.backendmuseeproject.entites.Oeuvre;
import com.ecommerce.backendmuseeproject.exception.ResourceNotFoundException;
import com.ecommerce.backendmuseeproject.mapper.MediaMapper;
import com.ecommerce.backendmuseeproject.repository.MediaRepository;
import com.ecommerce.backendmuseeproject.repository.OeuvreRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service gérant les ressources multimédias (liens de fichiers) des œuvres.
 * Assure les opérations CRUD et la vérification des clés étrangères.
 */
@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class MediaServiceImpl implements MediaService {

    // Dépendances nécessaires pour la logique et la persistance
    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;
    private final OeuvreRepository oeuvreRepository; // Nécessaire pour valider le lien Oeuvre-Media

    /**
     * Récupère une ressource multimédia par son ID.
     */
    @Override
    public MediaDTO getMediaById(Long mediaId) {
        log.info("Tentative de récupération du média ID: {}", mediaId);
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException("Media non trouvé avec id: " + mediaId));
        return mediaMapper.fromEntity(media);
    }

    /**
     * Récupère la liste de toutes les ressources multimédias.
     */
    @Override
    public List<MediaDTO> getAllMedia() {
        log.info("Récupération de la liste complète des médias pour l'administration.");
        List<Media> mediaList = mediaRepository.findAll();
        return mediaList.stream()
                .map(mediaMapper::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Enregistre une nouvelle ressource multimédia.
     * Effectue une vérification pour s'assurer que l'œuvre parente existe.
     */
    @Override
    public MediaDTO saveMedia(SaveMediaDTO saveMediaDTO) {
        // 1. Validation: L'œuvre doit exister
        Oeuvre oeuvre = oeuvreRepository.findById(saveMediaDTO.getOeuvreId())
                .orElseThrow(() -> new ResourceNotFoundException("Œuvre parente non trouvée avec id: " + saveMediaDTO.getOeuvreId()));

        log.info("Enregistrement d'un nouveau média de type {} pour l'œuvre ID: {}",
                saveMediaDTO.getType(), saveMediaDTO.getOeuvreId());

        // 2. Mapping DTO vers Entité
        Media media = mediaMapper.fromSaveDTO(saveMediaDTO, oeuvre);

        // 3. Sauvegarde
        Media savedMedia = mediaRepository.save(media);

        log.info("Média enregistré avec succès, nouvel ID: {}", savedMedia.getId());
        return mediaMapper.fromEntity(savedMedia);
    }

    /**
     * Met à jour l'URL ou le type d'une ressource multimédia existante.
     */
    @Override
    public MediaDTO updateMedia(Long mediaId, UpdateMediaDTO updateMediaDTO) {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException("Media non trouvé avec id: " + mediaId));

        log.info("Mise à jour du média ID: {}", mediaId);

        // Utilisation du mapper pour la mise à jour partielle
        Media updatedMedia = mediaMapper.partialUpdate(updateMediaDTO, media);
        Media savedMedia = mediaRepository.save(updatedMedia);

        return mediaMapper.fromEntity(savedMedia);
    }

    /**
     * Supprime une ressource multimédia du système.
     */
    @Override
    public void deleteMedia(Long mediaId) {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException("Media non trouvé avec id: " + mediaId));

        mediaRepository.delete(media);
        log.info("Média Supprimé Avec Succès, ID: {}", mediaId);
    }
}
