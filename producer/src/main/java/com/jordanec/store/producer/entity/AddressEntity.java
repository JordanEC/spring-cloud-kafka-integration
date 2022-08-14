package com.jordanec.store.producer.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = AddressEntity.class)
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "Address")
@Table(name = "sto_address")
public class AddressEntity
{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private OrderEntity order;
}
