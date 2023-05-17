package com.accenture.chat.billing.dto;

public class BillingDTO {

    private double rate;
    private double questionValue;
    private int count;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getQuestionValue() {
        return questionValue;
    }

    public void setQuestionValue(double questionValue) {
        this.questionValue = questionValue;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
