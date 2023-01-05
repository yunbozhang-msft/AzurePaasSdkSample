package org.example;

public class Main {

    public static void main(String[] args) {
        EventhubConsumer ec = new EventhubConsumer();
        try {
            ec.consumer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}