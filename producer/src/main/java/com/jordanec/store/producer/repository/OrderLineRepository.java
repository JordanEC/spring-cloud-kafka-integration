package com.jordanec.store.producer.repository;

import com.jordanec.store.producer.entity.OrderLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository  extends JpaRepository<OrderLineEntity, Long>
{

}
