package com.jordanec.store.dtos.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class AddressDTO implements Serializable
{
    private Long id;
    
    private String streetName;
    private String streetAddressNumber;
    private String streetAddress;
    private String buildingNumber;

    private String zipCode;
    private String city;
    private String state;

    private String latitude;
    private String longitude;
    private String timeZone;

    private String country;
    private String countryCode;

}
