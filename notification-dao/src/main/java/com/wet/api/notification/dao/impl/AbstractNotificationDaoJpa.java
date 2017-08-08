package com.wet.api.notification.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.wet.api.common.dao.impl.AbstractDaoJpa;
import com.wet.api.common.model.DomainEntity;

public class AbstractNotificationDaoJpa<T extends DomainEntity> extends AbstractDaoJpa<T>
{
	@PersistenceContext(unitName="notificationPersistenceUnit")
	private EntityManager entityManager;
	
	public AbstractNotificationDaoJpa(Class<T> type)
	{
		super(type);
	}

	@Override
	protected EntityManager getEntityManager()
	{
		return entityManager;
	}
	
	@Override
	@Transactional(value="notificationTransactionManager", readOnly=true)
	public T find(long id) 
	{
		return super.find(id);
	}

	@Override
	@Transactional(value="notificationTransactionManager", readOnly=true)
	public List<T> findAll() 
	{
		return super.findAll();
	}

	@Override
	@Transactional(value="notificationTransactionManager")
	public void save(T object) 
	{
		super.save(object);
	}

	@Override
	@Transactional(value="notificationTransactionManager")
	public void delete(T object) 
	{
		super.delete(object);
	}
}