package com.teamrocket.sys3studentservice.config.kaka;

import com.teamrocket.sys3studentservice.dto.IDDto;
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
    public ProducerFactory<String, IDDto<Long>> producerFactory() {
        return this.kafkaUtil.createClassProducerFactory();
    }

    @Bean
    public KafkaTemplate<String, IDDto<Long>> kafkaTemplate() {
        return new KafkaTemplate<>(this.producerFactory());
    }

}
