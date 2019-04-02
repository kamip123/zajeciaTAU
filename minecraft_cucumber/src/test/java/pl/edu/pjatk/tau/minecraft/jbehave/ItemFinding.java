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

public class ItemFinding {

    private EquipmentInMemoryDao equipmentInMemoryDao;
    private List<Equipment> filteredEquipments;
    private String choosedItem;
    private int choosedAmount;
    private String choosedPlayer;

    @Given("Player is on ranking page")
    public void player_is_on_ranking_page() {
        equipmentInMemoryDao = new EquipmentInMemoryDao();
        equipmentInMemoryDao.equipments = new ArrayList<>();
        Collections.addAll(equipmentInMemoryDao.equipments,
                new Equipment(1L,"Piotrek", 2, "Lucky Sword"),
                new Equipment(2L,"kamil", 2, "sword9"),
                new Equipment(3L,"Kamilen", 2, "sword9"),
                new Equipment(4L,"Wiedzmin", 1, "sword9"));
        filteredEquipments = equipmentInMemoryDao.getAll();
    }

    @When("Player set name of item to $item")
    public void player_set_name_of_item_to(String item) {
        choosedItem = item;
        filteredEquipments = filteredEquipments.parallelStream().filter(equipment -> equipment.getSlotOneName().equals(choosedItem)).collect(Collectors.toList());
    }

    @When("set the amount to $amount")
    public void set_the_amount_to(int amount) {
        choosedAmount = amount;
        filteredEquipments = filteredEquipments.parallelStream().filter(equipment -> equipment.getSlotOne() >= choosedAmount).collect(Collectors.toList());

    }

    @When("set the name of owner to $owner")
    public void set_the_name_of_owner_to(String owner) {
        choosedPlayer = owner;
        filteredEquipments = filteredEquipments.parallelStream().filter(equipment -> equipment.getOwner().equals(choosedPlayer)).collect(Collectors.toList());

    }

    @Then(value = "he found a player with filters he have set", priority = 1)
    public void he_found_a_player_with_filters_he_have_set() {
        Assert.assertEquals(1, filteredEquipments.size());
    }

}
