package com.example.lambda;

public class Lambda {    
    public void run() {
        Runnable r = () -> System.out.println("Hello World!");
        Thread t = new Thread(r);
        t.start();
    }
}
