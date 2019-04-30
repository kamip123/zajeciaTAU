package pl.tau.minecraft.domain;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Entity(name = "Equipment")
@Table(name = "equipment")
@NamedQueries({ 
	@NamedQuery(name = "equipment.all", query = "Select p from Equipment p"),
	@NamedQuery(name = "equipment.findEquipmentsByItemName", query = "Select c from Equipment c where c.firstPosition like :itemNameFragment")
})
public class Equipment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String firstPosition;
	private int firstPositionQuantity;

	private String secondPosition;
	private int secondPositionQuantity;

	@Temporal(TemporalType.DATE)
	private Date lastUseDate;

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

	public String getsecondPosition() {
		return secondPosition;
	}
	
	public void setsecondPosition(String secondPosition) {
		this.secondPosition = secondPosition;
	}

	public int getsecondPositionQuantity() {
		return secondPositionQuantity;
	}
	
	public void setsecondPositionQuantity(int secondPositionQuantity) {
		this.secondPositionQuantity = secondPositionQuantity;
	}

}
