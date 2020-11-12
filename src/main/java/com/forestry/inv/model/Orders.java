package com.forestry.inv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	//@Column(unique=true, nullable=false) 
	private String orderno;
	private String orderdate;
	private String orderdescription;
	private String officer;
	private Double orderamount;
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
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public String getOrderdescription() {
		return orderdescription;
	}
	public void setOrderdescription(String orderdescription) {
		this.orderdescription = orderdescription;
	}
	public String getOfficer() {
		return officer;
	}
	public void setOfficer(String officer) {
		this.officer = officer;
	}
	public Double getOrderamount() {
		return orderamount;
	}
	public void setOrderamount(Double orderamount) {
		this.orderamount = orderamount;
	}
	@Override
	public String toString() {
		return "Orders [id=" + id + ", orderno=" + orderno + ", orderdate=" + orderdate + ", orderdescription="
				+ orderdescription + ", officer=" + officer + ", orderamount=" + orderamount + "]";
	}
	



	
}
