package com.wet.api.notification.dao.impl;

import org.springframework.stereotype.Repository;

import com.wet.api.notification.dao.SubscriberDao;
import com.wet.api.notification.model.Subscriber;

@Repository("subscriberDaoJpa")
public class SubscriberDaoJpa extends AbstractNotificationDaoJpa<Subscriber> implements SubscriberDao 
{
	public SubscriberDaoJpa() 
	{
		super(Subscriber.class);
	}
}