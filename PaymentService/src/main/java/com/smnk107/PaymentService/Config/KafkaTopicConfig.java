package com.smnk107.PaymentService.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic getPostCreatedTopic() {
        return new NewTopic("payment-event", 3, (short) 1);
    }

}
