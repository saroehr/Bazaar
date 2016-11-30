/*
 * RestClient.java
 * Created On: Sep 29, 2016
 * Created By: Scott A. Roehrig (saroehr@hotmail.com)
 */
package org.apache.bazaar.web;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.bazaar.BazaarException;
import org.glassfish.jersey.CommonProperties;

/**
 * RestWebClient implements {@link Client} to provide a concrete implementation
 * for the repository.
 */
public final class RestWebClient implements Client, AutoCloseable {

	// declare members

	private static final ClientBuilder CLIENT_BUILDER;
	static {
		CLIENT_BUILDER = ClientBuilder.newBuilder();
		final List<Class<?>> providers = Arrays.asList(org.apache.bazaar.web.config.Configuration.PROVIDER_CLASSES);
		for (final Class<?> provider : providers) {
			RestWebClient.CLIENT_BUILDER.register(provider);
		}
		RestWebClient.CLIENT_BUILDER.property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);
	}

	private Client client;

	// declare constructors

	/**
	 * Constructor for RestWebClient
	 *
	 * @param client The client instance
	 */
	private RestWebClient(@NotNull final Client client) {
		super();
		this.client = client;
	}

	// declare methods

	/**
	 * Factory method for obtaining instance.
	 *
	 * @return Instance of RestWebClient
	 * @throws RestWebClientException if the instance could not be returned
	 */
	public static @NotNull RestWebClient newInstance() throws RestWebClientException {
		return new RestWebClient(RestWebClient.CLIENT_BUILDER.build());
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.core.Configurable#getConfiguration()
	 */
	@Override
	public Configuration getConfiguration() {
		return this.client.getConfiguration();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.core.Configurable#property(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public Client property(final String name, final Object value) {
		this.client = this.client.property(name, value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.core.Configurable#register(java.lang.Class)
	 */
	@Override
	public Client register(final Class<?> componentClass) {
		this.client = this.client.register(componentClass);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.core.Configurable#register(java.lang.Object)
	 */
	@Override
	public Client register(final Object component) {
		this.client = this.client.register(component);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.core.Configurable#register(java.lang.Class, int)
	 */
	@Override
	public Client register(final Class<?> componentClass, final int priority) {
		this.client = this.client.register(componentClass, priority);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.core.Configurable#register(java.lang.Class,
	 * java.lang.Class[])
	 */
	@Override
	public Client register(final Class<?> componentClass, final Class<?>... contracts) {
		this.client = this.client.register(componentClass, contracts);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.core.Configurable#register(java.lang.Class,
	 * java.util.Map)
	 */
	@Override
	public Client register(final Class<?> componentClass, final Map<Class<?>, Integer> contracts) {
		this.client = this.client.register(componentClass, contracts);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.core.Configurable#register(java.lang.Object, int)
	 */
	@Override
	public Client register(final Object component, final int priority) {
		this.client = this.client.register(component, priority);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.core.Configurable#register(java.lang.Object,
	 * java.lang.Class[])
	 */
	@Override
	public Client register(final Object component, final Class<?>... contracts) {
		this.client = this.client.register(component, contracts);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.core.Configurable#register(java.lang.Object,
	 * java.util.Map)
	 */
	@Override
	public Client register(final Object component, final Map<Class<?>, Integer> contracts) {
		this.client = this.client.register(component, contracts);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.client.Client#close()
	 */
	@Override
	public void close() {
		this.client.close();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.client.Client#getHostnameVerifier()
	 */
	@Override
	public HostnameVerifier getHostnameVerifier() {
		return this.client.getHostnameVerifier();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.client.Client#getSslContext()
	 */
	@Override
	public SSLContext getSslContext() {
		return this.client.getSslContext();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.client.Client#invocation(javax.ws.rs.core.Link)
	 */
	@Override
	public Builder invocation(final Link link) {
		return this.client.invocation(link);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.client.Client#target(java.lang.String)
	 */
	@Override
	public WebTarget target(final String uri) {
		return this.client.target(uri);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.client.Client#target(java.net.URI)
	 */
	@Override
	public WebTarget target(final URI uri) {
		return this.client.target(uri);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.client.Client#target(javax.ws.rs.core.UriBuilder)
	 */
	@Override
	public WebTarget target(final UriBuilder uriBuilder) {
		return this.client.target(uriBuilder);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.client.Client#target(javax.ws.rs.core.Link)
	 */
	@Override
	public WebTarget target(final Link link) {
		return this.client.target(link);
	}

	/**
	 * Utility method processes response instance
	 *
	 * @param type The type of persistable to be generated
	 * @param response The response instance
	 * @return The Persistable returned
	 * @throws BazaarException if the response was could not be processed
	 */
	@SuppressWarnings("unchecked")
	public static <T> T processResponse(@NotNull final GenericType<T> type, @NotNull final Response response)
			throws BazaarException {
		final Object object;
		if (MediaType.APPLICATION_JSON_TYPE.equals(response.getMediaType()) && response.hasEntity()) {
			try {
				if (Response.Status.Family.CLIENT_ERROR.equals(Response.Status.Family.familyOf(response.getStatus()))
						|| Response.Status.Family.SERVER_ERROR
								.equals(Response.Status.Family.familyOf(response.getStatus()))) {
					throw response.readEntity(Throwable.class);
				}
				object = response.readEntity(type);
			}
			catch (final Throwable throwable) {
				if (throwable instanceof BazaarException) {
					throw (BazaarException)throwable;
				}
				else if (throwable.getCause() != null && throwable.getCause() instanceof BazaarException) {
					throw (BazaarException)throwable.getCause();
				}
				else {
					throw new BazaarException(throwable);
				}
			}
		}
		else {
			throw new BazaarException(new RestWebServiceException(response.getStatusInfo().toString()));
		}
		// close the response instance
		response.close();
		return (T)object;
	}

}
