package com.jordanec.store.consumer.mapper;

import com.github.javafaker.Address;
import com.jordanec.store.dtos.dto.AddressDTO;
import com.jordanec.store.consumer.entity.AddressEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDTO toOrderDTO(AddressEntity addressEntity);

    AddressEntity toAddressEntity(AddressDTO addressDTO);

    AddressDTO toAddressDTO(Address address);
}
