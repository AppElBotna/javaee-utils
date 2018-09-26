package com.apporelbotna.javaee.persistence;

import java.util.List;

/**
 * A generic, agnostic interface which defines what every DAO should be able to do, no matter
 * which persistence system it is implemented with.
 * 
 * @author Jendoliver
 */
public interface JhenDAO
{
	<T extends Identifiable<K>, K> void store(T entity) throws JhenPersistenceException;
	<T extends Identifiable<K>, K> void update(T entity) throws JhenPersistenceException;
	<T extends Identifiable<K>, K> void delete(T entity) throws JhenPersistenceException;
	<T extends Identifiable<K>, K> boolean isPersisted(T entity);
	<T extends Identifiable<K>, K> T getByKey(Class<T> clazz, K key);
	<T extends Identifiable<K>, K> List<T> findAll(Class<T> clazz);
	<T extends Identifiable<K>, K> Integer count(Class<T> clazz);
}
