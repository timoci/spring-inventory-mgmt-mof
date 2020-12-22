package com.forestry.inv.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	//@Column(unique=true, nullable=false) 
	private String orderno;
	private String orderdescription;
	private String orderdate;
	private Double orderamount;
	private String officer;
	
	@OneToMany
	public List<Inventory> inventories;
	
	public Orders() {
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getOrderno() {
		return orderno;
	}



	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}



	public String getOrderdescription() {
		return orderdescription;
	}



	public void setOrderdescription(String orderdescription) {
		this.orderdescription = orderdescription;
	}



	public String getOrderdate() {
		return orderdate;
	}



	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}



	public Double getOrderamount() {
		return orderamount;
	}



	public void setOrderamount(Double orderamount) {
		this.orderamount = orderamount;
	}



	public String getOfficer() {
		return officer;
	}



	public void setOfficer(String officer) {
		this.officer = officer;
	}



	public List<Inventory> getInventories() {
		return inventories;
	}



	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}



	@Override
	public String toString() {
		return "Orders [id=" + id + ", orderno=" + orderno + ", orderdate=" + orderdate + ", orderdescription="
				+ orderdescription + ", officer=" + officer + ", orderamount=" + orderamount + "]";
	}
	



	
}
