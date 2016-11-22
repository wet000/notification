package com.wet.api.notification.dao.driver;

import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wet.api.notification.dao.SubscriberDao;
import com.wet.api.notification.model.Subscriber;

public class SubscriberDaoTestDriver 
{	
	private SubscriberDao subscriberDao;
	
	public void run()
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/spring.xml");
		subscriberDao = (SubscriberDao)context.getBean("subscriberDaoJpa");
		Subscriber subscriber = save();
		update(subscriber);
		context.close();
	}

	private Subscriber save()
	{
		Subscriber subscriber = new Subscriber();
		subscriber.setFormId((short)3);
		subscriber.setEmail("test@email.com");
		subscriber.setActive(Subscriber.ACTIVE);
		subscriber.setConfirmed(Subscriber.CONFIRMED);
		subscriber.setCreateDate(new Date());
		subscriber.setActivateDate(new Date());
		subscriber.setConfirmDate(new Date());
		subscriberDao.save(subscriber);
		
		return subscriber;
	}
	
	private void update(Subscriber subscriber)
	{
		Subscriber foundSubscriber = subscriberDao.find(subscriber.getId());
		foundSubscriber.setActive(Subscriber.INACTIVE);
		subscriberDao.save(foundSubscriber);
	}
	
	public static void main(String[] args) 
	{
		SubscriberDaoTestDriver testDriver = new SubscriberDaoTestDriver();
		testDriver.run();
	}
}