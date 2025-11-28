package com.ecommerce.backendmuseeproject.services.DescriptionService;

import com.ecommerce.backendmuseeproject.dto.DescriptionDTO;
import com.ecommerce.backendmuseeproject.dto.SaveDescriptionDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateDescriptionDTO;

import java.util.List;

public interface DescriptionService {

    public DescriptionDTO getDescriptionById(Long descriptionId);

    public DescriptionDTO saveDescription(SaveDescriptionDTO saveDescriptionDTO);

    public List<DescriptionDTO> getAllDescription();

    public DescriptionDTO updateDescription(Long descriptionId, UpdateDescriptionDTO updateDescriptionDTO);


    public void deleteDescription(Long descriptionId);
}
