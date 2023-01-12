package edu.rihong.Model;

import java.io.Serializable;

public class User implements Serializable {
    public boolean loginState;

    private String account;

    private String name;

    private char gender;

    private String password;

    public User() {
        this.clean();
    }
    
    /** Clean User state */
    public void clean() {
        loginState = false;
        account = "";
        name = "Anonymous";
        gender = 'M';
        password = "";
    }

    public void deepCopy(User user) {
        this.loginState = user.loginState;
        this.setAccount(user.getAccount());
        this.setName(user.getName());
        this.setGender(user.getGender());
        this.setPassword(user.getPassword());
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
