package com.wet.api.notification.service;

import com.wet.api.common.service.CommonDaoService;
import com.wet.api.notification.model.Subscriber;

public interface SubscriberDaoService extends CommonDaoService<Subscriber> 
{	
	/**
	 * Find a specific {@link Subscriber}.
	 * 
	 * @param 	email 	The email of the {@link Subscriber} to search for
	 * @return 			The found {@link Subscriber} (or null)
	 */
	//public Subscriber find(String email);

	/**
	 * Find all {@link Subscriber}s based on the given {@link SubscriberSearch}.
	 *
	 * @param 	search 	The search criteria used to search for {@link Subscriber}s
	 * @return			A {@link List} of {@link Subscriber}
	 */
	//public List<Subscriber> find(SubscriberSearch search);
}