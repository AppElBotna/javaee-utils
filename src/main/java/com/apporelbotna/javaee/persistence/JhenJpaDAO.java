package com.apporelbotna.javaee.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.apporelbotna.javaee.persistence.JhenPersistenceException.Reason;

/**
 * This class provides generic CRUD functionality for all DAOs using JPA as a
 * persistence specification. The concrete implementation of JPA, i.e Hibernate, EclipseLink, etc.
 * does not matter.
 * <br><br>
 * An application desiring to implement persistence with JPA should extend this class to provide
 * application-specific methods, i.e operations for treating with entities of the application domain.
 * 
 * @author Jendoliver
 */
public abstract class JhenJpaDAO implements IJhenJpaDAO
{
	protected EntityManager entityManager;
	protected EntityTransaction transaction;
	protected boolean autoCommit = true;

	public JhenJpaDAO()
	{
		entityManager = Persistence
				.createEntityManagerFactory(getPersistenceUnitName())
				.createEntityManager();
		
		transaction = entityManager.getTransaction();
	}

	/**
	 * Override this method in your DAO implementation by returning the name of the
	 * Persistence Unit as declared in your persistence.xml file. For instance, if you have written
	 * this is your persistence.xml:<br><br>
	 * 
	 * <code>
	 * ...<br>
	 * persistence-unit name="com.apporelbotna.sickpersistenceunit"<br>
	 * ...<br><br>
	 * </code>
	 * 
	 * You should then make this method <code>return "com.apporelbotna.sickpersistenceunit"</code>
	 */
	protected abstract String getPersistenceUnitName();

	@Override
	public CriteriaBuilder getCriteriaBuilder()
	{
		return entityManager.getCriteriaBuilder();
	}

	@Override
	public void beginTransaction()
	{
		transaction.begin();
	}

	@Override
	public void commit()
	{
		transaction.commit();
	}
	
	@Override
	public void setAutoCommit(boolean autoCommit)
	{
		this.autoCommit = autoCommit;
	}
	
	@Override
	public void rollback()
	{
		transaction.rollback();
	}
	
	@Override
	public void close()
	{
		entityManager.close();
	}

	@Override
	public <T extends Identifiable<K>, K> List<T> find(CriteriaQuery<T> query)
	{
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public <T extends Identifiable<K>, K> T get(CriteriaQuery<T> query)
	{
		List<T> entities = find(query);
		return entities.isEmpty() ? null : entities.get(0);
	}

	@Override
	public <T extends Identifiable<K>, K> void store(T entity) throws JhenPersistenceException
	{
		if (isPersisted(entity))
		{
			update(entity);
			return;
		}
		
		if(autoCommit)
			transaction.begin();
		
		entityManager.persist(entity);
		
		if(autoCommit)
			transaction.commit();
	}

	@Override
	public <T extends Identifiable<K>, K> void update(T entity) throws JhenPersistenceException
	{
		if (!isPersisted(entity))
			throw new JhenPersistenceException(Reason.ENTITY_NOT_PERSISTED);
		
		entityManager.merge(entity);
	}

	@Override
	public <T extends Identifiable<K>, K> void delete(T entity) throws JhenPersistenceException
	{
		if (!isPersisted(entity))
			throw new JhenPersistenceException(Reason.ENTITY_NOT_PERSISTED);
		
		entityManager.remove(entity);
	}

	@Override
	public <T extends Identifiable<K>, K> boolean isPersisted(T entity)
	{
		return entityManager.contains(entity);
	}

	@Override
	public <T extends Identifiable<K>, K> T getByKey(Class<T> clazz, K key)
	{
		return entityManager.find(clazz, key);
	}

	@Override
	public <T extends Identifiable<K>, K> List<T> findAll(Class<T> clazz)
	{
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root);
		
		return find(query);
	}

	@Override
	public <T extends Identifiable<K>, K> Integer count(Class<T> clazz)
	{
		List<T> entities = findAll(clazz);
		return entities == null ? null : entities.size();
	}
}
