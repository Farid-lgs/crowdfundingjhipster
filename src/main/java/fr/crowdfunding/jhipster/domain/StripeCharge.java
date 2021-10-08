package fr.crowdfunding.jhipster.domain;

import java.util.HashMap;
import java.util.Map;

public class StripeCharge {
    private long amount;
    private String receiptEmail;
    private String source;
    private String currency;

    public StripeCharge() {
        this.amount = 10 * 100;
        this.source = "tok_visa";
        this.currency = "aud";
        this.receiptEmail = "alexandre.meddas@gmail.com";
    }

    public StripeCharge(long amount, String receiptEmail) {
        this.amount = amount;
        this.source = "tok_visa";
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
        return params;
    }

    @Override
    public String toString() {
        return "StripeCharge{" +
            "amount=" + amount +
            ", receiptEmail='" + receiptEmail + '\'' +
            ", source='" + source + '\'' +
            ", currency='" + currency + '\'' +
            '}';
    }
}
