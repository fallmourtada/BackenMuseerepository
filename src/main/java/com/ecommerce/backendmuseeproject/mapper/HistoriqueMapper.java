package com.ecommerce.backendmuseeproject.mapper;


import com.ecommerce.backendmuseeproject.dto.HistoriqueDTO;
import com.ecommerce.backendmuseeproject.dto.OeuvreDTO;
import com.ecommerce.backendmuseeproject.dto.SaveHistoriqueDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateHistoriqueDTO;
import com.ecommerce.backendmuseeproject.entites.Historique;
import com.ecommerce.backendmuseeproject.entites.Oeuvre;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class HistoriqueMapper {

    private OeuvreMapper oeuvreMapper;


    public HistoriqueDTO  fromEntity(Historique historique){
        HistoriqueDTO historiqueDTO = new HistoriqueDTO();
        historiqueDTO.setCreateur(historique.getCreateur());
        historiqueDTO.setId(historique.getId());
        historiqueDTO.setDateCreation(historique.getDateCreation());
        historiqueDTO.setOeuvreDTO(oeuvreMapper.fromEntity(historique.getOeuvre()));
        return historiqueDTO;
    }


    public Historique fromSaveDTO(SaveHistoriqueDTO saveHistoriqueDTO, Oeuvre oeuvre){
        Historique historique = new Historique();
        historique.setCreateur(saveHistoriqueDTO.getCreateur());
        historique.setDateCreation(LocalDate.now());
        if(oeuvre.getId()!=null){
            historique.setOeuvre(oeuvre);
        }else {
            historique.setOeuvre(null);
        }

        return historique;
    }


    public Historique partialUpdate(UpdateHistoriqueDTO updateHistoriqueDTO,Historique historique){
        historique.setCreateur(updateHistoriqueDTO.getCreateur());
        historique.setDateCreation(updateHistoriqueDTO.getDateCreation());
        return historique;
    }
}
