Feature: Test CRUD methods in Swagger PetStore REST API testing

Scenario Outline: get pets of the store filtered by status
  When a call of the pets filtered by pet status "<status>" is request
  Then the list of all the pets "<status>" is showed

  Examples:
    | status |
    | available |
    | sold |
    | pending |
    | invented |


Scenario Outline: Valid Pet ID entry. A new pet is stored, sold and deleted
  When the user adds a new "Dog" with "<name>" name, "available" status and <idP> as a id
  Then the pet name is "<name>"
  When a user buys the new pet created
  Then the pet status is "sold"
  When a delete request with the same pet is getting done
  Then the <idP> is the same as the deleted one

  Examples:
    | name  |  idP |
    | Spike | 123873 |
    |       | 231872 |
