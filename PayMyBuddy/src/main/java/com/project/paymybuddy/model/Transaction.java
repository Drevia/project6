package com.project.paymybuddy.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //passer les deux en type int pour id ?
    @JoinColumn(name = "giver_id")
    private Integer giverId;

    @JoinColumn(name = "receiver_id")
    private Integer receiverId;

    @Column(name = "description")
    @Nullable
    private String description;

    @Column(name = "date")
    private Date date;

    @Column(name = "amount")
    private Float amount;
}
