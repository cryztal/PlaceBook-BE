package com.exadel.placebook.dao.impl;

import com.exadel.placebook.dao.OfficeDao;
import com.exadel.placebook.model.entity.Office;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;



@Repository
public class OfficeDaoImpl extends BaseDaoImpl<Office> implements OfficeDao {


    @Override
    public Office save(Office office) throws HibernateException {
        Session session = getSession();
        session.save(office);
        return office;
    }

    @Override
    public Office update(Office office){
        Session session = getSession();
        session.update(office);
        return office;
    }
    @Override
    public Office findById(Long id) {
        Session session = getSession();
        Query<Office> query =session.createQuery("from Office p where p.id=:id",Office.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }



}
