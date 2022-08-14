Feature: Create items
  Scenario Outline: Create one item (option 1)
    Given An item with name "<name>" description "<description>" and unit price <unitPrice> is instantiated
    When The item is created
    Then The item is returned
    Examples:
      | name        | description         | unitPrice |
      | Xperia X ii | Xperia X ii 256 GB  | 999.00    |
      | Mac Pro     | Mac Pro 2020 32 GB  | 3999.99   |

  Scenario: Create 2 items with same name
    Given An item with name "Xperia X ii" description "Xperia X ii 256 GB" and unit price 999.00 is instantiated
    And The item is created
    And An item with name "Xperia X ii" description "Xperia X ii 128 GB" and unit price 895.99 is instantiated
    When The item is created
    Then An exception "DataIntegrityViolationException" is thrown

  Scenario: Create one item (option 2)
    Given An item is instantiated
      | name        | description         | unitPrice |
      | Xperia X ii | Xperia X ii 256 GB  | 999.00    |
    When The item is created
    Then The item is returned
########################################################################################################################
#  Scenario: Create one item without name (option 1)
#    Given An item with name "" description "Xperia X ii 256 GB" and unit price 999.00 is instantiated
#    When The item is created
#    Then An exception containing "Validation failed" is thrown

#  Scenario: Create one item without name (option 2)
#    Given An item is instantiated
#      | description         | unitPrice |
#      | Xperia X ii 256 GB  | 999.00    |
#    When The item is created
#    Then An exception containing "Validation failed" is thrown
########################################################################################################################
  Scenario: Create multiple items
    Given Multiple items are instantiated
      | name        | description         | unitPrice |
      | Xperia X ii | Xperia X ii 256 GB  | 999.00    |
      | Mac Pro     | Mac Pro 2020 32 GB  | 3999.99   |
    When The items are created
    Then The items are returned