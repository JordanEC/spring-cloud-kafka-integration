# Order creation and shipmentEntity using spring-kafka

Basic implementation of a module for orderEntity creation in a store and shipmentEntity dispatching. This application has 3 subprojects/modules:

orderEntity (producer): Orders are created through a POST API. If the orderEntity is valid, a message is sent to the shipmentEntity module.

shipmentEntity (consumer): Receives events from orderEntity module for the shipmentEntity process.

dtos: Provides a set of shared DTOs used by other modules.