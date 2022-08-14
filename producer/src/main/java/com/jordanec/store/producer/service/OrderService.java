package com.jordanec.store.producer.service;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.jordanec.store.dtos.dto.AddressDTO;
import com.jordanec.store.dtos.dto.ItemDTO;
import com.jordanec.store.dtos.dto.OrderDTO;
import com.jordanec.store.dtos.dto.OrderLineDTO;
import com.jordanec.store.producer.entity.ItemEntity;
import com.jordanec.store.producer.entity.OrderEntity;
import com.jordanec.store.producer.entity.OrderLineEntity;
import com.jordanec.store.producer.mapper.AddressMapper;
import com.jordanec.store.producer.mapper.OrderMapper;
import com.jordanec.store.producer.producer.OrderProducer;
import com.jordanec.store.producer.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    private final OrderProducer orderProducer;

    private final OrderRepository orderRepository;

    private final OrderLineService orderLineService;
    private final ItemService itemService;

    private final EntityManager em;

    private final OrderMapper orderMapper;

    private final AddressMapper addressMapper;

    public OrderService(OrderProducer orderProducer, OrderRepository orderRepository, OrderLineService orderLineService, ItemService itemService, EntityManager em, OrderMapper orderMapper, AddressMapper addressMapper) {
        this.orderProducer = orderProducer;
        this.orderRepository = orderRepository;
        this.orderLineService = orderLineService;
        this.itemService = itemService;
        this.em = em;
        this.orderMapper = orderMapper;
        this.addressMapper = addressMapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OrderDTO create(OrderDTO orderDTO, Integer partition)
    {
        validateOrder(orderDTO);

        calculateOrder(orderDTO);

        Faker faker = new Faker();
        Address fakeAddress = faker.address();

        AddressDTO addressDTO = addressMapper.toAddressDTO(fakeAddress);
        orderDTO.setAddress(addressDTO);

        OrderEntity orderEntity = orderMapper.toOrderEntity(orderDTO);
//        orderEntity.getAddress().setOrder(orderEntity);

        orderRepository.save(orderEntity);

//        return null;
        OrderDTO newOrderDTO = orderMapper.toOrderDTO(orderEntity);
        orderProducer.sendOrderCreationAsynchronous(newOrderDTO, partition);
        return newOrderDTO;
    }

    public OrderDTO findByOrderNumber(String orderNumber, boolean includeOrderLines) {
        OrderEntity orderEntity;
        if (includeOrderLines) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery query = cb.createQuery();
            Root rootOrder = query.from(OrderEntity.class);
            rootOrder.fetch("orderLines", JoinType.LEFT);
            query.select(rootOrder);
            query.where(cb.equal(rootOrder.get("orderNumber"), orderNumber));
            orderEntity = (OrderEntity) em.createQuery(query).getSingleResult();
        } else {
                orderEntity = orderRepository.findByOrderNumber(orderNumber);
        }
        return orderMapper.toOrderDTO(orderEntity);
    }

    private void calculateOrder(OrderDTO order) {
        double orderLinesTotal = order.getOrderLines()
                .stream()
                .mapToDouble(line -> {
                    double itemTotal = line.getItem().getUnitPrice() * line.getQuantity();
                    line.setItemTotal(itemTotal);
                    return itemTotal;
                })
                .sum();

        order.setTotal(orderLinesTotal);
        order.setCreation(LocalDateTime.now());
    }

    private void validateOrder(OrderDTO order) throws RuntimeException
    {
        if (CollectionUtils.isEmpty(order.getOrderLines()))
        {
            throw new RuntimeException("No OrderLines");
        }
        //validate items
        for(OrderLineDTO orderLineDTO : order.getOrderLines())
        {
            if (orderLineDTO.getQuantity() == null || orderLineDTO.getQuantity() < 1)
            {
                throw new RuntimeException("Invalid quantity for order line: " + orderLineDTO);
            }
            if (orderLineDTO.getItem() == null)
            {
                throw new RuntimeException("Item missing for order line: " + orderLineDTO);
            }
            ItemDTO itemDTO = null;
            if (!StringUtils.isEmpty(orderLineDTO.getItem().getItemId() != null))
            {
                itemDTO = itemService.findByItemId(orderLineDTO.getItem().getItemId());
            }
            if (itemDTO == null)
            {
                if (!StringUtils.isEmpty(orderLineDTO.getItem().getName() != null))
                {
                    itemDTO = itemService.findByName(orderLineDTO.getItem().getName());
                }
                if (itemDTO == null)
                {
                    throw new RuntimeException("Item not found: "+ orderLineDTO.getItem());
                }
            }
            orderLineDTO.setItem(itemDTO);
        }
        //... TODO
    }
}
