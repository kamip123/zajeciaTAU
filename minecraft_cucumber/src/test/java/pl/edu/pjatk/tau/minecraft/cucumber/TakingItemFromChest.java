package pl.edu.pjatk.tau.minecraft.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.But;
import org.junit.Assert;
import pl.edu.pjatk.tau.minecraft.dao.EquipmentInMemoryDao;
import pl.edu.pjatk.tau.minecraft.domain.Equipment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TakingItemFromChest {

    private EquipmentInMemoryDao equipmentInMemoryDao;
    private List<Equipment> playerEquipment = new ArrayList();
    private String choosedItem;
    private String choosedFloor;

    @Given("Player have choosen a sword")
    public void player_have_choosen_a_sword() {
        equipmentInMemoryDao = new EquipmentInMemoryDao();
        equipmentInMemoryDao.equipments = new ArrayList<>();
        Collections.addAll(equipmentInMemoryDao.equipments,
                new Equipment(1L,"Chest on floor 1", 1, "Lucky Sword"),
                new Equipment(2L,"Chest on floor 2", 1, "Sword"),
                new Equipment(3L,"Chest on floor 3", 1, "Unlucky Sword"));
    }

    @When("Player Choose a {string}")
    public void player_Choose_a(String string) {
        choosedFloor = string;
    }

    @When("Player choose the {string}")
    public void player_choose_the(String string) {
        choosedItem = string;
    }


    @Then("Player have a lucky swords")
    public void player_have_a_lucky_swords() {
        Equipment chooseedItemFromChest = equipmentInMemoryDao.getAll().stream().filter(equipment -> equipment.getOwner().equals(choosedFloor) && equipment.getSlotOneName().equals(choosedItem)).findFirst().get();
        playerEquipment.add(chooseedItemFromChest);
        Assert.assertEquals(chooseedItemFromChest, equipmentInMemoryDao.getById(chooseedItemFromChest.getId()).get());
        equipmentInMemoryDao.delete(chooseedItemFromChest);
        Assert.assertEquals(2, equipmentInMemoryDao.equipments.size());
    }

    @But("Player don't have other items")
    public void player_don_t_have_other_items() {
        Assert.assertEquals(1, playerEquipment.size());    
    }


}
