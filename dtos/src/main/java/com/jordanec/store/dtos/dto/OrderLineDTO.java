package com.jordanec.store.dtos.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class OrderLineDTO implements Serializable
{
    private OrderLineIdDTO orderLineId;
    private ItemDTO item;
    private OrderDTO order;
    private Integer quantity;
    private Double itemTotal;
}

