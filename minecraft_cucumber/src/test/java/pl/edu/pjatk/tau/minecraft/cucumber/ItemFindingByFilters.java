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
import java.util.List;
import java.util.stream.Collectors;

public class ItemFindingByFilters {

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
                new Equipment(2L,"kamil", 2, "sword 9"),
                new Equipment(3L,"Kamilen", 2, "sword 9"),
                new Equipment(4L,"Wiedzmin", 1, "sword 9"));
        filteredEquipments = equipmentInMemoryDao.getAll();
    }

    @When("Player set name of item to {string}")
    public void player_set_name_of_item_to(String string) {
        choosedItem = string;
        filteredEquipments = filteredEquipments.parallelStream().filter(equipment -> equipment.getSlotOneName().equals(choosedItem)).collect(Collectors.toList());
    }

    @And("set the amount to {int}")
    public void set_the_amount_to(Integer int1) {
        choosedAmount = int1;
        filteredEquipments = filteredEquipments.parallelStream().filter(equipment -> equipment.getSlotOne() >= choosedAmount).collect(Collectors.toList());

    }

    @And("set the name of owner to {string}")
    public void set_the_name_of_owner_to(String string) {
        choosedPlayer = string;
        filteredEquipments = filteredEquipments.parallelStream().filter(equipment -> equipment.getOwner().equals(choosedPlayer)).collect(Collectors.toList());

    }

    @Then("he found a player with filters he have set")
    public void he_found_a_player_with_filters_he_have_set() {
        Assert.assertEquals(1, filteredEquipments.size());
    }

}
