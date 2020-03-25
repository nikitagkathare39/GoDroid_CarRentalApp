package com.project.dbmsse.models;

import com.google.firebase.auth.FirebaseAuth;

public class UserObject {
    private String name;
    //private String streetAddress;
    //private String aptNumber;
    //private String city;
    //private String zipCode;
    private String email;
    private String phoneNumber;
    private String id;
    private String subject;
    private String msg;
    //private newString = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//
    //private CreditCard creditCard;

    /*public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
    public CreditCard getCreditCard() {
        return this.creditCard;
    }*/

    public String getName() {
        return name;
    }



    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getId() {
        return id;
    }

   /* public void setName(String name) {
        this.name = name;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

  /*  public void setId(String id) {
        this.id = id;
    }*/


    public UserObject() {
        this.name = "";

        this.email = "";
        //this.id = "";
        this.phoneNumber = "";

    }

    public UserObject( String subject,  String msg) {

        //this.name = name;


        this.subject = subject;
        this.msg = msg;

        //this.id = id;
    }
}