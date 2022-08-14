package com.jordanec.store.producer.repository;

import com.jordanec.store.producer.entity.AddressEntity;
import com.jordanec.store.producer.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long>
{
}
