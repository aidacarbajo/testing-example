Feature: As a user I want to order products on Demoblaze e-commerce

Scenario: Navigation between categories finding products to add to my cart and to buy

  Given a new user on the main page of Demoblaze

  When user navigates to "Phones"
  * user navigates to "Laptops"
  * user navigates to "Monitors"

  * user navigates to "Laptops"
  * user clicks on "Sony vaio i5"
  * user clicks on "Add to cart"
  * user accepts pop up confirmation

  * user come back to "Laptops"
  * user clicks on "Dell i7 8gb"
  * user clicks on "Add to cart"
  * user accepts pop up confirmation

  * user navigates to "Cart"
  * user deletes "Dell i7 8gb"

  * user clicks on button "Place Order"
  * user fills every input form
    | name | country | city | card | month | year |
    | Aida | Spain | Benidorm | 12873619237 | February | 1999 |
  * user clicks on button "Purchase"

  Then the purchase has the correct amount
  * user clicks on button "OK"