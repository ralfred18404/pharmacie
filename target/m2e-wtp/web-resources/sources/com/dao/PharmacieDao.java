package com.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.domain.Pharmacie;

public class PharmacieDao extends Source<Pharmacie> {

	public Pharmacie getOne(String id) {
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Pharmacie us = (Pharmacie)s.get(Pharmacie.class, id);
		s.close();
		return us;
	}
	

	@SuppressWarnings("unchecked")
	public List<Pharmacie>pharmacieList(String district){
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Query q = s.createQuery("from Pharmacie where district.id =:district");
		q.setString("district", district);
		List<Pharmacie>list = q.list();
		s.close();
		return list;
	}
}
