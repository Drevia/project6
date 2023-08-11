package com.project.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Date;

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
