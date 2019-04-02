Feature: Puting sword into chest
    Player put sword into chest

    Scenario Outline: Player removed a sword from his inventory
        Given Player had 1 "<item>"
        When Player removed 1 "<item>" from inventory
        Then Quantity of "<answer>" have increased by 1

    Examples:
    | item           | answer |
    | sword          | swords |
    | bow            | bows   |
    | item           | items  |    