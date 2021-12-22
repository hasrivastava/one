package com.example.onevote;

public class User {
    public String firstName, lastName, phoneNumber, email, aadhar;

    public User(){

    }
    public User(String firstName, String lastName, String phoneNumber, String email, String aadhar){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.aadhar = aadhar;
    }
}
