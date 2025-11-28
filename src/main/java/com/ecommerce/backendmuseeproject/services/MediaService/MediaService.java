package com.ecommerce.backendmuseeproject.services.MediaService;

import com.ecommerce.backendmuseeproject.dto.MediaDTO;
import com.ecommerce.backendmuseeproject.dto.SaveMediaDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateMediaDTO;

import java.util.List;

public interface MediaService {

    public MediaDTO getMediaById(Long mediaId);

    public List<MediaDTO> getAllMedia();

    public MediaDTO saveMedia(SaveMediaDTO saveMediaDTO);

    public MediaDTO updateMedia(Long mediaId, UpdateMediaDTO updateMediaDTO);

    public void deleteMedia(Long mediaId);


}
