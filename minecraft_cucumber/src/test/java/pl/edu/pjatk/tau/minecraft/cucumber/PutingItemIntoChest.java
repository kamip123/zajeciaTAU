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
    private int previousQuantityOfItems;

    @Given("Player had {int} {string}")
    public void player_had_(Integer int1, String string) {
        equipmentInMemoryDao = new EquipmentInMemoryDao();
        equipmentInMemoryDao.equipments = new ArrayList<>();
    }

    @When("Player removed {int} {string} from inventory")
    public void player_removed_from_inventory(Integer int1, String string) {
        previousQuantityOfItems = equipmentInMemoryDao.equipments.size();
        equipmentInMemoryDao.save(new Equipment(1L,"Chest on floor 1", 1, string));

    }

    @Then("Quantity of {string} have increased by {int}")
    public void quantity_of_have_increased_by(String string, Integer int1) {
        Assert.assertEquals(previousQuantityOfItems + int1, equipmentInMemoryDao.equipments.size());
    }

}
