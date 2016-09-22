/*
 * Logger.java
 * Created by: Scott A. Roehrig
 * Created on: Jul 20, 2016 at 11:03:50 AM
 */
package org.apache.bazaar.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.validation.constraints.NotNull;

import org.apache.bazaar.nls.Messages;

/**
 * Logger provides a simplified adapter to the java.util.logging framework
 */
public final class Logger {

	// declare members

	private final java.util.logging.Logger logger;

	// declare constructors

	/**
	 * Constructor for Logger
	 * 
	 * @param logger The java.util.logging.Logger instance
	 */
	private Logger(@NotNull final java.util.logging.Logger logger) {
		super();
		this.logger = logger;
	}

	// declare methods

	/**
	 * Factory method for obtaining logger with
	 * name based on fully qualified name of Class
	 * passed and resource bundle already configured.
	 * 
	 * @param clazz The Class object to associate name
	 *        with logger
	 * @return The java.util.logging.Logger instance
	 */
	public static Logger newInstance(final Class<?> clazz) {
		return new Logger(java.util.logging.Logger.getLogger(clazz.getName(), Messages.RESOURCE_BUNDLE_BASE_NAME));
	}

	/**
	 * Returns true if underlying implementation is enabled
	 * for logging level
	 * 
	 * @param level The {@link java.util.logging.Level} to be
	 *        checked
	 * @return true if logger is enabled for logging level
	 */
	public boolean isLoggable(@NotNull final Level level) {
		return this.logger.isLoggable(level);
	}

	/**
	 * Logs information message.
	 * 
	 * @param message The information message to be logged
	 */
	public void info(@NotNull final String message) {
		this.logger.info(message);
	}

	/**
	 * Logs warning message
	 * 
	 * @param message The warning message to be logged
	 */
	public void warning(@NotNull final String message) {
		this.logger.warning(message);
	}

	/**
	 * Logs configuration message
	 * 
	 * @param message The configuration message to be logged
	 */
	public void config(@NotNull final String message) {
		this.logger.config(message);
	}

	/**
	 * Logs severe message
	 * 
	 * @param message The severe message to be logged
	 */
	public void severe(@NotNull final String message) {
		this.logger.severe(message);
	}

	/**
	 * Entering method. This method automatically populates
	 * the source class parameter sent to
	 * {@link java.util.logging.Logger#entering(String, String)}
	 * 
	 * @param methodName The name of method being entered
	 */
	public void entering(@NotNull final String methodName) {
		this.entering(methodName, (Object)null);
	}

	/**
	 * Entering method. This method automatically populates
	 * the source class parameter sent to
	 * {@link java.util.logging.Logger#entering(String, String, Object)}
	 * 
	 * @param methodName The name of method being entered
	 * @param methodParameter The single parameter sent to method
	 */
	public void entering(@NotNull final String methodName, final Object methodParameter) {
		this.entering(methodName, new Object[] { methodParameter });
	}

	/**
	 * Entering method. This method automatically populates the source class
	 * parameter sent to
	 * {@link java.util.logging.Logger#entering(String, String, Object[])}
	 * 
	 * @param methodName The name of method being entered
	 * @param methodParameters The parameters sent to method
	 */
	public void entering(@NotNull final String methodName, final Object[] methodParameters) {
		this.logger.entering(this.logger.getName(), methodName, methodParameters);
	}

	/**
	 * Exiting method. This method automatically populates the source class
	 * parameter sent to
	 * {@link java.util.logging.Logger#exiting(String, String)}
	 * 
	 * @param methodName The name of method being exited
	 */
	public void exiting(@NotNull final String methodName) {
		this.exiting(methodName, null);
	}

	/**
	 * Exiting method. The method automatically populates the source class
	 * parameter sent to
	 * {@link java.util.logging.Logger#exiting(String, String, Object)}
	 * 
	 * @param methodName The name of method being exited
	 * @param returnObject The object being returned from method
	 */
	public void exiting(@NotNull final String methodName, final Object returnObject) {
		this.logger.exiting(this.logger.getName(), methodName, returnObject);
	}

	/**
	 * Debug method. This method does not exist on the parent type. It is
	 * used to log debug statements. All debug statements are logged
	 * at FINE level.
	 * 
	 * @param methodName The name of method being executed
	 * @param message The debug message
	 * @param debugInformation The debug information
	 */
	public void debug(@NotNull final String methodName, @NotNull final String message, final Object debugInformation) {
		final LogRecord record = new LogRecord(Level.FINE, message);
		record.setSourceClassName(this.logger.getName());
		record.setSourceMethodName(methodName);
		if (debugInformation instanceof Throwable) {
			record.setThrown((Throwable)debugInformation);
		}
		else {
			record.setParameters(new Object[] { debugInformation });
		}
		this.logger.log(record);
	}

	/**
	 * Throwing method. This method automatically populates the source class
	 * parameter sent to
	 * {@link java.util.logging.Logger#throwing(String, String, Throwable)}
	 * 
	 * @param methodName The name of method being exiting
	 * @param throwable The throwable being processed
	 */
	public void throwing(@NotNull final String methodName, @NotNull final Throwable throwable) {
		this.logger.throwing(this.logger.getName(), methodName, throwable);
	}

	/**
	 * This method serves as a utility method to generate a basic
	 * {@link java.lang.String#toString()}
	 * method given a set of objects. It will generate a string of the basic
	 * form "objectIdentifier: [object, object, ....]"
	 * where objectIdentifier is provided by
	 * {@link System#identityHashCode(Object)}
	 * 
	 * @param object The object the toString is being generated for
	 * @param objects The array of objects to be rendered in the to string
	 *        output
	 * @return The string output
	 */
	public static String toString(final Object object, final Object[] objects) {
		final StringBuilder builder = new StringBuilder(500);
		builder.append(object.getClass().getName());
		builder.append("@");
		builder.append(System.identityHashCode(object));
		builder.append("[");
		for (int index = 0; index < objects.length; index++) {
			builder.append(objects[index]);
			if (index != (objects.length - 1)) {
				builder.append(", ");
			}
			else {
				builder.append("]");
			}
		}
		return builder.toString();
	}

}
