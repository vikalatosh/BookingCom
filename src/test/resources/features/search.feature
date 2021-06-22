Feature: Booking search
  Scenario Outline: Search apartments
    Given Keyword for apartments search is <name>
    When User does search
    Then Apartments <result> are on the first page
    And Apartments should have <rating>
    Examples:
      | name                                            | result                                          | rating |
      | "Family apartments in Lviv center with balcony" | "Family apartments in Lviv center with balcony" | "9.4"  |
      | "Modern Art Hotel"                              | "Modern Art Hotel"                              | "9.1"  |