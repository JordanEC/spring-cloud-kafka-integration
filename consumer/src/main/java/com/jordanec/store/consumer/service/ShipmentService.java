package com.jordanec.store.consumer.service;

import com.jordanec.store.consumer.constant.ShipmentStatus;
import com.jordanec.store.consumer.entity.ShipmentEntity;
import com.jordanec.store.consumer.mapper.AddressMapper;
import com.jordanec.store.consumer.mapper.ShipmentMapper;
import com.jordanec.store.consumer.repository.ShipmentRepository;
import com.jordanec.store.dtos.dto.OrderDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ShipmentService
{
    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;
    private final AddressMapper addressMapper;

    public ShipmentService(ShipmentRepository shipmentRepository, ShipmentMapper shipmentMapper, AddressMapper addressMapper) {
        this.shipmentRepository = shipmentRepository;
        this.shipmentMapper = shipmentMapper;
        this.addressMapper = addressMapper;
    }

    @Transactional
    public void registerOrder(OrderDTO order)
    {
        ShipmentEntity shipmentEntity = new ShipmentEntity();
        shipmentEntity.setAddress(addressMapper.toAddressEntity(order.getAddress()));
        shipmentEntity.setClient(order.getClient());
        shipmentEntity.setOrderNumber(order.getOrderNumber());
        shipmentEntity.setCreation(LocalDateTime.now());
        shipmentEntity.setStatus(ShipmentStatus.PREPARING.toString());
        shipmentRepository.save(shipmentEntity);
    }

    public ShipmentEntity findFirstByStatusOrderByCreation(ShipmentStatus shipmentStatus)
    {
        return shipmentRepository.findFirstByStatusOrderByCreation(shipmentStatus.toString());
    }

    public void updateStatus(ShipmentEntity shipmentEntity, ShipmentStatus shipmentStatus) {
        shipmentRepository.updateStatus(shipmentEntity.getId(), shipmentStatus.toString());
    }
}
