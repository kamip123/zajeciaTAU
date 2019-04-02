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
import java.util.List;
import java.util.stream.Collectors;

public class ItemTaking {

    private EquipmentInMemoryDao equipmentInMemoryDao;
    private List<Equipment> playerEquipment = new ArrayList();
    private String choosedItem;
    private String choosedFloor;

    @Given("Player have choosen a sword")
    public void player_have_choosen_a_sword() {
        equipmentInMemoryDao = new EquipmentInMemoryDao();
        equipmentInMemoryDao.equipments = new ArrayList<>();
        Collections.addAll(equipmentInMemoryDao.equipments,
                new Equipment(1L,"ChestOnFloor1", 1, "LuckySword"),
                new Equipment(2L,"ChestOnFloor2", 1, "Sword"),
                new Equipment(3L,"ChestOnFloor3", 1, "UnluckySword"));
    }

    @When("Player Choose a $string")
    public void player_Choose_a(String string) {
        choosedFloor = string;
    }

    @When("Player choose the $string")
    public void player_choose_the(String string) {
        choosedItem = string;
    }


    @Then("Player have a luckySwords")
    public void player_have_a_lucky_swords() {
        Equipment chooseedItemFromChest = equipmentInMemoryDao.getAll().stream().filter(equipment -> equipment.getOwner().equals(choosedFloor) && equipment.getSlotOneName().equals(choosedItem)).findFirst().get();
        playerEquipment.add(chooseedItemFromChest);
        Assert.assertEquals(chooseedItemFromChest, equipmentInMemoryDao.getById(chooseedItemFromChest.getId()).get());
        equipmentInMemoryDao.delete(chooseedItemFromChest);
        Assert.assertEquals(2, equipmentInMemoryDao.equipments.size());
    }
}
