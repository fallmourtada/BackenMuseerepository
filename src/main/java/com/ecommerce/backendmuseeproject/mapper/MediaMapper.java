package com.ecommerce.backendmuseeproject.mapper;


import com.ecommerce.backendmuseeproject.dto.MediaDTO;
import com.ecommerce.backendmuseeproject.dto.SaveMediaDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateMediaDTO;
import com.ecommerce.backendmuseeproject.entites.Media;
import com.ecommerce.backendmuseeproject.entites.Oeuvre;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;

@Service
@AllArgsConstructor
public class MediaMapper {
    private OeuvreMapper oeuvreMapper;

    public MediaDTO fromEntity(Media media){
        if(media == null) return null;
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setId(media.getId());
        mediaDTO.setType(media.getType());
        mediaDTO.setUrl(media.getUrl());
        mediaDTO.setOeuvreDTO(oeuvreMapper.fromEntity(media.getOeuvre()));

        return mediaDTO;
    }



    public Media fromSaveDTO(SaveMediaDTO saveMediaDTO, Oeuvre oeuvre){
        if(saveMediaDTO == null) return null;
        Media media = new Media();
        media.setType(saveMediaDTO.getType());
        media.setUrl(saveMediaDTO.getUrl());
        if(oeuvre.getId() != null){
            media.setOeuvre(oeuvre);
        }else {
            media.setOeuvre(null);
        }

        return media;
    }



    public Media partialUpdate(UpdateMediaDTO updateMediaDTO,Media media){
        if(updateMediaDTO == null || media == null) return null;

        media.setUrl(updateMediaDTO.getUrl());
        media.setType(updateMediaDTO.getType());
        return media;
    }
}
