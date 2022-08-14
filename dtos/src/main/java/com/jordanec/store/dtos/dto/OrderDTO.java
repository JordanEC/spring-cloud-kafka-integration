package com.jordanec.store.dtos.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class OrderDTO implements Serializable
{
    private Long id;
    private String orderNumber;
    private LocalDateTime creation;
    private String client;
    private List<OrderLineDTO> orderLines;
    private Double total;
    private AddressDTO address;
}
