package pl.edu.pjatk.tau.domain;

import java.util.Objects;

public class Player {

    private Long id;
    private String name;

    private int armor;
    private int hp;  

    public Player(){ }

    public Player(String name, int armor, int hp){
        this.name = name;
        this.armor=armor;
        this.hp=hp;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return armor == player.armor &&
                hp == player.hp &&
                Objects.equals(id, player.id) &&
                Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, armor, hp);
    }
}
