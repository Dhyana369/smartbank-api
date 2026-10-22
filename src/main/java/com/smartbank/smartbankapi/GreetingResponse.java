package com.smartbank.smartbankapi;

public class GreetingResponse {
    private String message;

    public GreetingResponse(String message){
        this.message = message;
    }

    public String getName(){
        return message;
    }
}
