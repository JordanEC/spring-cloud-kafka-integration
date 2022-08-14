package com.jordanec.store.consumer.config;

import com.jordanec.store.dtos.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

//@Configuration
@Slf4j
public class OrderNotificatorConfig
{
//    @Bean
    public Function<KStream<String, OrderDTO>, KStream<String, OrderDTO>> orderNotificator() {

        return kstream -> kstream.filter((key, order) -> {
                log.debug("orderNotificator Order with shipment to America received: {}", order.getAddress());
                return true;
            }
        );
    }
}
