package com.sunkun.gh.domain.operator;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.sunkun.gh.domain.Hospital;

public class DBTool {

	public static void save(Object object)
	{
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		

		session.save(object);
		
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
}
	
	public static void update(Object object)
	{
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		

		session.update(object);
		
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
}
	
	public static List search(Class cls,String table,String condition) throws ClassNotFoundException
	{
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		

		//session.save(object);
		String sql="select * from "+table;
		if(condition!=null&&!condition.equalsIgnoreCase(""))
			
			sql+=" where "+condition;
		Query q = session.createSQLQuery(sql).addEntity(cls);
		List rs = q.list();
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();  
		return rs;
	}
}
