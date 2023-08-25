package com.project.paymybuddy.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;


@Data
@Getter
@Setter
public class TransactionsDto {

    private Integer giverId;

    private Integer receiverId;

    @Nullable
    private String description;

    private Float amount;
}
