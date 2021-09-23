package com.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.domain.Category;;

public class CategoryDao extends Source<Category> {

	public Category getOne(String id) {
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Category us = (Category)s.get(Category.class, id);
		s.close();
		return us;
	}
	
	@SuppressWarnings("unchecked")
	public List<Category>medecine(String pharmacie){
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Query q = s.createQuery("from Category where status ='Used' and drugs.id =:pharmacie");
		q.setString("pharmacie", pharmacie);
		List<Category>list = q.list();
		s.close();
		return list;
	}
}
