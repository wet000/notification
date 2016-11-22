package com.wet.api.notification.service;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;

import com.wet.api.notification.dao.SubscriberDao;
import com.wet.api.notification.model.Subscriber;
import com.wet.api.notification.service.exception.DuplicateSubscriberException;
import com.wet.api.notification.service.impl.SubscriberDaoServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SubscriberDaoServiceImplTest 
{
	private SubscriberDaoService subscriberDaoService;
	
	@Mock
	private SubscriberDao mockSubscriberDao;
	
	@Before
	public void setup()
	{
		subscriberDaoService = new SubscriberDaoServiceImpl();
		Whitebox.setInternalState(subscriberDaoService, "subscriberDao", mockSubscriberDao);
	}
	
	@Test
	public void testFindById()
	{
		long id = 1L;
		Subscriber expected = new Subscriber();
		
		when(mockSubscriberDao.find(id)).thenReturn(expected);
		Subscriber found = subscriberDaoService.find(id);
		
		assertThat(found, sameInstance(expected));
		verify(mockSubscriberDao).find(id);
	}
	
	@Test
	public void testFindAll()
	{
		Subscriber subscriber = new Subscriber();
		List<Subscriber> expected = new ArrayList<Subscriber>();
		expected.add(subscriber);
		
		when(mockSubscriberDao.findAll()).thenReturn(expected);
		List<Subscriber> found = subscriberDaoService.findAll();
		
		assertThat(found, sameInstance(expected));
		verify(mockSubscriberDao).findAll();
	}

	@Test
	public void testSave()
	{
		Subscriber subscriber = new Subscriber();
		
		doNothing().when(mockSubscriberDao).save(subscriber);	
		subscriberDaoService.save(subscriber);
		
		verify(mockSubscriberDao).save(subscriber);
	}
	
	@Test(expected = DuplicateSubscriberException.class)
	public void testDuplicateSubscriberException()
	{
		Subscriber subscriber = new Subscriber();
		SQLException sqle = new SQLException("Reason");
		ConstraintViolationException cve = new ConstraintViolationException("Constraint violation message", sqle, "Duplicate entry violation");
		
		DataAccessException dae = new DataAccessException("Data access message", cve) 
		{
			private static final long serialVersionUID = 1L;
		};
		
		// Mock throwing the DataAccessException
		// This should be caught and a DuplicateSubscriberException 
		// should be thrown if a constraint violation occurred
		Mockito.doThrow(dae).when(mockSubscriberDao).save(subscriber);
		subscriberDaoService.save(subscriber);
	}
	
	@Test(expected = DataAccessException.class)
	public void testDataAccessException()
	{
		Subscriber subscriber = new Subscriber();
		
		DataAccessException dae = new DataAccessException("Data access message", new Exception("Exception message")) 
		{
			private static final long serialVersionUID = 1L;
		};
		
		// Mock throwing the DataAccessException
		// This should be caught and re-thrown since there's no constraint violation
		Mockito.doThrow(dae).when(mockSubscriberDao).save(subscriber);
		subscriberDaoService.save(subscriber);
	}

	@Test
	public void testDelete()
	{
		Subscriber subscriber = new Subscriber();
		
		doNothing().when(mockSubscriberDao).delete(subscriber);	
		subscriberDaoService.delete(subscriber);
		
		verify(mockSubscriberDao).delete(subscriber);
	}
}