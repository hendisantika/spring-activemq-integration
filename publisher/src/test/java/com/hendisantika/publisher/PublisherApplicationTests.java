package com.hendisantika.publisher;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.activemq.broker-url=vm://embedded?broker.persistent=false&broker.useJmx=false")
class PublisherApplicationTests {

    @Test
    void contextLoads() {
    }

}
