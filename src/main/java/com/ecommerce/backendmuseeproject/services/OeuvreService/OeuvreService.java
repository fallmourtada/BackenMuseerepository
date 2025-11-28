package com.ecommerce.backendmuseeproject.services.OeuvreService;

import com.ecommerce.backendmuseeproject.dto.OeuvreDTO;
import com.ecommerce.backendmuseeproject.dto.SaveOeuvreDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateOeuvreDTO;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.List;

public interface OeuvreService {

    public OeuvreDTO getOeuvreById(Long oeuvreId);

    public OeuvreDTO saveOeuvre(SaveOeuvreDTO saveOeuvreDTO) throws IOException, WriterException;

    public void deleteOeuvre(Long oeuvreId);

    public OeuvreDTO updateOeuvre(Long oeuvreId, UpdateOeuvreDTO updateOeuvreDTO);

    public List<OeuvreDTO> getAllOeuvre();

    public OeuvreDTO getOeuvreByAccessToken(String accessToken);
}
