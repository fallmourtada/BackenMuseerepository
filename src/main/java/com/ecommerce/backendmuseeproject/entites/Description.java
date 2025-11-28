package com.ecommerce.backendmuseeproject.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Description {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String langue;

    @Column(length = 5000)
    private String texte;

    @ManyToOne
    @JoinColumn(name = "oeuvre_id")
    private Oeuvre oeuvre;


}