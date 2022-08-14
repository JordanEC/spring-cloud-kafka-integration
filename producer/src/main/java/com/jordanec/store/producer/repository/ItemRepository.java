package com.jordanec.store.producer.repository;

import com.jordanec.store.producer.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long>
{
    ItemEntity findByName(String name);
    ItemEntity findByItemId(Long itemId);
}
