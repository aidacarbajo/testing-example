# forma imperativa (?
Feature: As a user I want to order products from Demoblaze e-commerce

Scenario: Navigate between different categories, finding products to add to the cart and buying them

  Given a new user on the main page of Demoblaze

  When user navigates to category "Phones"
  * user navigates to category "Laptops"
  * user navigates to category "Monitors"

  * user come back to category "Laptops"
  * user clicks on product "Dell i7 8gb"
  * user adds product to cart
  * user accepts pop up confirmation

  * user come back to category "Laptops"
  * user clicks on product "Sony vaio i5"
  * user adds product to cart
  * user accepts pop up confirmation

  * user come back to category "Laptops"
  * user clicks on product "Sony vaio i7"
  * user adds product to cart
  * user accepts pop up confirmation

  * user selects the cart from the menu
  * user deletes "Dell i7 8gb"

  * user clicks button to buy
  * user buys the product left with the next info
    | name | country | city | card | month | year |
    | Aida | Spain | Benidorm | 12873619237 | February | 1999 |
  Then the purchase has the correct amount
  * user closes purchase review