package com.wet.api.notification.dao.impl;

import org.springframework.stereotype.Repository;

import com.wet.api.common.dao.impl.AbstractDaoJpa;
import com.wet.api.notification.dao.SubscriberDao;
import com.wet.api.notification.model.Subscriber;

@Repository("subscriberDaoJpa")
public class SubscriberDaoJpa extends AbstractDaoJpa<Subscriber> implements SubscriberDao 
{
	public SubscriberDaoJpa() 
	{
		super(Subscriber.class);
	}
}