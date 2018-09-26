package com.apporelbotna.javaee.date;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * A representation of the period of time between two {@link LocalDateTime}. 
 * This class provides some utility methods which can be useful while treating these structures,
 * such as methods to check if an instant of time is contained between the interval, if it has
 * started/finished, if it's in progress, etc.<br>
 * <br>
 * Note that this class is an {@link Embeddable}, so when adding it to an {@link Entity} as a
 * property, the generated table for the entity will have both the startDate and endDate of the
 * DateInterval as columns instead of a reference to a DateInterval table which would be really
 * tedious to work with <i>(and kind of stupid as well)</i>.
 * 
 * @author Jendoliver
 */
@Embeddable
public class DateInterval
{
	public static final String START_END_SEPARATOR = "~";
	
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private boolean isLenient;
	
	protected DateInterval()
	{
		// JPA Constructor
	}
	
	public DateInterval(LocalDateTime startDate, LocalDateTime endDate)
	{
		this.startDate = startDate;
		this.endDate = endDate;
		checkDateValidity();
	}
	
	public DateInterval(DateInterval dateInterval)
	{
		this.startDate = LocalDateTime.of(dateInterval.startDate.toLocalDate(), dateInterval.startDate.toLocalTime());
		this.endDate = LocalDateTime.of(dateInterval.endDate.toLocalDate(), dateInterval.endDate.toLocalTime());
		this.isLenient = dateInterval.isLenient;
	}
	
	public static DateInterval of(LocalDateTime startDate, LocalDateTime endDate)
	{
		return new DateInterval(startDate, endDate);
	}
	
	public static DateInterval parse(String text, DateTimeFormatter formatter)
	{
		String[] startAndEndDates = text.split(START_END_SEPARATOR);
		if(startAndEndDates.length != 2)
			throw new IllegalArgumentException("The text to parse must contain two LocalDateTime "
					+ "instances separated by a " + START_END_SEPARATOR);
		
		LocalDateTime startDate = LocalDateTime.parse(startAndEndDates[0], formatter);
		LocalDateTime endDate = LocalDateTime.parse(startAndEndDates[1], formatter);
		
		return DateInterval.of(startDate, endDate);
	}
	
	public String format(DateTimeFormatter formatter)
	{
		return startDate.format(formatter) + START_END_SEPARATOR + endDate.format(formatter);
	}
	
	public boolean contains(LocalDateTime localDateTime)
	{
		return localDateTime.isAfter(startDate) && localDateTime.isBefore(endDate);
	}
	
	public boolean startsBefore(DateInterval other)
	{
		return this.startDate.isBefore(other.startDate);
	}
	
	public boolean startsAfter(DateInterval other)
	{
		return this.startDate.isAfter(other.startDate);
	}
	
	public boolean endsBefore(DateInterval other)
	{
		return this.endDate.isBefore(other.endDate);
	}
	
	public boolean endsAfter(DateInterval other)
	{
		return this.endDate.isAfter(other.endDate);
	}
	
	/**
	 * Returns true if and only if <b>this</b> DateInterval's
	 * end date is before the other's start date.
	 */
	public boolean isBefore(DateInterval other)
	{
		return this.endDate.isBefore(other.startDate);
	}
	
	/**
	 * Returns true if and only if <b>this</b> DateInterval's
	 * start date is after the other's end date.
	 */
	public boolean isAfter(DateInterval other)
	{
		return this.startDate.isAfter(other.endDate);
	}
	
	public boolean overlaps(DateInterval other)
	{
		return !(isBefore(other) || isAfter(other));
	}
	
	public boolean hasStarted()
	{
		return LocalDateTime.now().isAfter(startDate);
	}
	
	public boolean hasFinished()
	{
		return LocalDateTime.now().isAfter(endDate);
	}
	
	@Transient
	public boolean isInProgress()
	{
		return contains(LocalDateTime.now());
	}
	
	@Transient
	public Period getPeriod()
	{
		return Period.between(startDate.toLocalDate(), endDate.toLocalDate());
	}

	@Column(nullable = false)
	public LocalDateTime getStartDate()
	{
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate)
	{
		this.startDate = startDate;
		checkDateValidity();
	}

	@Column(nullable = false)
	public LocalDateTime getEndDate()
	{
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate)
	{
		this.endDate = endDate;
		checkDateValidity();
	}
	
	@Transient
	public boolean isLenient()
	{
		return isLenient;
	}
	
	public void setLenient(boolean isLenient)
	{
		this.isLenient = isLenient;
	}
	
	private void checkDateValidity()
	{
		// JPA might be creating the object, so we check for nulls
		if(startDate != null && endDate != null && startDate.isAfter(endDate))
			if(isLenient)
				startDate = LocalDateTime.of(endDate.toLocalDate(), endDate.toLocalTime());
			else
				throw new IllegalArgumentException("startDate must be before endDate");			
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DateInterval other = (DateInterval)obj;
		if (startDate == null)
		{
			if (other.startDate != null)
				return false;
		}
		else if (!startDate.equals(other.startDate))
			return false;
		if (endDate == null)
		{
			if (other.endDate != null)
				return false;
		}
		else if (!endDate.equals(other.endDate))
			return false;
		return true;
	}
}
