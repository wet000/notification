package com.wet.api.notification.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import com.wet.api.notification.model.Subscriber;
import com.wet.api.notification.service.impl.SubscriberServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SubscriberServiceImplTest 
{
	private SubscriberService subscriberService;
	
	@Mock
	private SubscriberDaoService mockSubscriberDaoService;
	
	@Before
	public void setup()
	{
		subscriberService = new SubscriberServiceImpl();
		Whitebox.setInternalState(subscriberService, "subscriberDaoService", mockSubscriberDaoService);
	}
	
	@Test
	public void testSubscribe()
	{
		Subscriber subscriber = new Subscriber();
		
		doNothing().when(mockSubscriberDaoService).save(subscriber);
		subscriberService.subscribe(subscriber);
		
		assertTrue(subscriber.isActive());
		assertNotNull(subscriber.getCreateDate());
		assertNotNull(subscriber.getActivateDate());
		
		verify(mockSubscriberDaoService).save(subscriber);
	}
	
	@Test
	public void testConfirm()
	{
		Subscriber subscriber = new Subscriber();
		
		doNothing().when(mockSubscriberDaoService).save(subscriber);
		subscriberService.confirm(subscriber);
		
		assertTrue(subscriber.isConfirmed());
		assertNotNull(subscriber.getConfirmDate());
		
		verify(mockSubscriberDaoService).save(subscriber);
	}
	
	@Test
	public void testSubscribeAndConfirm()
	{
		Subscriber subscriber = new Subscriber();
		
		doNothing().when(mockSubscriberDaoService).save(subscriber);
		subscriberService.subscribeAndConfirm(subscriber);
		
		assertTrue(subscriber.isActive());
		assertTrue(subscriber.isConfirmed());
		assertNotNull(subscriber.getCreateDate());
		assertNotNull(subscriber.getActivateDate());
		assertNotNull(subscriber.getConfirmDate());
		
		verify(mockSubscriberDaoService).save(subscriber);
	}
	
	@Test
	public void testUnsubscribe()
	{
		Subscriber subscriber = new Subscriber();
		
		doNothing().when(mockSubscriberDaoService).save(subscriber);
		subscriberService.unsubscribe(subscriber);
		
		assertFalse(subscriber.isActive());
		assertNotNull(subscriber.getDeactivateDate());
		
		verify(mockSubscriberDaoService).save(subscriber);
	}
}