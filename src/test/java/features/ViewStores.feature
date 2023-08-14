
@ViewStoresFeature
Feature: View Stores
 As a user i will be able to view the different stores

  @ViewAllStores @ignore
  Scenario: Get all stores
    Given Store API is available 
    When I invoke stores api with get Method
    Then the response code should be 200
    
  @ViewSingleStoreDetails @sanity @regression  
    Scenario: Get a single Store Details
    Given Store API is available 
    When I invoke "stores/{id}" api with get Method
    Then the response code should be 200
     
Scenario Outline:  Invoke the store API with limit

 Given Store API is available
When I invoke stores api with get method and <limit>
 Then the response code should be 200
 Examples:
 |limit|
 |1|
 |4|
 |10|
    
