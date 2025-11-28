package com.ecommerce.backendmuseeproject.entites;

import com.ecommerce.backendmuseeproject.enumeration.TypeMedia;
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
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeMedia type;

    private String url;

    @ManyToOne
    @JoinColumn(name = "oeuvre_id")
    private Oeuvre oeuvre;


}
