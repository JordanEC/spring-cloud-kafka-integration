package com.jordanec.store.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jordanec.store.dtos.dto.ItemDTO;
import com.jordanec.store.producer.entity.ItemEntity;
import com.jordanec.store.producer.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api")
@Slf4j
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(value = "/item",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemEntity> create(@RequestBody ItemEntity itemEntity) throws JsonProcessingException {
        try {
            return new ResponseEntity<>(itemService.create(itemEntity), HttpStatus.OK);
        } catch (Exception e) {
            log.error("create() -> item: {}", itemEntity, e);
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping(value = "/item/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemEntity> update(@PathVariable("itemId") long itemId, @RequestBody ItemEntity itemEntity) {
        try {
            return new ResponseEntity<>(itemService.update(itemId, itemEntity), HttpStatus.OK);
        } catch (Exception e) {
            log.error("update() -> item: {}", itemEntity, e);
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping(value = "/item/{itemId}")
    public ResponseEntity<Void> delete(@PathVariable("itemId") long itemId) {
        try {
            itemService.delete(itemId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("delete() -> item: {}", itemId, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/item/bulkcreate", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemEntity>> bulkCreate(@RequestBody List<ItemEntity> itemEntities) {
        try {
            return new ResponseEntity<>(itemService.create(itemEntities), HttpStatus.OK);
        } catch (Exception e) {
            log.error("bulkCreate() -> {}", itemEntities, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemDTO>> findAll() {
        return new ResponseEntity<>(itemService.findAll(), HttpStatus.OK);
    }


    @GetMapping(value = "/item/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDTO> get(@PathVariable("itemId") long itemId) {
        return new ResponseEntity<>(itemService.findByItemId(itemId), HttpStatus.OK);
    }
}
