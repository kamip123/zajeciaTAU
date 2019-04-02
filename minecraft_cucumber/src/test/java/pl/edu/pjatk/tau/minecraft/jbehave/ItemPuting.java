package pl.edu.pjatk.tau.minecraft.jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.Story;
import org.junit.Assert;

import pl.edu.pjatk.tau.minecraft.dao.EquipmentInMemoryDao;
import pl.edu.pjatk.tau.minecraft.domain.Equipment;

import java.util.ArrayList;
import java.util.Collections;

public class ItemPuting {
    private EquipmentInMemoryDao equipmentInMemoryDao;
    private int previousQuantityOfItems;

    @Given("Player had 1 sword")
    public void givenPlayerHad1Sword() {
        equipmentInMemoryDao = new EquipmentInMemoryDao();
        equipmentInMemoryDao.equipments = new ArrayList<>();
    }

    @When("Player removed 1 sword from inventory")
    public void whenPlayerRemoved1SwordFromInventory() {
        previousQuantityOfItems = equipmentInMemoryDao.equipments.size();
        equipmentInMemoryDao.save(new Equipment(1L,"Chest on floor 1", 1, "sword"));

    }

    @Then("Quantity of swords have increased by $amountAdded")
    public void thenQuantityOfSwordsHaveIncreasedBy1(Integer amountAdded) {
        Assert.assertEquals(previousQuantityOfItems + amountAdded, equipmentInMemoryDao.equipments.size());
    }

}
