package com.project.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "nom")
    private String lastName;

    @Column(name = "prenom")
    private String firstName;

    @Column(name = "motDePasse")
    private String password;

    @JoinColumn(name = "bankAccount_id")
    private String bankAccountKey;

    //le passer en type int qui correspond aux ids ?
    @Column(name = "friends")
    @OneToMany(cascade = CascadeType.ALL)
    private List<User> friends;

    @Column(name = "sold")
    private Integer sold;
}
