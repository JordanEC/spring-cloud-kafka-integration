package com.jordanec.store.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jordanec.store.consumer.service.ShipmentService;
import com.jordanec.store.dtos.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class OrderConsumer {
    public final static String TOPIC_NAME = "order-notifications";

    private final ShipmentService shipmentService;

    public OrderConsumer(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    /**
     * Since partition is not defined here, it will receive all messages for any partition.
     *
     * @throws JsonProcessingException
     */
    @KafkaListener(topics = TOPIC_NAME)
    //If there's a listener for each partition is not possible to have another one for all partitions
    public void onMessageNoPartitionDefined(ConsumerRecord<String, OrderDTO> consumerRecord) {
        logRecordConsumption("onMessageNoPartitionDefined", consumerRecord);
        shipmentService.registerOrder(consumerRecord.value());
    }


    private void logRecordConsumption(String methodName, ConsumerRecord<String, OrderDTO> consumerRecord) {
        StringBuilder sb = new StringBuilder();
        consumerRecord.headers()
                .forEach(h -> {
                    try {
                        sb.append(h.key()).append("=").append(new String(h.value(), "UTF-8")).append(" ");
                    } catch (UnsupportedEncodingException e) {
                        log.error("logRecordConsumption() -> Error while decoding headers", e);
                    }
                });
        log.info("{}() -> ConsumerRecord: {} Headers: {}", methodName, consumerRecord.value(), sb);
    }
}
