package com.teamrocket.sys3studentservice.config.kaka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic bookBoughtTopic() {
        return TopicBuilder
            .name("bookBought")
            .build();
    }
}
