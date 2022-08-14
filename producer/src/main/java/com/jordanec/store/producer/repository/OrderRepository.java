package com.jordanec.store.producer.repository;

import com.jordanec.store.producer.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>
{
    OrderEntity findByOrderNumber(String orderNumber);
}
