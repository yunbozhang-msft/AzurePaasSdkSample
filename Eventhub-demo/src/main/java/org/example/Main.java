package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        EventhubConsumer ec = new EventhubConsumer();
        try {
            ec.consumer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
/**
         EventhubProducer po = new EventhubProducer();
                po.sendMessage();
         */




    }

}