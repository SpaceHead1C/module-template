Feature: Groups

  Scenario Outline: Group creation
    Given a set of units2
    When I create a new unit2 with text <text> and some parameter <some_parameter>
    Then the new set of units2 is equal to the old set with the added unit

    Examples:
    | text      | some_parameter  |
    | text 1    | test value 1    |
    | text 2    | test value 2    |
    | text 3    | test value 2    |