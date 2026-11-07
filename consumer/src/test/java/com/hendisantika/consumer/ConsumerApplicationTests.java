package com.hendisantika.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.activemq.broker-url=vm://embedded?broker.persistent=false&broker.useJmx=false")
class ConsumerApplicationTests {

    @Test
    void contextLoads() {
    }

}
