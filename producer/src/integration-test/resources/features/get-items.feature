Feature: Get and update items
  Background:
    Given The given item is created
      | name        | description         | unitPrice |
      | Xperia X ii | Xperia X ii 256 GB  | 999.00    |
  Scenario: Fetch item
    When The item is fetched by its id
    Then The values match
  Scenario: Update and fetch item
    When The item name description and unit price are updated
      | name                  | description                     | unitPrice |
      | Xperia X ii (updated) | Xperia X ii 256 GB (updated)    | 984.00    |
    Then The values match
  Scenario: Items bulk creation and Find All
    Given The given items are created
      | name          | description                                         | unitPrice |
      | Mac Pro       | Mac Pro 2020 32 GB                                  | 3999.99   |
      | Go Pro Hero 8 | Go Pro Hero 8                                       | 300.00    |
      | HP Elitebook  | HP Elitebook 16GB Ram, Core i7 8th gen, 500GB SSD   | 1200.00   |
    When Finding all items
    Then A list with 4 elements is returned