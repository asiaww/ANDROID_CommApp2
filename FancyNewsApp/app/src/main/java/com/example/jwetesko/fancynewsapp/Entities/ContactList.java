package com.example.jwetesko.fancynewsapp.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jwetesko on 06.01.17.
 */

public class ContactList {

    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<Contact> contactList) {
        this.contactList = contactList;
    }

    @SerializedName("ContactList")
    ArrayList<Contact> contactList;


    public ContactList(ArrayList<Contact> contacts) {
        this.contactList = contacts;
    }


}
