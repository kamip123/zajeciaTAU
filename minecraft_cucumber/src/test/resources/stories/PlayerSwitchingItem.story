Scenario: Player switch sword to bow

Given Player had a sword
When Player drop it and add 1 bow to his inventory
Then Quantity of bows have increased by 1