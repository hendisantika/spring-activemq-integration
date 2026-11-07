package com.hendisantika.publisher;

import com.hendisantika.person.schema.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;

import java.util.UUID;

@SpringBootApplication
public class PublisherApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(PublisherApplication.class);

    private final JmsTemplate jmsTemplate;

    private final String destination;

    public PublisherApplication(JmsTemplate jmsTemplate, @Value("${outbound.endpoint}") String destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    public static void main(String[] args) {
        SpringApplication.run(PublisherApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setName("John Doe");

        jmsTemplate.convertAndSend(destination, person);
        log.info("Published person {} to {}", person.getId(), destination);
    }

}
