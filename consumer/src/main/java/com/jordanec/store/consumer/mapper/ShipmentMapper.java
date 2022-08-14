package com.jordanec.store.consumer.mapper;

import com.jordanec.store.consumer.entity.AddressEntity;
import com.jordanec.store.dtos.dto.AddressDTO;
import com.jordanec.store.dtos.dto.ShipmentDTO;
import com.jordanec.store.consumer.entity.ShipmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {
    ShipmentDTO toShipmentDTO(ShipmentEntity orderEntity);

    ShipmentEntity toShipmentEntity(ShipmentDTO orderDTO);

    AddressEntity toAddressEntity(AddressDTO addressDTO);
}
