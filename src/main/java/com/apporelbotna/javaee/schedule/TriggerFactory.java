package com.apporelbotna.javaee.schedule;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.text.MessageFormat;

public class TriggerFactory
{
	private static final String DAILY_CRON_EXP = "0 {0} {1} 1/1 * ? *";
	
	public static Trigger createScheduledTrigger(String name, String cronExpression)
	{
		return TriggerBuilder.newTrigger()
				.withIdentity(name)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				.startNow()
				.build();
	}
	
	public static Trigger createDailyTrigger(String name, int atHour, int atMinute)
	{
		return createScheduledTrigger(name, MessageFormat.format(DAILY_CRON_EXP, atMinute, atHour));
	}
	
	private TriggerFactory()
	{
		throw new IllegalAccessError();
	}
}
