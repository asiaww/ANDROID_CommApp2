package com.example.jwetesko.fancynewsapp.Entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jwetesko on 06.01.17.
 */

public class Contact {

    @SerializedName("ID")
    int ID;

    @SerializedName("Name")
    String name;

    @SerializedName("PhoneNumber")
    String number;

    public Contact(int ID, String name, String number) {
        this.ID = ID;
        this.name = name;
        this.number = number;
        }

    public int getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public String getNumber() {
        return this.number;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public void getName(String name) {
        this.name = name;
    }

    public void PhoneNumber(String number) {
        this.number = number;
    }
}
