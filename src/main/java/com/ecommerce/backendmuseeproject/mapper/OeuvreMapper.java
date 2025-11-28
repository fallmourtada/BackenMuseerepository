package com.ecommerce.backendmuseeproject.mapper;

import com.ecommerce.backendmuseeproject.dto.OeuvreDTO;
import com.ecommerce.backendmuseeproject.dto.SaveOeuvreDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateOeuvreDTO;
import com.ecommerce.backendmuseeproject.entites.Oeuvre;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OeuvreMapper {

    public OeuvreDTO fromEntity(Oeuvre oeuvre) {
        if(oeuvre == null)
            return null;

        OeuvreDTO oeuvreDTO = new OeuvreDTO();
        oeuvreDTO.setId(oeuvre.getId());
        oeuvreDTO.setTitre(oeuvre.getTitre());
        return oeuvreDTO;
    }


    public Oeuvre fromSavingDTO(SaveOeuvreDTO saveOeuvreDTO){
        if(saveOeuvreDTO == null)
            return null;

        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre(saveOeuvreDTO.getTitre());
        return oeuvre;
    }


    public Oeuvre partialUpdate( UpdateOeuvreDTO updateOeuvreDTO,Oeuvre oeuvre){
        if(oeuvre == null || updateOeuvreDTO == null)
            return null;

        oeuvre.setTitre(updateOeuvreDTO.getTitre());
        return oeuvre;
    }
}
