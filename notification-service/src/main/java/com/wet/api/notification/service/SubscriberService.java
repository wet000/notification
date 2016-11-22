package com.wet.api.notification.service;

import com.wet.api.common.service.CommonService;
import com.wet.api.notification.model.Subscriber;

public interface SubscriberService extends CommonService 
{
	/**
	 * Subscribes a {@link Subscriber}
	 *
	 * @param 	subscriber	The {@link Subscriber} to subscribe
	 */
	public void subscribe(Subscriber subscriber);
	
	/**
	 * Confirms a {@link Subscriber}
	 *
	 * @param 	subscriber	The {@link Subscriber} to confirm
	 */
	public void confirm(Subscriber subscriber);
	
	/**
	 * Subscribes and confirms a {@link Subscriber}
	 *
	 * @param 	subscriber	The {@link Subscriber} to subscribe and confirm
	 */
	public void subscribeAndConfirm(Subscriber subscriber);
	
	/**
	 * Unsubscribes a {@link Subscriber}
	 *
	 * @param 	subscriber	The {@link Subscriber} to unsubscribe
	 */
	public void unsubscribe(Subscriber subscriber);
}