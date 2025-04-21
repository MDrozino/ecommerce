package com.unicesumar.entities;


import com.unicesumar.paymentMethods.PaymentType;

import java.util.UUID;

public class Sale {
    private UUID id;
    private UUID userId;
    private PaymentType paymentMethod;

    public Sale(UUID id, UUID userId, PaymentType paymentMethod) {
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

    public PaymentType getPaymentMethod() {
        return paymentMethod;
    }
}
