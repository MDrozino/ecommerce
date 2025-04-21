package com.unicesumar;
import com.unicesumar.paymentMethods.PaymentMethod;

import java.util.concurrent.atomic.AtomicReference;

public class PaymentManager {
    private PaymentMethod paymentMethod;

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void pay(AtomicReference amount) {
        this.paymentMethod.pay(amount);
    }
}
