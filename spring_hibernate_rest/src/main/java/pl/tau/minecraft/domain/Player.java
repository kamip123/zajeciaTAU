package pl.tau.minecraft.domain;

import javax.persistence.*;
import java.util.Objects;
import pl.tau.minecraft.domain.Equipment;
import java.util.List;
import java.util.LinkedList;

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
    
    @OneToMany(cascade = CascadeType.PERSIST,
			fetch = FetchType.EAGER,
			orphanRemoval=false,
			mappedBy = "player"
	)
    private List<Equipment> equipments = new LinkedList<>();

    public List<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return hp == player.hp &&
                armor == player.armor &&
                Objects.equals(id, player.id) &&
                Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hp, armor);
    }

    public Player clone() {
		Player p = new Player();
		p.equipments = null;
		p.id = id;
		p.name = name;
        p.hp = hp;
		p.armor = armor;
		return p;
	}
}
