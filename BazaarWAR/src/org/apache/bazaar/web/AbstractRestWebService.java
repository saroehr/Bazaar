/*
 * AbstractRestWebService.java
 * Created by: Scott A. Roehrig
 * Created on: Aug 12, 2016 at 9:55:37 AM
 */
package org.apache.bazaar.web;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

import javax.naming.Binding;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.apache.bazaar.Identifier;
import org.apache.bazaar.Persistable;
import org.apache.bazaar.PersistableNotFoundException;
import org.apache.bazaar.i18n.Messages;
import org.apache.bazaar.logging.Logger;

/**
 * AbstractRestWebService extends implements RestWebService to provide a base
 * class suitable for sub class implementations
 */
// @Resource(name = "java:comp/env/jdbc/Bazaar", lookup = "jdbc/Bazaar", type =
// javax.sql.XADataSource.class, shareable = true, authenticationType =
// AuthenticationType.CONTAINER)
@Produces(MediaType.APPLICATION_JSON)
public abstract class AbstractRestWebService implements RestWebService {

	// declare members

	protected static final Logger LOGGER = Logger.newInstance(RestWebService.class);
	protected static final Messages MESSAGES = Messages.newInstance(Locale.getDefault());

	private static final Field IDENTIFIER_FIELD;

	static {
		try {
			final Field field = org.apache.bazaar.AbstractPersistable.class.getDeclaredField("identifier");
			field.setAccessible(true);
			IDENTIFIER_FIELD = field;
		}
		catch (final NoSuchFieldException | SecurityException | IllegalArgumentException exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	private InitialContext initialContext;
	private @Context HttpHeaders httpHeaders;
	private @Context SecurityContext securityContext;
	private @Context UriInfo uriInfo;

	// declare constructors

	/**
	 * Constructor for AbstractRestWebService
	 */
	AbstractRestWebService() {
		super();
	}

	// declare methods

	/*
	 * Utility method dumps the contents of the JNDI namespace
	 * 
	 * @param name The namespace name to list bindings for
	 * 
	 * @throws NamingException if the operation fails
	 */
	private static void dumpNamespace(@NotNull final String name) throws NamingException {
		final InitialContext context = new InitialContext();
		final NamingEnumeration<Binding> bindings = context.listBindings(name);
		AbstractRestWebService.LOGGER.config(name + " -->");
		while (bindings.hasMoreElements()) {
			final Binding binding = bindings.nextElement();
			if (binding.getObject() instanceof javax.naming.Context) {
				final NamingEnumeration<Binding> bindings1 = ((javax.naming.Context)binding.getObject())
						.listBindings("");
				while (bindings1.hasMoreElements()) {
					final Binding binding1 = bindings1.nextElement();
					AbstractRestWebService.LOGGER.config(binding1.toString());
				}
			}
			else {
				AbstractRestWebService.LOGGER.config(binding.toString());
			}
		}
		bindings.close();
	}

	/*
	 * Utility method to process throwable
	 * 
	 * @param exception The Throwable to process
	 * 
	 * @return The Response instance
	 */
	private static final Response processThrowable(final Throwable throwable) {
		AbstractRestWebService.LOGGER.entering("processThrowable", throwable);
		final Response response;
		if ((throwable instanceof PersistableNotFoundException)
				|| ((throwable.getCause() != null) && (throwable.getCause() instanceof PersistableNotFoundException))) {
			response = Response.status(HttpServletResponse.SC_NOT_FOUND).entity(throwable)
					.type(MediaType.APPLICATION_JSON_TYPE).language(Locale.getDefault())
					.encoding(org.apache.bazaar.web.config.Configuration.DEFAULT_ENCODING).build();
		}
		else {
			response = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).entity(throwable)
					.type(MediaType.APPLICATION_JSON_TYPE).language(Locale.getDefault())
					.encoding(org.apache.bazaar.web.config.Configuration.DEFAULT_ENCODING).build();
		}
		AbstractRestWebService.LOGGER.exiting("processThrowable", response);
		return response;
	}

	/**
	 * Utility method extracts instance of class passed
	 * from request
	 * 
	 * @param map The map to convert
	 * @return The Request Parameters instance
	 * @throws IllegalArgumentException if the instance could not be converted
	 */
	protected static final RequestParameters convert(@NotNull final Map<String, List<String>> map,
			@NotNull final RestWebServiceRequest request) throws IllegalArgumentException {
		return RequestParameters.newInstance(map);
	}

	/**
	 * Utility method sets identifier on {@link Persistable}
	 * using reflection to avoid exposing this to misuse
	 * 
	 * @param identifier The {@link Identifier} to be set
	 * @param persistable The {@link Persistable} to set it on
	 * @throws RestWebServiceException if the operation could
	 *         not be completed
	 */
	protected final static void setIdentifier(@NotNull final Identifier identifier,
			@NotNull final Persistable persistable) throws RestWebServiceException {
		try {
			AbstractRestWebService.IDENTIFIER_FIELD.set(persistable, identifier);
		}
		catch (final IllegalArgumentException | IllegalAccessException exception) {
			throw new RestWebServiceException(exception);
		}
	}

	/**
	 * Utility method constructs {@link Response} instance. The response
	 * is configured with {@link Response.Status.OK} status instance,
	 * {@link Entity} instance,
	 * instance, the Locale set to (@see Locale#getDefault()), and the encoding
	 * set to
	 * {@link org.apache.bazaar.web.config.Configuration.DEFAULT_ENCODING}
	 * 
	 * If callers wish to override the status, they should update the builder
	 * with the appropriate status
	 * 
	 * @param entity The {@link GenericEntity} instance
	 * 
	 * @return The {@link ResponseBuilder} instance
	 */
	protected final static ResponseBuilder newResponse(final GenericEntity<?> entity) {
		return Response.status(Response.Status.OK).language(Locale.getDefault())
				.encoding(org.apache.bazaar.web.config.Configuration.DEFAULT_ENCODING).entity(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.RestWebService#doDelete()
	 */
	@Override
	public final Response doDelete() {
		AbstractRestWebService.LOGGER.entering("doDelete");
		Response response;
		try {
			response = this.doDelete(new RestWebServiceRequest(this.securityContext, this.httpHeaders, this.uriInfo));
		}
		catch (final Throwable throwable) {
			response = AbstractRestWebService.processThrowable(throwable);
		}
		AbstractRestWebService.LOGGER.exiting("doDelete", response);
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.RestWebService#doGet()
	 */
	@Override
	public final Response doGet() {
		AbstractRestWebService.LOGGER.entering("doGet");
		Response response;
		try {
			response = this.doGet(new RestWebServiceRequest(this.securityContext, this.httpHeaders, this.uriInfo));
		}
		catch (final Throwable throwable) {
			response = AbstractRestWebService.processThrowable(throwable);
		}
		AbstractRestWebService.LOGGER.exiting("doGet", response);
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.RestWebService#doPost()
	 */
	@Override
	public final Response doPost() {
		AbstractRestWebService.LOGGER.entering("doPost");
		Response response;
		try {
			response = this.doPost(new RestWebServiceRequest(this.securityContext, this.httpHeaders, this.uriInfo));
		}
		catch (final Throwable throwable) {
			response = AbstractRestWebService.processThrowable(throwable);
		}
		AbstractRestWebService.LOGGER.exiting("doPost", response);
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.bazaar.RestWebService#doPut()
	 */
	@Override
	public final Response doPut() {
		AbstractRestWebService.LOGGER.entering("doPut");
		Response response;
		try {
			response = this.doPut(new RestWebServiceRequest(this.securityContext, this.httpHeaders, this.uriInfo));
		}
		catch (final Throwable throwable) {
			response = AbstractRestWebService.processThrowable(throwable);
		}
		AbstractRestWebService.LOGGER.exiting("doPut", response);
		return response;
	}

	/**
	 * Subclasses may override to handle GET request processing
	 * The default behavior is to throw RestWebServiceException
	 * 
	 * @param request The {@link RestWebServiceRequest} instance
	 * @return The Response instance
	 * @throws Throwable if the request could
	 *         not be processed
	 */
	protected Response doGet(@NotNull final RestWebServiceRequest request) throws Throwable {
		throw new RestWebServiceException(AbstractRestWebService.MESSAGES.findMessage(
				org.apache.bazaar.web.config.Configuration.UNSUPPORTED_METHOD_MESSAGE, new Object[] { "doGet" }));
	}

	/**
	 * Subclasses may override to handle POST request processing
	 * The default behavior is to throw RestWebServiceException
	 * 
	 * @param request The {@link RestWebServiceRequest} instance
	 * @return The Response instance
	 * @throws Throwable if the request could
	 *         not be processed
	 */
	protected Response doPost(@NotNull final RestWebServiceRequest request) throws Throwable {
		throw new RestWebServiceException(AbstractRestWebService.MESSAGES.findMessage(
				org.apache.bazaar.web.config.Configuration.UNSUPPORTED_METHOD_MESSAGE, new Object[] { "doPost" }));
	}

	/**
	 * Subclasses may override to handle PUT request processing
	 * The default behavior is to throw RestWebServiceException
	 * 
	 * @param request The {@link RestWebServiceRequest} instance
	 * @return The Response instance
	 * @throws Throwable if the request could
	 *         not be processed
	 */
	protected Response doPut(@NotNull final RestWebServiceRequest request) throws Throwable {
		throw new RestWebServiceException(AbstractRestWebService.MESSAGES.findMessage(
				org.apache.bazaar.web.config.Configuration.UNSUPPORTED_METHOD_MESSAGE, new Object[] { "doPut" }));
	}

	/**
	 * Subclasses may override to handle DELETE request processing
	 * The default behavior is to throw RestWebServiceException
	 * 
	 * @param request The {@link RestWebServiceRequest} instance
	 * @return The Response instance
	 * @throws Throwable if the request could
	 *         not be processed
	 */
	protected Response doDelete(@NotNull final RestWebServiceRequest request) throws Throwable {
		throw new RestWebServiceException(AbstractRestWebService.MESSAGES.findMessage(
				org.apache.bazaar.web.config.Configuration.UNSUPPORTED_METHOD_MESSAGE, new Object[] { "doDelete" }));
	}

	/**
	 * Looks up object from name space
	 * 
	 * @return The object from name space
	 * @throws RestWebServiceException if the object
	 *         could not be retrieved
	 */
	protected final Object lookup(@NotNull final String name) throws RestWebServiceException {
		final Object result;
		try {
			if (this.initialContext == null) {
				this.initialContext = new InitialContext();
			}
			if (AbstractRestWebService.LOGGER.isLoggable(Level.CONFIG)) {
				AbstractRestWebService.dumpNamespace("java:app");
			}
			result = this.initialContext.lookup(name);
		}
		catch (final NamingException exception) {
			throw new RestWebServiceException(exception);
		}
		return result;
	}

}
