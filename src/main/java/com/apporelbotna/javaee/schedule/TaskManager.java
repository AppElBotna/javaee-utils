package com.apporelbotna.javaee.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.apporelbotna.javaee.message.ApplicationMessages;

public class TaskManager
{
	private Scheduler scheduler;
	private List<Task> tasks;
	
	private static class ExceptionMessages extends ApplicationMessages
	{		
		@Override
		public String getBundleName()
		{
			return "exceptionMessages";
		}
	}
	
	private ExceptionMessages exceptionMessages;
	
	public TaskManager()
	{
		this(new ArrayList<>());
	}
	
	public TaskManager(List<Task> tasks)
	{
		this.tasks = tasks;
		exceptionMessages = new ExceptionMessages();
	}
	
	public void start()
	{
		try
		{
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			for(Task task : tasks)
				scheduler.scheduleJob(task.getJobDetail(), task.getTrigger());
			
			scheduler.start();
		}
		catch (SchedulerException e)
		{
			Logger.getGlobal().log(Level.SEVERE, exceptionMessages.get("scheduler.couldNotStart"), e);
		}
	}
	
	public void add(Task task)
	{
		tasks.add(task);
	}
	
	public void shutdown()
	{
		try
		{
			scheduler.shutdown(true);
		}
		catch (SchedulerException e)
		{
			Logger.getGlobal().log(Level.SEVERE, exceptionMessages.get("scheduler.couldNotStop"), e);
		}
	}
	
	public void forceShutdown()
	{
		try
		{
			scheduler.shutdown();
		}
		catch (SchedulerException e)
		{
			Logger.getGlobal().log(Level.SEVERE, exceptionMessages.get("scheduler.couldNotStop"), e);
		}
	}
}
