package com.example.project531.boydata;

public class OrderInfo {

    String productName;
    String quantity;
    String proPrice;
    String subtotal;
    String grndTotal;
    String custName;


    public OrderInfo(String productName, String quantity, String proPrice, String subtotal, String grndTotal, String custName) {
        this.productName = productName;
        this.quantity = quantity;
        this.proPrice = proPrice;
        this.subtotal = subtotal;
        this.grndTotal = grndTotal;
        this.custName = custName;
    }
}
