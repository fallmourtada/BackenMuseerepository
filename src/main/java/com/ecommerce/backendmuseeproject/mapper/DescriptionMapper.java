package com.ecommerce.backendmuseeproject.mapper;

import com.ecommerce.backendmuseeproject.dto.DescriptionDTO;
import com.ecommerce.backendmuseeproject.dto.SaveDescriptionDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateDescriptionDTO;
import com.ecommerce.backendmuseeproject.entites.Description;
import com.ecommerce.backendmuseeproject.entites.Oeuvre;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DescriptionMapper {
    private OeuvreMapper oeuvreMapper;

    public DescriptionDTO fromEntity(Description description) {
        if(description == null) return null;

        DescriptionDTO descriptionDTO = new DescriptionDTO();
        descriptionDTO.setId(description.getId());
        descriptionDTO.setLangue(description.getLangue());
        descriptionDTO.setTexte(description.getTexte());
        descriptionDTO.setOeuvreDTO(oeuvreMapper.fromEntity(description.getOeuvre()));
        return descriptionDTO;
    }

    public Description fromSaveDTO(SaveDescriptionDTO saveDescriptionDTO, Oeuvre oeuvre) {
        Description description = new Description();
        description.setLangue(saveDescriptionDTO.getLangue());
        description.setTexte(saveDescriptionDTO.getTexte());
        if(oeuvre.getId() != null) {
            description.setOeuvre(oeuvre);
        }else {
            description.setOeuvre(null);
        }

        return description;
    }


    public Description partialUpdate(UpdateDescriptionDTO updateDescriptionDTO,Description description){
        if(updateDescriptionDTO ==null || description == null) return null;

        description.setLangue(updateDescriptionDTO.getLangue());
        description.setTexte(updateDescriptionDTO.getTexte());

        return description;
    }
}
