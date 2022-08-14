package com.jordanec.store.producer.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = OrderEntity.class)
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "Order")
@Table(name = "sto_order", uniqueConstraints = { @UniqueConstraint(columnNames = "orderNumber")})
public class OrderEntity
{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderNumber;
    @Column
    private LocalDateTime creation;
    @Column
    private String client;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "order",
            cascade = CascadeType.DETACH,
            orphanRemoval = true)
    private List<OrderLineEntity> orderLines;
    @Column
    private Double total;
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private AddressEntity address;

    public void setAddress(AddressEntity address) {
        this.address = address;
        address.setOrder(this);
    }
}
