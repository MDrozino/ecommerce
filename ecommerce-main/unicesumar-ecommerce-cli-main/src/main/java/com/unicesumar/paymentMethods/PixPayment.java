package com.unicesumar.paymentMethods;

import java.util.concurrent.atomic.AtomicReference;

public class PixPayment implements PaymentMethod {
    public void pay(AtomicReference amount) {
        System.out.println("Código PIX: 12345678-1234-1234-12345678");
    }
}