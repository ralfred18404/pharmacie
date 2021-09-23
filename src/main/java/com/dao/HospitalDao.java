package com.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.domain.Hospital;

public class HospitalDao extends Source<Hospital> {

	public Hospital getOne(String id) {
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Hospital us = (Hospital)s.get(Hospital.class, id);
		s.close();
		return us;
	}
	
	@SuppressWarnings("unchecked")
	public List<Hospital>hospitalList(String district){
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Query q = s.createQuery("from Hospital where district.id =:district");
		q.setString("district", district);
		List<Hospital>list = q.list();
		s.close();
		return list;
	}
	
}
