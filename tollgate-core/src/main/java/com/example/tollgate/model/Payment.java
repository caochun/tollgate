package com.example.tollgate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends TollgateEntity {
    private double amount;
    private String method;
}
