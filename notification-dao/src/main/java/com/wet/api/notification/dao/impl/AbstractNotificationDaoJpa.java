package com.wet.api.notification.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.wet.api.common.dao.impl.AbstractDaoJpa;
import com.wet.api.common.model.DomainEntity;

public class AbstractNotificationDaoJpa<T extends DomainEntity> extends AbstractDaoJpa<T>
{
	@PersistenceContext(unitName="notificationPersistenceUnit")
	protected EntityManager entityManager;
	
	public AbstractNotificationDaoJpa(Class<T> type)
	{
		super(type);
	}

	@Override
	protected EntityManager getEntityManager()
	{
		return entityManager;
	}
}