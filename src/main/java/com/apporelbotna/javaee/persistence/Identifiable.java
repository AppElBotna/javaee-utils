package com.apporelbotna.javaee.persistence;

import javax.persistence.Entity;

/**
 * Every {@link Entity} on an application using an implementation of {@link JhenDAO}
 * should implement this interface to be able to use the generic DAO's operations.<br><br>
 * 
 * It is highly recommended to avoid naming some of your classes fields "id", just
 * to make it JavaBeans compliant. Instead if, for instance, you have a class User with an
 * email which you want to use as primary key, write it like this:<br>
 * <br>
 * <code>
 * public class User implements Identifiable&lt;String&gt;<br>
 * {<br>
 * &nbsp; private String username;<br>
 * <br>
 * &nbsp; public String getUsername() { return username; }<br>
 * &nbsp; public String setUsername(String username) { this.username = username; }<br>
 * <br>
 * &nbsp; @Id<br>
 * &nbsp; @Column(name = "username")<br>
 * &nbsp; @Override<br>
 * &nbsp; public String getId() { getUsername(); }<br>
 * &nbsp; public String setId(String id) { setUsername(id); }<br>
 * }</code><br>
 * <br>
 * This way, your model will stay readable, since any newcomer will easily guess that
 * the user has an username and that it is also its PK in the persistence system.<br>
 * <br>
 * <i>Note: if you are looking for an easy way to autogenerate an ID for your entities, please
 * refer to {@link IdentifiableAuto}</i>
 * <br><br>
 * <img src="https://vignette.wikia.nocookie.net/enciclopediadelmisterio/images/0/02/Imgupload_1336072826_615.jpg/revision/latest?cb=20120702183721&path-prefix=es"/>
 * <br><i>Identify me!</i>
 *
 * @param <T> The type of  the entity's primary key
 * 
 * @author Jendoliver
 */
public interface Identifiable<T>
{
	T getId();
	void setId(T id);
}
