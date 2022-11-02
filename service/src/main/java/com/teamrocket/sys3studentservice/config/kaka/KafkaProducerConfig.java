package com.teamrocket.sys3studentservice.config.kaka;

import com.teamrocket.sys3studentservice.dto.IDDto;
import com.teamrocket.sys3studentservice.dto.event.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaUtil kafkaUtil;

    @Bean
    public ProducerFactory<String, BookDto> producerFactory() {
        return this.kafkaUtil.createClassProducerFactory();
    }

    @Bean
    public ProducerFactory<String, String> stringProducerFactory() {
        return this.kafkaUtil.createClassProducerFactory();
    }

    @Bean
    public KafkaTemplate<String, BookDto> kafkaTemplate() {
        return new KafkaTemplate<>(this.producerFactory());
    }

    @Bean
    public KafkaTemplate<String, String> stringKafkaTemplate() {
        return new KafkaTemplate<>(this.stringProducerFactory());
    }

}
