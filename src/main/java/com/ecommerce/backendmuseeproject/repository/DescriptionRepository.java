package com.ecommerce.backendmuseeproject.repository;

import com.ecommerce.backendmuseeproject.entites.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionRepository extends JpaRepository<Description,Long> {
}
