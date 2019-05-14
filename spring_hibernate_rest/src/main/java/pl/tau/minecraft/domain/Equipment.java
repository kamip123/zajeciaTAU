package pl.tau.minecraft.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

import pl.tau.minecraft.domain.Player;

@Entity(name = "Equipment")
@Table(name = "equipment")
@NamedQueries({ 
	@NamedQuery(name = "equipment.all", query = "Select p from Equipment p"),
	@NamedQuery(name = "equipment.findEquipmentsByItemName", query = "Select c from Equipment c where c.secondPosition like :itemName")
})
public class Equipment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String firstPosition;
	private int firstPositionQuantity;

	private String secondPosition;
	private int secondPositionQuantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
	private Player player;
	
	@Temporal(TemporalType.DATE)
	private Date lastUseDate;

	public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
	}

	public String getFirstPosition() {
		return firstPosition;
	}

	public void setFirstPosition(String firstPosition) {
		this.firstPosition = firstPosition;
	}

	public int getFirstPositionQuantity() {
		return firstPositionQuantity;
	}

	public void setFirstPositionQuantity(int firstPositionQuantity) {
		this.firstPositionQuantity = firstPositionQuantity;
	}

	public String getSecondPosition() {
		return secondPosition;
	}
	
	public void setSecondPosition(String secondPosition) {
		this.secondPosition = secondPosition;
	}

	public int getSecondPositionQuantity() {
		return secondPositionQuantity;
	}
	
	public void setSecondPositionQuantity(int secondPositionQuantity) {
		this.secondPositionQuantity = secondPositionQuantity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Equipment equipment = (Equipment) o;
		return firstPositionQuantity == equipment.firstPositionQuantity &&
				secondPositionQuantity == equipment.secondPositionQuantity &&
				Objects.equals(id, equipment.id) &&
				Objects.equals(firstPosition, equipment.firstPosition) &&
				Objects.equals(secondPosition, equipment.secondPosition) &&
				Objects.equals(lastUseDate, equipment.lastUseDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstPosition, firstPositionQuantity, secondPosition, secondPositionQuantity, lastUseDate);
	}
}
