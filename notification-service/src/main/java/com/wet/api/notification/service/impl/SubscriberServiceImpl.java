package com.wet.api.notification.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wet.api.notification.model.Subscriber;
import com.wet.api.notification.service.SubscriberDaoService;
import com.wet.api.notification.service.SubscriberService;
import com.wet.api.notification.service.exception.DuplicateSubscriberException;

@Service("subscriberService")
public class SubscriberServiceImpl implements SubscriberService 
{
	@Autowired
	SubscriberDaoService subscriberDaoService;
	
	@Override
	public void subscribe(Subscriber subscriber) 
	{
		// This needs to be a new subscriber without an id
		Date currentDate = new Date();
		
		subscriber.setActive(Subscriber.ACTIVE);
		subscriber.setCreateDate(currentDate);
		subscriber.setActivateDate(currentDate);
		subscriberDaoService.save(subscriber);
	}

	@Override
	public void confirm(Subscriber subscriber) 
	{
		// The subscriber needs to have been saved and retrieved already
		Date currentDate = new Date();
		
		subscriber.setConfirmed(Subscriber.CONFIRMED);
		subscriber.setConfirmDate(currentDate);
		subscriberDaoService.save(subscriber);
	}

	@Override
	public void subscribeAndConfirm(Subscriber subscriber) throws DuplicateSubscriberException
	{
		// This needs to be a new subscriber without an id
		Date currentDate = new Date();
		
		subscriber.setActive(Subscriber.ACTIVE);
		subscriber.setConfirmed(Subscriber.CONFIRMED);
		subscriber.setCreateDate(currentDate);
		subscriber.setActivateDate(currentDate);
		subscriber.setConfirmDate(currentDate);
		subscriberDaoService.save(subscriber);
	}

	@Override
	public void unsubscribe(Subscriber subscriber) 
	{
		// The subscriber needs to have been saved and retrieved already
		Date currentDate = new Date();
		
		subscriber.setActive(Subscriber.INACTIVE);
		subscriber.setDeactivateDate(currentDate);
		subscriberDaoService.save(subscriber);
	}
}