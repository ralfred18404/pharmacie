package com.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.domain.District;



public class DistrictDao extends Source<District> {

	public District getOne(String id) {
		Session s = SessionManager.getSession();
		s.beginTransaction();
		District us = (District)s.get(District.class, id);
		s.close();
		return us;
	}
	
	@SuppressWarnings("unchecked")
	public List<District>districtList(String province){
		Session s = SessionManager.getSession();
		s.beginTransaction();
		Query q = s.createQuery("from District where province.id =:province");
		q.setString("province", province);
		List<District>list = q.list();
		s.close();
		return list;
	}
	
}
