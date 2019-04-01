Feature: Puting sword into chest
    Player put sword into chest

    Scenario: Player removed a sword from his inventory
        Given Player had 1 sword
        When Player removed 1 sword from inventory
        Then Quantity of swords have increased by 1