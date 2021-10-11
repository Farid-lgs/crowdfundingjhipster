package fr.crowdfunding.jhipster.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StripeCharge implements Serializable {
    private long amount;
    private String receiptEmail;
    private String source;
    private String currency;
    private String stripePublicKey = "pk_test_51Jh7d8IFCde9fuycMpWCWzGmBpwIWiqgtrkyakVrZuxBv4P2uo87i4yXZreLng129zHGhVIc1SalezZbMsfnkhe800ymADAHyo";
    private static final int CENT_TO_DOLLAR = 100;

    public StripeCharge(long amount, String receiptEmail, String token) {
        this.amount = amount * CENT_TO_DOLLAR;
        this.source = token;
        this.currency = "aud";
        this.receiptEmail = receiptEmail;
    }

    public Map<String, Object> getCharge() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", this.amount);
        params.put("currency", this.currency);
        // source should obtained with Stripe.js
        params.put("source", this.source);
        params.put(
            "description",
            "My First Test Charge (created for API docs)"
        );
        params.put("receipt_email",this.receiptEmail);
//        params.put("stripePublicKey",this.stripePublicKey);
//        params.put("stripeToken", "tok_1JiF6eIFCde9fuycXfaoY7S5");
        return params;
    }

    @Override
    public String toString() {
        return "StripeCharge{" +
            "amount=" + amount +
            ", receiptEmail='" + receiptEmail + '\'' +
            ", source='" + source + '\'' +
            ", currency='" + currency + '\'' +
            ", stripePublicKey='" + stripePublicKey + '\'' +
            '}';
    }
}
