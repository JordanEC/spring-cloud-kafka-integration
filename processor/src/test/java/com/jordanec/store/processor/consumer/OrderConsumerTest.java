package com.jordanec.store.processor.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jordanec.store.processor.ProcessorApp;
import com.jordanec.store.processor.service.ShipmentService;
import com.jordanec.store.dtos.dto.ItemDTO;
import com.jordanec.store.dtos.dto.OrderDTO;
import com.jordanec.store.dtos.dto.OrderLineDTO;
import kafka.server.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/*@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { ConsumerApp.class,
        H2JpaConfig.class })
@EmbeddedKafka(topics = { OrderConsumer.TOPIC_NAME}, partitions = 1)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        //"spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}"})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")*/
public class OrderConsumerTest
{
    @SpyBean
    OrderConsumer orderConsumer;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    KafkaListenerEndpointRegistry endpointRegistry;

    @SpyBean
    ShipmentService shipmentService;

    @Before
    public void setUp() {

        for (MessageListenerContainer messageListenerContainer : endpointRegistry.getListenerContainers()){
            ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafkaBroker.getPartitionsPerTopic());
        }
    }

    @After
    public void tearDown() {

    }

    //@Test
    public void testOrderConsumption() throws InterruptedException, JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();

        OrderDTO order = new OrderDTO();
        order.setOrderNumber("12931478901243-47");
        order.setClient("Jordan Espinoza C.");
        order.setShippingAddress("33122, FL, USA");
        List<OrderLineDTO> orderLineDTOList = new ArrayList<>();

        OrderLineDTO orderLineDTO1 = new OrderLineDTO();
        ItemDTO itemDTO1 = new ItemDTO();
        itemDTO1.setName("Xperia X ii");
        orderLineDTO1.setItem(itemDTO1);
        orderLineDTO1.setQuantity(5);
        orderLineDTOList.add(orderLineDTO1);

        OrderLineDTO orderLineDTO2 = new OrderLineDTO();
        ItemDTO itemDTO2 = new ItemDTO();
        itemDTO2.setName("Mac Pro");
        orderLineDTO2.setItem(itemDTO2);
        orderLineDTO2.setQuantity(1);
        orderLineDTOList.add(orderLineDTO2);

        order.setOrderLines(orderLineDTOList);

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(OrderConsumer.TOPIC_NAME, 0,
                order.getOrderNumber(), objectMapper.writeValueAsString(order),
                Arrays.asList(new RecordHeader("APPLICATION_NAME", "test".getBytes())));
        kafkaTemplate.send(producerRecord);

        CountDownLatch latch = new CountDownLatch(1);
        latch.await(5, TimeUnit.SECONDS);

        Mockito.verify(orderConsumer, Mockito.times(1)).onMessagePartition0(ArgumentMatchers.isA(ConsumerRecord.class));
        Mockito.verify(shipmentService, Mockito.times(1)).registerOrder(ArgumentMatchers.isA(OrderDTO.class));


    }

}
