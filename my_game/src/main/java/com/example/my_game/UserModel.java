package com.example.my_game;

/**
 * Created by bryan on 2017/3/30.
 */

public class UserModel {
    private static UserModel muserModel = new UserModel();
    private String name;
    private boolean isfirst = true;
    public static UserModel getInstence() {
        return muserModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getisfirst() {
        return isfirst;
    }

    public void setIsfirst(boolean isfirst) {
        this.isfirst = isfirst;
    }
}
