package pl.edu.pjatk.tau.minecraft.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pl.edu.pjatk.tau.minecraft.dao.EquipmentInMemoryDao;
import pl.edu.pjatk.tau.minecraft.domain.Equipment;

import java.util.ArrayList;
import java.util.Collections;

public class PutingItemIntoChest {
    private EquipmentInMemoryDao equipmentInMemoryDao;
    private int previousQuantityOfSwords;

    @Given("Player had {int} sword")
    public void player_had_sword(Integer int1) {
        equipmentInMemoryDao = new EquipmentInMemoryDao();
        equipmentInMemoryDao.equipments = new ArrayList<>();
    }

    @When("Player removed {int} sword from inventory")
    public void player_removed_sword_from_inventory(Integer int1) {
        previousQuantityOfSwords = equipmentInMemoryDao.equipments.size();
        equipmentInMemoryDao.save(new Equipment(1L,"Chest on floor 1", 1, "Sword"));

    }

    @Then("Quantity of swords have increased by {int}")
    public void quantity_of_swords_have_increased_by(Integer int1) {
        Assert.assertEquals(previousQuantityOfSwords + int1, equipmentInMemoryDao.equipments.size());
    }

}
