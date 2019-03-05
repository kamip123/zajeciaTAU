package pl.edu.pjatk.tau.domain;

public class Equipment {

    private Long id;
    private String owner;

    private int slotOne;
    private int slotTwo;  

    public void setId(long i) {
        this.id = i;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public long getId() {
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
