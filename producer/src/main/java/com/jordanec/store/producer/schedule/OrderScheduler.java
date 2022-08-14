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
import reactor.util.function.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OrderScheduler {
    private final OrderService orderService;
    private final ItemService itemService;
    private final AddressMapper addressMapper;
    private static final Faker faker = new Faker();

    public OrderScheduler(OrderService orderService, ItemService itemService, AddressMapper addressMapper) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.addressMapper = addressMapper;
    }

    @Scheduled(cron = "#{ ${application.scheduler.random-order-creator.enabled} ? '${application.scheduler.random-order-creator.cron}' : '-' }")
    public void randomOrderCreator() {
        log.info("randomOrderCreator() -> Started...");
        final int orderLinesToGenerate = faker.random().nextInt(1, 5);

        orderPublisher(orderLinesToGenerate)
                .subscribe(tuple -> {
                    final OrderDTO order = tuple.getT1();

                    final List<OrderLineDTO> orderLines = tuple.getT2();
                    order.setOrderLines(orderLines);

                    log.info("Creating random order: {}", order);
                    orderService.create(order, null);
                });

    }

    private Mono<Tuple2<OrderDTO, List<OrderLineDTO>>> orderPublisher(int orderLinesCount) {
        return Mono.zip(baseOrderPublisher(), orderLinesPublisher(itemsPublisher(orderLinesCount)));
    }

    private Mono<OrderDTO> baseOrderPublisher() {
        return Mono.fromSupplier(
                () -> {
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setOrderNumber(String.valueOf(faker.number().numberBetween(50, 900000000000L)));
                    orderDTO.setClient(faker.name().fullName());
                    orderDTO.setAddress(addressMapper.toAddressDTO(faker.address()));
                    return orderDTO;
                }
        );
    }


    private Mono<List<ItemDTO>> itemsPublisher(int count) {
        return Mono.fromSupplier(() -> {
            List<ItemDTO> items = itemService.findAll();
            Collections.shuffle(items);
            return items.stream().limit(count).collect(Collectors.toList());
        });
    }

    private Mono<List<OrderLineDTO>> orderLinesPublisher(Mono<List<ItemDTO>> itemsPublisher) {
        return itemsPublisher.map(items ->
                items.stream().map(item -> {
                    OrderLineDTO orderLineDTO = new OrderLineDTO();
                    orderLineDTO.setItem(item);
                    orderLineDTO.setQuantity(faker.random().nextInt(1, 3));
                    return orderLineDTO;
                }).collect(Collectors.toList())
        );
    }
}
