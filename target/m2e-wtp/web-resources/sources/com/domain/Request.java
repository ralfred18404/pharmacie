package com.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Request {

	@Id
	private String id;
	private Date requestDate;
	private String status;
	
	@ManyToOne
	@JoinColumn(name="director")
	private Director director;
	@Transient
	private List<DrugsRequest>drugsrequest;
	
	
	public List<DrugsRequest> getDrugsrequest() {
		return drugsrequest;
	}
	public void setDrugsrequest(List<DrugsRequest> drugsrequest) {
		this.drugsrequest = drugsrequest;
	}
	public Director getDirector() {
		return director;
	}
	public void setDirector(Director director) {
		this.director = director;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
