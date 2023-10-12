package com.project.paymybuddy.model;


import lombok.Data;
import org.springframework.lang.Nullable;


@Data
public class TransactionsDto {

    private Integer giverId;

    private Integer receiverId;

    @Nullable
    private String description;

    private Float amount;
}
