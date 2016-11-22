package com.wet.api.notification.service.impl;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wet.api.common.dao.Dao;
import com.wet.api.common.service.impl.AbstractCommonDaoService;
import com.wet.api.notification.dao.SubscriberDao;
import com.wet.api.notification.model.Subscriber;
import com.wet.api.notification.service.SubscriberDaoService;
import com.wet.api.notification.service.exception.DuplicateSubscriberException;

@Service("subscriberDaoService")
public class SubscriberDaoServiceImpl extends AbstractCommonDaoService<Subscriber> implements SubscriberDaoService 
{
	@Autowired
	@Qualifier("subscriberDaoJpa")
	private SubscriberDao subscriberDao;

	@Override
	protected Dao<Subscriber> getDao() 
	{
		return subscriberDao;
	}
	
	@Override
	public void save(Subscriber subscriber)
	{
		try
		{
			super.save(subscriber);
		}
		catch (DataAccessException e) 
		{
		    Throwable t = e.getCause();
		    while ((t != null) && !(t instanceof ConstraintViolationException))
		    {
		        t = t.getCause();
		    }
		    if (t instanceof ConstraintViolationException) 
		    {
		    	// TODO: 
		    	// 1. Check to see if the constraint violation is due to duplicate email and formId.
		    	// Otherwise, just throw the exception or deal with it here.
		    	// 2. Need to get the message from a property file for internationalization. 
		    	// This message may more generic and only used for logging. The message that the application
		    	// client uses should be more specific to the application. But this is an example:
				throw new DuplicateSubscriberException(subscriber.getEmail() + " has already subscribed.");
		    }
		    throw e;
		}
	}
}