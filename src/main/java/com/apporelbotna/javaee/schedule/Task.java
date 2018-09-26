package com.apporelbotna.javaee.schedule;

import org.quartz.JobDetail;
import org.quartz.Trigger;

public class Task
{
	private BaseJob job;
	private Trigger trigger;
	
	public Task(BaseJob job, Trigger trigger)
	{
		this.job = job;
		this.trigger = trigger;
	}
	
	public BaseJob getBaseJob()
	{
		return job;
	}
	
	public JobDetail getJobDetail()
	{
		return job.getJobDetail();
	}
	
	public Trigger getTrigger()
	{
		return trigger;
	}
}
