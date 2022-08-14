package com.jordanec.store.producer.controller;

import com.jordanec.store.dtos.dto.OrderDTO;
import com.jordanec.store.producer.entity.OrderEntity;
import com.jordanec.store.producer.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "api")
public class OrderController
{
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/order/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO order, @RequestParam(required = false) Integer partition)
    {
        try
        {
            return new ResponseEntity<>(orderService.create(order, partition), HttpStatus.OK);
        }
        catch (Throwable throwable)
        {
            log.error("create() -> order: {}, partition: {}", order, partition, throwable);
            return ResponseEntity.badRequest().build();
        }
    }


    @RequestMapping(value = "/order", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDTO> get(@RequestParam("orderNumber") String orderNumber,
                                           @RequestParam(value = "includeOrderLines", required = false) boolean includeOrderLines)
    {
        return new ResponseEntity<>(orderService.findByOrderNumber(orderNumber, includeOrderLines), HttpStatus.OK);
    }
}
