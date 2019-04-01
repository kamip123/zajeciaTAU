Feature: Item finding by filtering players
    Player is looking for player with an item

    Scenario: Player is looking for player with an sword
        Given Player is on ranking page
        When Player set name of item to "sword 9"
        And set the amount to 2
        And set the name of owner to "kamil"
        Then he found a player with filters he have set