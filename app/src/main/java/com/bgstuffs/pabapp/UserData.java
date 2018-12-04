package com.bgstuffs.pabapp;

public class UserData {

    String userName;
    String userEmail;
    String userCarCompany;
    String userMobile;

    public UserData(String userName, String userEmail, String userCarCompany, String userMobile) {

        this.userName = userName;
        this.userEmail = userEmail;
        this.userCarCompany = userCarCompany;
        this.userMobile = userMobile;
    }


    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserCarCompany() {
        return userCarCompany;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserCarCompany(String userCarCompany) {
        this.userCarCompany = userCarCompany;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}
