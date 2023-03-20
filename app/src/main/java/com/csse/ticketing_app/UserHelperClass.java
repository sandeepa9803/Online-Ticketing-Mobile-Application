package com.csse.ticketing_app;

public class UserHelperClass {

    private String fullName, userName, nic, mobileNum, password, balance;

    public UserHelperClass() {
    }

    public UserHelperClass(String fullName , String username , String nic  , String password, String balance) {
        this.fullName = fullName;
        this.userName = username;
        this.nic = nic;
        this.password = password;
        this.balance = balance;
    }

    public void setUserData(String fullName , String username , String nic  , String password, String balance) {
        this.fullName = fullName;
        this.userName = username;
        this.nic = nic;
        this.password = password;
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
