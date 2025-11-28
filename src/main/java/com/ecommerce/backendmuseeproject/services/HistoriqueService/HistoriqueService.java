package com.ecommerce.backendmuseeproject.services.HistoriqueService;

import com.ecommerce.backendmuseeproject.dto.HistoriqueDTO;
import com.ecommerce.backendmuseeproject.dto.SaveHistoriqueDTO;
import com.ecommerce.backendmuseeproject.dto.UpdateHistoriqueDTO;

import java.util.List;

public interface HistoriqueService {

    public HistoriqueDTO getHistoriqueById(Long historiqueId);

    public List<HistoriqueDTO> getAllHistorique();

    public HistoriqueDTO saveHistorique(SaveHistoriqueDTO saveHistoriqueDTO);

    public HistoriqueDTO updateHistorique(Long historiqueId, UpdateHistoriqueDTO updateHistoriqueDTO);

    public void deleteHistorique(Long historiqueId);
}
