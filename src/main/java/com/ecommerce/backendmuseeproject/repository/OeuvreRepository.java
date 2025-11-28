package com.ecommerce.backendmuseeproject.repository;

import com.ecommerce.backendmuseeproject.entites.Oeuvre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OeuvreRepository extends JpaRepository<Oeuvre,Long> {
    Optional<Oeuvre> findByAccessToken(String accessToken);
}
