package com.unicesumar.entities;


import java.util.UUID;

public class Sale {
    private UUID id;
    private UUID userId;
    private String paymentMethod;

    public Sale(UUID id, UUID userId, String paymentMethod) {
        this.id = id;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
