package com.example.elixirmedicityhealthapp;

public class ReadWriteUserDetails {
    public String dob, age, weight,height,blood,gender;
    public ReadWriteUserDetails( String textage, String textdob,String textblood, String textheight, String textgender, String textweight){

        dob = textdob;
        age = textage;
        blood = textblood;
        weight = textweight;
        height = textheight;
        gender = textgender;
    }
}
