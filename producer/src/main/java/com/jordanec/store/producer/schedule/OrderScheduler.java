package com.jordanec.store.producer.schedule;

import com.github.javafaker.Faker;
import com.jordanec.store.dtos.dto.ItemDTO;
import com.jordanec.store.dtos.dto.OrderDTO;
import com.jordanec.store.dtos.dto.OrderLineDTO;
import com.jordanec.store.producer.mapper.AddressMapper;
import com.jordanec.store.producer.service.ItemService;
import com.jordanec.store.producer.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class OrderScheduler {
    private final OrderService orderService;
    private final ItemService itemService;
    private final AddressMapper addressMapper;

    public OrderScheduler(OrderService orderService, ItemService itemService, AddressMapper addressMapper) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.addressMapper = addressMapper;
    }

    @Scheduled(cron = "#{ ${application.scheduler.randomOrderCreator.enabled} ? '${application.scheduler.randomOrderCreator.cron}' : '-' }")
    public void randomOrderCreator() {
        log.info("randomOrderCreator() -> Started...");

        final Mono<OrderDTO> mono = Mono.fromSupplier(() -> {
            Faker faker = new Faker();

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderNumber(String.valueOf(faker.number().numberBetween(50, 900000000000L)));
            orderDTO.setClient(faker.name().fullName());
            orderDTO.setAddress(addressMapper.toAddressDTO(faker.address()));
            Integer orderLinesToGenerate = faker.random().nextInt(1, 5);
            List<OrderLineDTO> orderLines = new ArrayList<>(orderLinesToGenerate);

            List<ItemDTO> items = itemService.findAll();
            Collections.shuffle(items);
            LinkedList<ItemDTO> linkedList = new LinkedList<>(items);

            for (int i = 0; i < orderLinesToGenerate; i++) {
                ItemDTO dto = linkedList.pollFirst();

                OrderLineDTO orderLineDTO = new OrderLineDTO();
                orderLineDTO.setItem(dto);
                orderLineDTO.setQuantity(faker.random().nextInt(1, 3));

                orderLines.add(orderLineDTO);
            }

            orderDTO.setOrderLines(orderLines);
            return orderDTO;
        });

        mono.subscribe(dto -> {
            log.info("Creating random order: {}", dto);
            orderService.create(dto, null);});

    }
}
