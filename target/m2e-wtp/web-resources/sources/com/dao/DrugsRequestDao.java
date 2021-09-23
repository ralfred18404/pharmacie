package com.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.domain.DrugsRequest;

public class DrugsRequestDao extends Source<DrugsRequest> {
	
	public DrugsRequest getOne(String id) {
		Session s = SessionManager.getSession();
		s.beginTransaction();
		DrugsRequest us = (DrugsRequest)s.get(DrugsRequest.class, id);
		s.close();
		return us;
	}

	@SuppressWarnings("unchecked")
	public List<DrugsRequest>medecine(String pharmacie){
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Query q = s.createQuery("from DrugsRequest where request.director.id =:pharmacie order by request.requestDate desc");
		q.setString("pharmacie", pharmacie);
		List<DrugsRequest>list = q.list();
		s.close();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<DrugsRequest>drugsRequest(String pharmacie){
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Query q = s.createQuery("from DrugsRequest where category.drugs.pharmacie.ids =:pharmacie order by request.requestDate desc");
		q.setString("pharmacie", pharmacie);
		List<DrugsRequest>list = q.list();
		s.close();
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<DrugsRequest>drugsReportH(String pharmacie){
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Query q = s.createQuery("from DrugsRequest where request.director.hospital.id =:pharmacie order by request.requestDate desc");
		q.setString("pharmacie", pharmacie);
		List<DrugsRequest>list = q.list();
		s.close();
		return list;
	}
}
