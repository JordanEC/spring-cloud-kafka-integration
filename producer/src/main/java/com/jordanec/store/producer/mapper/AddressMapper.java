package com.jordanec.store.producer.mapper;

import com.github.javafaker.Address;
import com.jordanec.store.dtos.dto.AddressDTO;
import com.jordanec.store.producer.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDTO toOrderDTO(AddressEntity addressEntity);

    AddressEntity toAddressEntity(AddressDTO addressDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "streetName", expression = "java(address.streetName())")
    @Mapping(target = "streetAddressNumber", expression = "java(address.streetAddressNumber())")
    @Mapping(target = "streetAddress", expression = "java(address.streetAddress())")
    @Mapping(target = "buildingNumber", expression = "java(address.buildingNumber())")
    @Mapping(target = "zipCode", expression = "java(address.zipCode())")
    @Mapping(target = "city", expression = "java(address.city())")
    @Mapping(target = "state", expression = "java(address.state())")
    @Mapping(target = "latitude", expression = "java(address.latitude())")
    @Mapping(target = "longitude", expression = "java(address.longitude())")
    @Mapping(target = "timeZone", expression = "java(address.timeZone())")
    @Mapping(target = "country", expression = "java(address.country())")
    @Mapping(target = "countryCode", expression = "java(address.countryCode())")
    AddressDTO toAddressDTO(Address address);
}
