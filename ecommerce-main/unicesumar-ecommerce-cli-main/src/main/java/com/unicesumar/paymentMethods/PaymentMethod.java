package com.unicesumar.paymentMethods;

import java.util.concurrent.atomic.AtomicReference;

public interface PaymentMethod {
    public void pay(AtomicReference amount);
}