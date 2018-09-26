package com.apporelbotna.javaee.persistence;

/**
 * Exception thrown by the implementations of {@link JhenDAO}
 * 
 * @author Jendoliver
 */
public class JhenPersistenceException extends Exception
{
	private static final long serialVersionUID = 5083497374657147435L;

	public enum Reason
	{
		ENTITY_ALREADY_EXISTS("The entity is already persisted and cannot be inserted again"),
		ENTITY_NOT_PERSISTED("The entity is not persisted, so it cannot be updated nor deleted"),
		COMMIT_ERROR("There was an error on the commit operation");

		private String msg;
		private Reason(String msg)
		{
			this.msg = msg;
		}

		public String getMsg()
		{
			return msg;
		}
	}

	public JhenPersistenceException(Reason reason)
	{
		super(reason.msg);
	}
}
