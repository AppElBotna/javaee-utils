package com.apporelbotna.javaee.message;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Extend this class to centralize the ResourceBundle creation on one
 * point of your application.
 * 
 * For any different {@link ResourceBundle} that an application might have,
 * one subclass of ApplicationMessages should be created. By implementing
 * {@link #getBundleName()}, the package path where the .properties files
 * for the concrete {@link ResourceBundle} will be written just one time on your
 * entire application and no explicit creation of it will be needed.
 * 
 * Think about this class as a ResourceBundle factory. It also provides utility methods
 * to retrieve parametrized messages (see {@link #get(String, Object...)}
 * 
 * @author Jendoliver
 */
public abstract class ApplicationMessages
{
	protected ResourceBundle bundle;
	
	public ApplicationMessages()
	{
		bundle = ResourceBundle.getBundle(getBundleName(), Locale.getDefault());
	}
	
	public String get(String code)
	{
		return bundle.getString(code);
	}
	
	/**
	 * Used to retrieve a parameterized message from the properties files
	 */
	public String get(String code, Object... parameters)
	{
		String message = get(code);
		return MessageFormat.format(message, parameters);
	}
	
	public ResourceBundle getBundle()
	{
		return bundle;
	}
	
	public void setBundle(ResourceBundle bundle)
	{
		this.bundle = bundle;
	}
	
	public void setBundle(String bundleName)
	{
		bundle = ResourceBundle.getBundle(bundleName, Locale.getDefault());
	}
	
	public void setLocale(Locale locale)
	{
		bundle = ResourceBundle.getBundle(bundle.getBaseBundleName(), locale);
	}
	
	public abstract String getBundleName();
}
