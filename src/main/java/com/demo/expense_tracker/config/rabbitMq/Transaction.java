package com.demo.expense_tracker.config.rabbitMq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction implements Serializable {
    private String type;
    private double amount;
    private String description;
    private Long group_id;
    private Long user_id;
}
