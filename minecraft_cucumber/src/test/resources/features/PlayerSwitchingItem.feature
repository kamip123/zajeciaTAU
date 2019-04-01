Feature: Player switch sword to bow
    Player put bow into his inventory

    Scenario: Player removed a sword from his inventory and took a bow
        Given Player had a sword
        When Player drop it and add 1 bow to his inventory
        Then Quantity of bows have increased by 1