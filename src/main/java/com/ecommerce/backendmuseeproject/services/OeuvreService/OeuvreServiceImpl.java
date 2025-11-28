package com.ecommerce.backendmuseeproject.services.OeuvreService;

import com.ecommerce.backendmuseeproject.dto.OeuvreDTO;
import com.ecommerce.backendmuseeproject.dto.SaveOeuvreDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateOeuvreDTO;
import com.ecommerce.backendmuseeproject.entites.Oeuvre;
import com.ecommerce.backendmuseeproject.entites.QrCodeGenerator;
import com.ecommerce.backendmuseeproject.exception.ResourceNotFoundException;
import com.ecommerce.backendmuseeproject.mapper.OeuvreMapper;
import com.ecommerce.backendmuseeproject.repository.OeuvreRepository;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OeuvreServiceImpl implements OeuvreService{
    private OeuvreRepository oeuvreRepository;
    private OeuvreMapper oeuvreMapper;


    @Override
    public OeuvreDTO getOeuvreById(Long oeuvreId) {
        Oeuvre oeuvre = oeuvreRepository.findById(oeuvreId)
                .orElseThrow(()->new ResourceNotFoundException("Oeuvre non trouver avec id"+oeuvreId));
        OeuvreDTO  oeuvreDTO = oeuvreMapper.fromEntity(oeuvre);
        return oeuvreDTO;
    }


    // Fonction Pour creer le dossier pour les qr_codes
    private void ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Override
    public OeuvreDTO saveOeuvre(SaveOeuvreDTO saveOeuvreDTO) throws IOException, WriterException {
        Oeuvre oeuvre = oeuvreMapper.fromSavingDTO(saveOeuvreDTO);
        // Génération de l'accessToken et du QR Code uniquement pour les nouveaux utilisateurs
        String accessToken = UUID.randomUUID().toString();
        oeuvre.setAccessToken(accessToken);
        String contenuQRCode = "http://localhost:8080/public-access/" + accessToken;
        String directoryPath = "qr_codes";
        ensureDirectoryExists(directoryPath);
        String filePath = directoryPath + "/oeuvre_" + saveOeuvreDTO.getTitre()+"_qr.png";
        QrCodeGenerator.generateQRCode(contenuQRCode, filePath);
        oeuvre.setQrCode(filePath);
        oeuvre.setContenuQrCode(contenuQRCode);

        Oeuvre oeuvre1  = oeuvreRepository.save(oeuvre);

        return oeuvreMapper.fromEntity(oeuvre1);
    }

    @Override
    public void deleteOeuvre(Long oeuvreId) {
        Oeuvre oeuvre = oeuvreRepository.findById(oeuvreId)
                .orElseThrow(()->new ResourceNotFoundException("Oeuvre non trouver avec id"+oeuvreId));
        oeuvreRepository.delete(oeuvre);
        log.info("Oeuvre Supprimer Avec Success");


    }

    @Override
    public OeuvreDTO updateOeuvre(Long oeuvreId, UpdateOeuvreDTO updateOeuvreDTO) {
        Oeuvre oeuvre = oeuvreRepository.findById(oeuvreId)
                .orElseThrow(()->new ResourceNotFoundException("Oeuvre non trouver avec id"+oeuvreId));

        Oeuvre oeuvre1 = oeuvreMapper.partialUpdate(updateOeuvreDTO, oeuvre);

        Oeuvre oeuvreSaved = oeuvreRepository.save(oeuvre1);

        return oeuvreMapper.fromEntity(oeuvreSaved) ;
    }

    @Override
    public List<OeuvreDTO> getAllOeuvre() {
        List<Oeuvre> oeuvreList = oeuvreRepository.findAll();

        List<OeuvreDTO> oeuvreDTOList = oeuvreList.stream()
                .map(oeuvreMapper::fromEntity)
                .collect(Collectors.toList());

        return oeuvreDTOList;
    }


    @Override
    public OeuvreDTO getOeuvreByAccessToken(String accessToken) {
        Oeuvre oeuvre = oeuvreRepository.findByAccessToken(accessToken)
                .orElseThrow(()->new ResourceNotFoundException("Aucune Oeuvre Trouver Avec cet AccessToken"+accessToken));

        OeuvreDTO oeuvreDTO = oeuvreMapper.fromEntity(oeuvre);

        return oeuvreDTO;
    }
}
