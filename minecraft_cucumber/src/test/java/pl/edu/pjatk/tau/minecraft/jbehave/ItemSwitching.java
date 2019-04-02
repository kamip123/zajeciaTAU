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

public class ItemSwitching {
    private EquipmentInMemoryDao equipmentInMemoryDao;

    @Given("Player had a sword")
    public void player_had_a_sword() {
        equipmentInMemoryDao = new EquipmentInMemoryDao();
        equipmentInMemoryDao.equipments = new ArrayList<>();
        equipmentInMemoryDao.save(new Equipment(1L,"PLayer 1", 1, "Sword"));
        equipmentInMemoryDao.save(new Equipment(2L,"PLayer 1", 1, "Armor"));
    }

    @When("Player drop it and add $int bow to his inventory")
    public void player_drop_it_and_add_bow_to_his_inventory(Integer int1) {
        Equipment droppedSword = equipmentInMemoryDao.getAll().stream().filter(equipment -> equipment.getSlotOneName().equals("Sword")).findFirst().get();
        Assert.assertEquals(droppedSword, equipmentInMemoryDao.getById(droppedSword.getId()).get());
        equipmentInMemoryDao.delete(droppedSword);
        equipmentInMemoryDao.save(new Equipment(3L,"PLayer 1", 1, "Bow"));
    }

    @Then("Quantity of bows have increased by $int")
    public void quantity_of_bows_have_increased_by(Integer int1) {
        Assert.assertEquals(2, equipmentInMemoryDao.equipments.size());
    }

}
