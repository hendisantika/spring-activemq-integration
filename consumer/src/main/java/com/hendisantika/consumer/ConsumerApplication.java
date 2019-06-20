package com.hendisantika.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;

@SpringBootApplication
public class ConsumerApplication {
    private static final Logger log = LoggerFactory.getLogger(ConsumerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @JmsListener(destination = "${inbound.endpoint}")
    public void consumedMessageHandler(Person person) {
        log.info("Id :" + person.getId() + " Name: " + person.getName());

    }

}
