package com.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

@Entity
public class Pharmacie {

	@Id
	private String ids;
	@NotBlank(message="pharmacie name is required")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="district")
	private District district;
	
	@Transient
	private List<Drugs>drugs;
	

	
	
	
   
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
		this.district = district;
	}
	public List<Drugs> getDrugs() {
		return drugs;
	}
	public void setDrugs(List<Drugs> drugs) {
		this.drugs = drugs;
	}
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
