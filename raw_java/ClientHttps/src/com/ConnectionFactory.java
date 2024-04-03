package com;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class ConnectionFactory implements HttpURLConnectionFactory {

	Proxy proxy;
	String proxyHost = "proxy.tcs.com";
	Integer proxyPort = 8080;
	SSLContext sslContext;

	public ConnectionFactory() {
	}

	public ConnectionFactory(String proxyHost, Integer proxyPort) {
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
	}

	private void initializeProxy() {
		proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
	}

	@Override
	public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
		initializeProxy();
		HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
		if (con instanceof HttpsURLConnection) {
			System.out.println("The valus is....");
			HttpsURLConnection httpsCon = (HttpsURLConnection) url.openConnection(proxy);
			httpsCon.setHostnameVerifier(getHostnameVerifier());
			httpsCon.setSSLSocketFactory(getSslContext().getSocketFactory());
			return httpsCon;
		} else {
			return con;
		}

	}

	public SSLContext getSslContext() {
		try {
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { new SecureTrustManager() }, new SecureRandom());
		} catch (NoSuchAlgorithmException ex) {
			// Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null,ex);
		} catch (KeyManagementException ex) {
			// Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null,ex);
		}
		return sslContext;
	}

	private HostnameVerifier getHostnameVerifier() {
		return new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
				return true;
			}
		};
	}

	public static void main(String[] args) {
		String uri ="https://34.212.65.116:8080/v1/tests";//"http://dummy.restapiexample.com/api/v1/create";// "https://jsonplaceholder.typicode.com/todos/1";// https://34.212.65.116:8080/v1/tests
		String status = "TERMINATED";
		int limit = 50;
		int offset = 0;
		URLConnectionClientHandler cc = new URLConnectionClientHandler(new ConnectionFactory("proxy.tcs.com", 8080));
		Client client = new Client(cc);
		client.setConnectTimeout(2000000);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("status", status);
		queryParams.add("limit", String.valueOf(limit));
		queryParams.add("offset", String.valueOf(offset));

		WebResource resource = client.resource(uri);
		ClientResponse response = null;
		response = resource.queryParams(queryParams).header("Accept", "application/json")
				.header("Content-Type", "application/json;charset=UTF-8")
				.header("accountToken", "DR5WqNqIZv4HmnIl8cVmo8pT").get(ClientResponse.class);//,"{\"name\":\"testfk\",\"salary\":\"123\",\"age\":\"223\"}"
		String resp = response.getEntity(String.class);
		System.out.println(uri);
		System.out.println(resp);
	}
}
