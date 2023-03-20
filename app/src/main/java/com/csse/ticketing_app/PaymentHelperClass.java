package com.csse.ticketing_app;

public class PaymentHelperClass {

    private String cardHolderName, cardNum, expiryDate, cvv, mobileNumber;

    public PaymentHelperClass() {}

    public PaymentHelperClass(String cardHolderName , String cardNum , String expiryDate , String cvv , String mobileNumber) {
        this.cardHolderName = cardHolderName;
        this.cardNum = cardNum;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.mobileNumber = mobileNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
