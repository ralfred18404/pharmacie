package com.dao;


import org.hibernate.Query;
import org.hibernate.Session;

import com.domain.Director;

public class DirectorDao extends Source<Director> {

	public Director getOne(String id) {
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Director us = (Director)s.get(Director.class, id);
		s.close();
		return us;
	}
	public Director getPharmacie(String id) {
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Query q = s.createQuery("from Director where pharmacie.id =:id");
		q.setString("id", id);
		Director us = (Director)q.uniqueResult();
		s.close();
		return us;
	}
	public Director getHospital(String id) {
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Query q = s.createQuery("from Director where hospital.id =:id");
		q.setString("id", id);
		Director us = (Director)q.uniqueResult();
		s.close();
		return us;
	}
	public Director getPhone(String id) {
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Query q = s.createQuery("from Director where phoneNumber =:id");
		q.setString("id", id);
		Director us = (Director)q.uniqueResult();
		s.close();
		return us;
	}
	
}
