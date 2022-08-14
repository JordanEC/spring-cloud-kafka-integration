package com.jordanec.store.producer.mapper;

import com.jordanec.store.dtos.dto.ItemDTO;
import com.jordanec.store.producer.entity.ItemEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemDTO toItemDTO(ItemEntity orderEntity);

    ItemEntity toItemEntity(ItemDTO orderDTO);

    List<ItemDTO> toItemDTOList(List<ItemEntity> orderEntityList);

}
