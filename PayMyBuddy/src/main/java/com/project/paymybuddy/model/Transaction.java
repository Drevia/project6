package com.project.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //passer les deux en type int pour id ?
    @JoinColumn(name = "giver_id")
    @OneToOne
    private AppUser giverId;

    @JoinColumn(name = "receiver_id")
    @OneToOne
    private AppUser receiverId;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private Date date;

    @Column(name = "amount")
    private Float amount;
}
