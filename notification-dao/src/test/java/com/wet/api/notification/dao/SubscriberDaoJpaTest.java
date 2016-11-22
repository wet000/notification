package com.wet.api.notification.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;

import com.wet.api.notification.model.Subscriber;

public class SubscriberDaoJpaTest extends SubscriberDaoTest
{
	@Autowired
	@Qualifier("subscriberDaoJpa")
	private SubscriberDao subscriberDaoJpa;

	@Override
	protected SubscriberDao getSubscriberDao() 
	{
		return subscriberDaoJpa;
	}
	
	@Test(expected = DataAccessException.class)
	public void testDuplicateEntry()
	{
		Subscriber subscriber = getSubscriber();
		Subscriber duplicateSubscriber = getSubscriber();
		
		getSubscriberDao().save(subscriber);
		getSubscriberDao().save(duplicateSubscriber);
	}
}