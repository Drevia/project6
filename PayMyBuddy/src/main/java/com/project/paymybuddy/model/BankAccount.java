package com.project.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bankAccount_id")
    private Integer id;

    @Column(name="Titulaire")
    private String owner;

    @Column(name="Rib")
    private String rib;
}
