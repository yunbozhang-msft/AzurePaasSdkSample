package com.example.springmessaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
public class SpringMessagingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMessagingApplication.class, args);

        MessageChannel channel = new MessageChannel() {
            @Override
            public boolean send(Message<?> message, long timeout) {
                return false;
            }
        };
        // channel
    }

}
