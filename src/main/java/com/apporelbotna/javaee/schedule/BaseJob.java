package com.apporelbotna.javaee.schedule;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

public interface BaseJob extends Job
{
	public default JobDetail getJobDetail()
	{
		return JobBuilder.newJob(this.getClass()).build();
	}
}
