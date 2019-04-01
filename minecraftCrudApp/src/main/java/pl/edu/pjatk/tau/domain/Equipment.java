package pl.edu.pjatk.tau.domain;

public class Equipment {

    private Long id;
    private String owner;

    private int slotOne;
    private int slotTwo;  

    public Equipment(){ }

    public Equipment(Long id, String owner, int slotOne, int slotTwo){
        this.id = id;
        this.owner = owner;
        this.slotOne=slotOne;
        this.slotTwo=slotTwo;
    }

    public Equipment(Equipment equipment){
        id = equipment.id;
        owner = equipment.owner;
        slotOne = equipment.slotOne;
        slotTwo = equipment.slotTwo;
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

    public void setSlotTwo(int i) {
        this.slotTwo = i;
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

    public int getSlotTwo() {
        return this.slotTwo;
    }

    public int add(int a, int b){
        switch (b) {
            case 1:
                this.slotOne += a;
                if(this.slotOne>64){
                    this.slotOne=64;
                }
                return slotOne;

            case 2:
                this.slotTwo += a;
                if(this.slotTwo>64){
                    this.slotTwo=64;
                }
                return slotTwo;

            default: 
                return 0;  
        } 
    }

}
