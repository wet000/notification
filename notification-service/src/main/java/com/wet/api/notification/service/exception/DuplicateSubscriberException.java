package com.wet.api.notification.service.exception;

public class DuplicateSubscriberException extends RuntimeException
{
	private static final long serialVersionUID = 1723508895950898183L;

	public DuplicateSubscriberException()
	{
		super();
	}

	public DuplicateSubscriberException(String message)
	{
		super(message);
	}

	public DuplicateSubscriberException(String message, Throwable cause)
	{
		super(message,cause);
	}

	public DuplicateSubscriberException(Throwable cause)
	{
		super(cause);
	}
}