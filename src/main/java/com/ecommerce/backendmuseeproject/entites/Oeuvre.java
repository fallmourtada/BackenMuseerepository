package com.ecommerce.backendmuseeproject.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Oeuvre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @OneToMany(mappedBy = "oeuvre", cascade = CascadeType.ALL)
    private List<Description> descriptions;

    @OneToMany(mappedBy = "oeuvre", cascade = CascadeType.ALL)
    private List<Media> medias;

    @OneToOne(mappedBy = "oeuvre", cascade = CascadeType.ALL)
    private Historique historique;

    private String contenuQrCode;

    private String  qrCode;

    private  String accessToken;

}
