package com.jordanec.store.producer.mapper;

import com.jordanec.store.dtos.dto.OrderDTO;
import com.jordanec.store.dtos.dto.OrderLineDTO;
import com.jordanec.store.producer.entity.OrderEntity;
import com.jordanec.store.producer.entity.OrderLineEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toOrderDTO(OrderEntity orderEntity);

    OrderLineDTO toOrderLineDTO(OrderLineEntity orderLineEntity);

    OrderEntity toOrderEntity(OrderDTO orderDTO);
}
