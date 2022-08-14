package com.jordanec.store.producer.cucumber;

import com.jordanec.store.producer.entity.ItemEntity;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetItemsSteps {

    @Autowired
    TestRestTemplate testRestTemplate;

    ResponseEntity<ItemEntity> responseEntity;
    ResponseEntity<List<ItemEntity>> responseEntityList;

    Long itemId;
    ItemEntity inputItemEntity;

    List<Long> itemIds;

    @LocalServerPort
    private int port;

    private String buildURL()
    {
        return "http://localhost:" + port + "/store/api/";
    }

    public static ParameterizedTypeReference<List<ItemEntity>> listItemTypeReference()
    {
        return new ParameterizedTypeReference<List<ItemEntity>>(){};
    }
    public static ParameterizedTypeReference<ItemEntity> itemTypeReference()
    {
        return new ParameterizedTypeReference<ItemEntity>(){};
    }

    @Before(order = 100)
    public void init() {
        itemIds = new ArrayList<>();
    }

    @After(order = 100)
    public void cleanUp(){
        itemIds.forEach(id -> {
            Map<String, Long> params = new HashMap<>();
            params.put("itemId", id);
            testRestTemplate.delete(buildURL() + "item/{itemId}", params);
        });

    }

    @Given("The given item is created")
    public void theGivenItemIsCreated(ItemEntity itemEntity){
        this.inputItemEntity = itemEntity;
        ResponseEntity<ItemEntity> responseEntity =
            testRestTemplate
                .postForEntity(buildURL() + "item", itemEntity, ItemEntity.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        itemId = responseEntity.getBody().getItemId();
        itemIds.add(itemId);
    }

    @When("The item is fetched by its id")
    public void theItemIsGetById() {
        Map<String, Long> params = new HashMap<>();
        params.put("itemId", itemId);
        responseEntity = testRestTemplate.getForEntity(buildURL() + "item/{itemId}", ItemEntity.class, params);
    }

    @When("The item name description and unit price are updated")
    public void theItemNameDescriptionAndUnitPriceAreUpdated(ItemEntity itemEntity) {
        this.inputItemEntity = itemEntity;
        Map<String, Long> params = new HashMap<>();
        params.put("itemId", itemId);
        HttpEntity<ItemEntity> httpEntity = new HttpEntity<>(itemEntity);
        responseEntity =
                testRestTemplate.exchange(buildURL() + "item/{itemId}", HttpMethod.PUT, httpEntity, ItemEntity.class, params);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        itemId = responseEntity.getBody().getItemId();
    }

    @Then("The values match")
    public void theItemIsFetched() {
        Assert.assertNotNull(responseEntity.getBody());
        ItemEntity responseItemEntity = responseEntity.getBody();
        Assert.assertEquals(inputItemEntity.getName(), responseItemEntity.getName());
        Assert.assertEquals(inputItemEntity.getDescription(), responseItemEntity.getDescription());
        Assert.assertEquals(inputItemEntity.getUnitPrice(), responseItemEntity.getUnitPrice());
    }

    @Given("The given items are created")
    public void theGivenItemsAreCreated(List<ItemEntity> itemEntities) {
        HttpEntity<List<ItemEntity>> httpEntity = new HttpEntity<>(itemEntities);
        responseEntityList = testRestTemplate
                .exchange(buildURL() + "item/bulkcreate", HttpMethod.POST, httpEntity, listItemTypeReference());
        Assert.assertEquals(HttpStatus.OK, responseEntityList.getStatusCode());
        Assert.assertNotNull(responseEntityList.getBody());
        responseEntityList.getBody().forEach(it -> itemIds.add(((ItemEntity)it).getItemId()));
    }

    @When("Finding all items")
    public void findingAllItems() {
        responseEntityList = testRestTemplate.exchange(buildURL() + "item", HttpMethod.GET, HttpEntity.EMPTY, listItemTypeReference());
        Assert.assertEquals(HttpStatus.OK, responseEntityList.getStatusCode());

    }

    @Then("A list with {int} elements is returned")
    public void aListWithElementsIsReturned(int totalItems) {
        Assert.assertEquals(totalItems, responseEntityList.getBody().size());
    }
}
