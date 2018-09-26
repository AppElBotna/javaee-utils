package com.apporelbotna.javaee.persistence;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * JPA concrete interface
 * 
 * @see JhenDAO
 * @see JhenJpaDAO
 * @author Jendoliver
 */
public interface IJhenJpaDAO extends JhenDAO
{
	void beginTransaction();
	void commit();
	void setAutoCommit(boolean autoCommit);
	void rollback();
	void close();
	CriteriaBuilder getCriteriaBuilder();
	<T extends Identifiable<K>, K> List<T> find(CriteriaQuery<T> query);
	<T extends Identifiable<K>, K> T get(CriteriaQuery<T> query);
}
