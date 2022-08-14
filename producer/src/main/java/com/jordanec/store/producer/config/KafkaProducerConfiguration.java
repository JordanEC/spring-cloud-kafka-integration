package com.jordanec.store.producer.config;

import com.jordanec.store.producer.entity.OrderEntity;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

//@Configuration
public class KafkaProducerConfiguration {
//    @Bean
    public KafkaTemplate<String, OrderEntity> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

//    @Bean
    public ProducerFactory<String, OrderEntity> producerFactory() {
        Map<String, Object> config = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:29092", //not working
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class

        );
        return new DefaultKafkaProducerFactory<>(config);
    }
}
