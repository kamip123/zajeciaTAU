package pl.edu.pjatk.tau.domain;

public class Player {

    private Long id;
    private String name;

    private int armor;
    private int hp;  

    public Player(){ }

    public Player(Long id, String name, int armor, int hp){
        this.id = id;
        this.name = name;
        this.armor=armor;
        this.hp=hp;
    }

    public void setId(Long i) {
        this.id = i;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArmor(int i) {
        this.armor = i;
    }

    public void setHp(int i) {
        this.hp = i;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getArmor() {
        return this.armor;
    }

    public int getHp() {
        return this.hp;
    }
}
