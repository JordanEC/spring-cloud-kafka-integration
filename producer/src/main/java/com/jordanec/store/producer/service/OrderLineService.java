package com.jordanec.store.producer.service;

import com.jordanec.store.producer.entity.OrderLineEntity;
import com.jordanec.store.producer.repository.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService
{

    @Autowired
    OrderLineRepository orderLineRepository;

    public List<OrderLineEntity> createOrderLines(List<OrderLineEntity> orderLineEntities)
    {
        return orderLineRepository.saveAll(orderLineEntities);
    }
}
