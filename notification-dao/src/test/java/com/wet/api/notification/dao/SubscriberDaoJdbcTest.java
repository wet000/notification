package com.wet.api.notification.dao;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SubscriberDaoJdbcTest extends SubscriberDaoTest
{
	@Autowired
	@Qualifier("subscriberDaoJdbc")
	private SubscriberDao subscriberDaoJdbc;

	@Override
	protected SubscriberDao getSubscriberDao() 
	{
		return subscriberDaoJdbc;
	}
	
	@Override
	@Test(expected=NotImplementedException.class)
	public void testFindAll()
	{
		super.testFindAll();
	}
	
	@Override
	@Test
	@Ignore
	public void testSave()
	{
		// H2 doesn't support now() function and ON DUPLICATE KEY
	}
	
	@Override
	@Test
	@Ignore
	public void testDelete()
	{
		// FindAll() is used to test delete which is unimplemented
	}
}