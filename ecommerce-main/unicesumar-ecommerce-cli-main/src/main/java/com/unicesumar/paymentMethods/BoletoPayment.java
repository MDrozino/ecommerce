package com.unicesumar.paymentMethods;

import java.util.concurrent.atomic.AtomicReference;

public class BoletoPayment implements PaymentMethod {
    @Override
    public void pay(AtomicReference amount) {
        System.out.println("Código boleto: 1234-4213-3546-3456");
    }
}