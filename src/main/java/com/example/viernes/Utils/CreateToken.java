package com.example.viernes.Utils;

import java.util.UUID;

public class CreateToken {

    public String genenrateTokenUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
