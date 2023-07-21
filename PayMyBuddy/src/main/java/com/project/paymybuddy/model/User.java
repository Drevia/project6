package com.project.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "nom")
    private String lastName;

    @Column(name = "prenom")
    private String firstName;

    @Column(name = "motDePasse")
    private String password;

    @Column(name = "bankAccountKey")
    private String bankAccountKey;

    @Column(name = "friends")
    private List<User> friends;

    @Column(name = "sold")
    private Integer sold;
}
