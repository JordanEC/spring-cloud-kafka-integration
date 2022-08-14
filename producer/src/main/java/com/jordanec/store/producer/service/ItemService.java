package com.jordanec.store.producer.service;

import com.jordanec.store.dtos.dto.ItemDTO;
import com.jordanec.store.producer.entity.ItemEntity;
import com.jordanec.store.producer.mapper.ItemMapper;
import com.jordanec.store.producer.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {
    private final ItemMapper itemMapper;

    private final ItemRepository itemRepository;

    public ItemService(ItemMapper itemMapper, ItemRepository itemRepository) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public ItemEntity create(ItemEntity itemEntity) {
        itemRepository.save(itemEntity);
        return itemEntity;
    }

    @Transactional
    public ItemEntity update(long itemId, ItemEntity itemEntity) {
        itemEntity.setItemId(itemId);
        return itemRepository.save(itemEntity);
    }


    @Transactional
    public void delete(long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Transactional
    public List<ItemEntity> create(List<ItemEntity> itemEntities) {
        itemRepository.saveAll(itemEntities);
        return itemEntities;
    }

    public ItemDTO findByName(String name) {
        return itemMapper.toItemDTO(itemRepository.findByName(name));
    }

    public ItemDTO findByItemId(Long itemId) {
        return itemMapper.toItemDTO(itemRepository.findByItemId(itemId));
    }

    public List<ItemDTO> findAll() {
        return itemMapper.toItemDTOList(itemRepository.findAll());
    }
}
