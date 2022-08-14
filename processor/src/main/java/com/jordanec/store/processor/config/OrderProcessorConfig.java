package com.jordanec.store.processor.config;

import com.jordanec.store.dtos.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@Slf4j
public class OrderProcessorConfig {
    @Bean
    public Function<KStream<String, OrderDTO>, KStream<String, OrderDTO>> orderProcessor() {

        return kstream -> kstream.filter((key, order) -> {
            if (order.getAddress().getTimeZone().startsWith("America")) {
                log.info("Order with shipment to America received: {}", order.getAddress());
                return true;
            } else {
                log.info("Order shipment is not to America: {}", order.getAddress());
                return false;
            }
        });

    }
}
