package com.jordanec.store.dtos.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class ItemDTO implements Serializable
{
    private Long itemId;
    private String name;
    private String description;
    private Double unitPrice;
}
