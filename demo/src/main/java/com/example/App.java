package com.example;

import java.util.function.Supplier;

import com.example.lambda.Lambda;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        Supplier<Lambda> lambda = Lambda::new; 
        lambda.get().run();
    }
}
