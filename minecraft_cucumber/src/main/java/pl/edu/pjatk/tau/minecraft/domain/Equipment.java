package pl.edu.pjatk.tau.minecraft.domain;

import java.util.Objects;

public class Equipment {

    private Long id;
    private String owner;

    private int slotOne;
    private String slotOneName;

    public Equipment(){ }

    public Equipment(Long id, String owner, int slotOne, String slotOneName){
        this.id = id;
        this.owner = owner;
        this.slotOne=slotOne;
        this.slotOneName=slotOneName;
    }

    public Equipment(Equipment equipment){
        id = equipment.id;
        owner = equipment.owner;
        slotOne = equipment.slotOne;
        slotOneName = equipment.slotOneName;
    }

    public void setId(Long i) {
        this.id = i;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setSlotOne(int i) {
        this.slotOne = i;
    }

    public void setSlotOneName(String i) {
        this.slotOneName = i;
    }

    public Long getId() {
        return this.id;
    }

    public String getOwner() {
        return this.owner;
    }

    public int getSlotOne() {
        return this.slotOne;
    }

    public String getSlotOneName() {
        return this.slotOneName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return slotOne == equipment.slotOne &&
                Objects.equals(id, equipment.id) &&
                Objects.equals(owner, equipment.owner) &&
                Objects.equals(slotOneName, equipment.slotOneName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, slotOne, slotOneName);
    }
}
