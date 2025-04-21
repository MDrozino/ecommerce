package com.unicesumar.paymentMethods;

import java.util.concurrent.atomic.AtomicReference;

public class CreditCardPayment implements PaymentMethod {
    @Override
    public void pay(AtomicReference amount) {
        System.out.println("Pagamento efetuado com sucesso via cartão de crédito");
    }
}