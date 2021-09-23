package com.dao;

import javax.persistence.Entity;

import org.hibernate.Session;

import com.domain.Province;

@Entity
public class ProvinceDao extends Source<Province>{

	public Province getOne(String id) {
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Province us = (Province)s.get(Province.class, id);
		s.close();
		return us;
	}
	
}
