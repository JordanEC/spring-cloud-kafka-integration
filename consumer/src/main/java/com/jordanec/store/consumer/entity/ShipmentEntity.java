package com.jordanec.store.consumer.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = ShipmentEntity.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity(name = "Shipment")
@Table(name = "sto_shipment")
public class ShipmentEntity
{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String orderNumber;
    @Column
    private LocalDateTime creation;
    @Column
    private String client;
    @OneToOne(mappedBy = "shipment", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private AddressEntity address;
    @Column
    private String status;

    public void setAddress(AddressEntity address) {
        this.address = address;
        address.setShipment(this);
    }
}
