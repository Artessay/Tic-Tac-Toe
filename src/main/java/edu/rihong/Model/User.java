package edu.rihong.Model;

import java.io.Serializable;

public class User implements Serializable {
    public boolean loginState;

    public String account;

    public String name;

    public char gender;

    private String password;

    public User() {
        loginState = false;
        gender = 'M';
    }

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public char getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(char gender) {
        if (gender != 'M' && gender != 'F') {
            System.out.println("Program Error, gender should be M or F");
            return;
        }

        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
