package com.jordanec.store.dtos.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class ShipmentDTO implements Serializable
{
    private Long shipmentId;
    private String orderNumber;
    private LocalDateTime creation;
    private String client;
    private AddressDTO address;
    private String status;
}
