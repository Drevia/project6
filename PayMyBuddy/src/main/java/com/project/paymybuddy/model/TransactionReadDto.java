package com.project.paymybuddy.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class TransactionReadDto {

    private String connexionsName;

    private String description;

    private Float amount;
}
