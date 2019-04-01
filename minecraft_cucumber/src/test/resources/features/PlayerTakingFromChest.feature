Feature: Player taking swords from chest
    PLayer get swords from chest

    Scenario: Player get a sword from chest
        Given Player have choosen a sword
        When Player Choose a "Chest on floor 1"
        And Player choose the "Lucky Sword"
        Then Player have a lucky swords