package com.jordanec.store.consumer.schedule;

import com.jordanec.store.consumer.constant.ShipmentStatus;
import com.jordanec.store.consumer.entity.ShipmentEntity;
import com.jordanec.store.consumer.service.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Component
public class ShipmentScheduler {
    final private ShipmentService shipmentService;

    public ShipmentScheduler(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    /**
     * To simulate errors while processing shipments, a random number is generated and if it matches with a given
     * value an exception is thrown. In this way the retryable mechanism comes into picture.
     *
     * @throws Exception
     */
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 60000))
    @Transactional
    @Scheduled(cron = "#{ ${application.scheduler.shipment-dispatcher.enabled} ? '${application.scheduler.shipment-dispatcher.cron}' : '-' }")
    public void shipmentDispatcher() throws Exception {
        log.info("shipmentDispatcherScheduler() -> Started...");
        Random random = new Random();
        if (5 == (random.nextInt(5) + 1)) {
            log.warn("random number matches!");
            throw new Exception("Simulating an error if a random number is 5");
        }

        ShipmentEntity shipmentEntity = shipmentService.findFirstByStatusOrderByCreation(ShipmentStatus.PREPARING);
        if (shipmentEntity != null) {
            shipmentService.updateStatus(shipmentEntity, ShipmentStatus.SHIPPED);
            log.info("shipmentDispatcherScheduler() -> Shipment: {} was shipped!", shipmentEntity.getId());
        } else {
            log.info("shipmentDispatcherScheduler() -> No shipment with status: {} found", ShipmentStatus.PREPARING);
        }
    }

    @Recover
    public void shipmentDispatcherRecover(Exception exception) {
        log.error("shipmentDispatcherScheduler() -> Max attempts done for the method hence executing recovery", exception);
    }
}
