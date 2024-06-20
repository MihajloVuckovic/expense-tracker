package com.demo.expense_tracker.config.rabbitMq;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Data
public class Transaction implements Serializable {
    private String type;
    private double amount;
    private String description;
    private Long group_id;
    private Long user_id;
}
