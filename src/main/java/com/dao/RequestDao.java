package com.dao;

import org.hibernate.Session;


import com.domain.Request;

public class RequestDao extends Source<Request> {

	public Request getOne(String id) {
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Request us = (Request)s.get(Request.class, id);
		s.close();
		return us;
	}
}
