package com.example.viernes.Utils;

import java.util.regex.Pattern;

public class Validator {

    public boolean validatorPassword(String password){
        return Pattern.compile("([A-Z]{1})([a-z]+)([0-9]{2,})").matcher(password).matches();
    }

    public boolean validatorEmail(String email){
        return Pattern.compile("^[a-z]+@[a-zA-Z0-9.-]+$").matcher(email).matches();
    }
}

