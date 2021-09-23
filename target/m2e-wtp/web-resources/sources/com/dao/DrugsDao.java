package com.dao;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.domain.Drugs;


public class DrugsDao extends Source<Drugs> {

	public Drugs getOne(String id) {
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Drugs us = (Drugs)s.get(Drugs.class, id);
		s.close();
		return us;
	}
	
	@SuppressWarnings("unchecked")
	public List<Drugs>medecine(String pharmacie){
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Query q = s.createQuery("from Drugs where pharmacie.ids =:pharmacie");
		q.setString("pharmacie", pharmacie);
		List<Drugs>list = q.list();
		s.close();
		return list;
	}
	
}
