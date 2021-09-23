package com.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

@Entity
public class Category {

	@Id
	private String id;
	@NotBlank(message="category name is required..")
	private String name;
	@NotBlank(message="grammes is required..")
	private String gramme;
	private int quantity;
	private int box;
	private int givenbox;
	
	private Date expiredDate;
	private Double amount;
	private String status;
	private Date registrationDate;
	
	@ManyToOne
	@JoinColumn(name="drugs")
	private Drugs drugs;
	
	@Transient
	private List<DrugsRequest>drugsrequest;
	
	
	public List<DrugsRequest> getDrugsrequest() {
		return drugsrequest;
	}
	public void setDrugsrequest(List<DrugsRequest> drugsrequest) {
		this.drugsrequest = drugsrequest;
	}
	public Drugs getDrugs() {
		return drugs;
	}
	public void setDrugs(Drugs drugs) {
		this.drugs = drugs;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGramme() {
		return gramme;
	}
	public void setGramme(String gramme) {
		this.gramme = gramme;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getBox() {
		return box;
	}
	public void setBox(int box) {
		this.box = box;
	}
	public Date getExpiredDate() {
		return expiredDate;
	}
	//@Future(message="this expired date must be at future...")
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public int getGivenbox() {
		return givenbox;
	}
	public void setGivenbox(int givenbox) {
		this.givenbox = givenbox;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
