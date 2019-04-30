package pl.tau.minecraft.domain;

import javax.persistence.*;

@Entity(name = "Player")
@Table(name = "player")
@NamedQueries({
        @NamedQuery(name = "player.all", query = "Select p from Player p")
})
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private int hp;
    private int armor;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

    public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

}
