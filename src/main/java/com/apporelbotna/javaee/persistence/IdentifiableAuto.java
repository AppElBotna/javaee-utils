package com.apporelbotna.javaee.persistence;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * A ready-to-extend implementation of {@link Identifiable}, using an auto-generated Integer
 * as identifier. If you want an Entity from your application to have an autoincrementable ID when
 * using one of the implementations of {@link JhenDAO#store(Identifiable)}, make it inherit this
 * class and it will be ready to go.
 * 
 * @author Jendoliver
 */
@MappedSuperclass
public class IdentifiableAuto implements Identifiable<Long>
{
	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@Override
	public Long getId()
	{
		return id;
	}
	
	@Override
	public void setId(Long id)
	{
		this.id = id;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		IdentifiableAuto other = (IdentifiableAuto)obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}
}
