package com.project.paymybuddy.model;

import lombok.Data;

@Data
public class TransactionReadDto {

    private String connexionsName;

    private String description;

    private Float amount;
}
