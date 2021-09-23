package com.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DrugsRequest {

	@Id
	private String id;
	private int quantity;
	private int receivedQuantity;
	private double amountPaid;
	private String status;
	
	@ManyToOne
	@JoinColumn(name="category")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name="request")
	private Request request;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(int receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DrugsRequest(String id, int quantity, int receivedQuantity, Category category, Request request) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.receivedQuantity = receivedQuantity;
		this.category = category;
		this.request = request;
	}

	public DrugsRequest() {
		
	}
	
	
}
