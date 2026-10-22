package com.smartbank.smartbankapi.controller;

import com.smartbank.smartbankapi.GreetingRequest;
import com.smartbank.smartbankapi.GreetingResponse;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String hello(){
        return "Welcome to SmartBank API!";
    }

    @GetMapping("/api/hello/{name}")
    public String greet(@PathVariable String name){
        return "Welcome, " + name + "!";
    }

    @GetMapping("/api/greet")
    public String greetUser(@RequestParam String name){
        return "Hello, " + name + "!";
    }

    @PostMapping("/api/test")
    public String testPost(){
        return "POST request received!";
    }

    @PostMapping("/api/greet")
    public GreetingResponse greet(@Valid @RequestBody GreetingRequest request){
        String message = "hello, " + request.getName() + "!";
        return new GreetingResponse(message);
    }
}
