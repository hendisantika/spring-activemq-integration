package com.hendisantika.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jms.core.JmsTemplate;

import java.util.UUID;

@SpringBootApplication
@EnableConfigurationProperties
public class PublisherApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublisherApplication.class, args);
    }

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${outbound.endpoint}")
    private String destination;


    /*
     * (non-Javadoc)
     *
     * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
     */
    @Override
    public void run(String... arg0) throws Exception {
        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setName("John Doe");

        jmsTemplate.convertAndSend(destination, person);

    }

}
