package com.project.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    //clé unique pour le mail
    @Column(name = "email")
    private String email;

    @Column(name = "nom")
    private String lastName;

    @Column(name = "prenom")
    private String firstName;

    @Column(name = "mot_de_passe")
    private String password;
    @Column(name = "balance")
    private Float sold;

}
