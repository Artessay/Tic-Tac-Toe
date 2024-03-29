package edu.rihong.Model;

import java.io.Serializable;

public class User implements Serializable {
    static final int rewardFortune = 500;

    public boolean loginState;

    private String account;

    private String name;

    private char gender;

    private int fortune;

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
        fortune = 5000;
        password = "";
    }

    public void deepCopy(User user) {
        this.loginState = user.loginState;
        this.setAccount(user.getAccount());
        this.setName(user.getName());
        this.setGender(user.getGender());
        this.setFortune(user.getFortune());
        this.setPassword(user.getPassword());
    }

    public void increaseFortune() {
        fortune += rewardFortune;
    }

    public void decreaseFortune() {
        fortune -= rewardFortune;
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

    public int getFortune() {
        return fortune;
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

    public void setFortune(int fortune) {
        if (fortune < 0) {
            fortune =  0;
        }

        this.fortune = fortune;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
