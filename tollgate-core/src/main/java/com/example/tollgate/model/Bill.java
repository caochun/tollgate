package com.example.tollgate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

@Data
public class Bill extends TollgateEntity {
    private double total;
    private String details;

    public static Bill randomBill() {
        Bill bill = new Bill();
        bill.setDetails(RandomStringUtils.randomPrint(10,100));
        bill.setTotal(new Random().nextInt());
        return bill;
    }
}
