package com.jordanec.store.producer.cucumber;

import com.jordanec.store.producer.entity.ItemEntity;
import com.jordanec.store.producer.service.ItemService;
import io.cucumber.java.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CreateItemsSteps {

    @Autowired
    ItemService itemService;
    List<Long> itemIds;
    ItemEntity inputItemEntity;
    List<ItemEntity> inputItemEntities;
    ItemEntity responseItemEntity;
    List<ItemEntity> responseItemEntities;

    Exception exception;

    @Before(order = 100)
    public void init() {
        itemIds = new ArrayList<>();
    }

    @After(order = 100)
    public void cleanUp(){
        itemIds.forEach(id -> itemService.delete(id));
    }


    @Given("An item with name {string} description {string} and unit price {double} is instantiated")
    public void anItemWithNameDescriptionAndUnitPriceIsInstantiated(String name, String description, Double unitPrice) {
        inputItemEntity = new ItemEntity();
        if (StringUtils.isNotBlank(name)) {
            inputItemEntity.setName(name);
        }
        inputItemEntity.setDescription(description);
        inputItemEntity.setUnitPrice(unitPrice);
    }

    @Given("An item is instantiated")
    public void anItemWithNameDescriptionAndUnitPriceIsInstantiated(ItemEntity itemEntity) {
        inputItemEntity = itemEntity;
    }

    @When("^The item is created$")
    public void theItemIsCreated() {
        try {
            responseItemEntity = itemService.create(inputItemEntity);
            itemIds.add(responseItemEntity.getItemId());
            this.exception = null;
        } catch (Exception exception) {
            this.exception = exception;
        }
    }

    @Then("^The item is returned$")
    public void theItemIsReturned() {
        Assert.assertNotNull(responseItemEntity);
        Assert.assertNotNull(responseItemEntity.getItemId());
    }

    @Then("An exception containing {string} is thrown")
    public void anExceptionContainingThrown(String exceptionName) {
        Assert.assertThat(exception.getCause().getCause().getMessage(), CoreMatchers.containsString(exceptionName));

    }

    @Then("An exception {string} is thrown")
    public void anExceptionIsThrown(String exceptionName) {
        Assert.assertEquals(exceptionName,  exception.getClass().getSimpleName());
    }

    @Given("Multiple items are instantiated")
    public void multipleItemsAreInstantiated(List<ItemEntity> itemEntities) {
        this.inputItemEntities = itemEntities;
    }

    @When("The items are created")
    public void theItemsAreCreated() {
        try {
            responseItemEntities = itemService.create(inputItemEntities);
            responseItemEntities.forEach(ri -> itemIds.add(ri.getItemId()));
            this.exception = null;
        } catch (Exception exception) {
            this.exception = exception;
        }
    }

    @Then("^The items are returned$")
    public void theItemsAreReturned() {
        Assert.assertNotNull(responseItemEntities);
        responseItemEntities.forEach(item -> Assert.assertNotNull(item.getItemId()));
    }

}
