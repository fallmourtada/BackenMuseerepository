package com.ecommerce.backendmuseeproject.repository;

import com.ecommerce.backendmuseeproject.entites.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HistoriqueRepository extends JpaRepository<Historique,Long> {
}
