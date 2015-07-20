package com.ais.goeuro.service;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationFetchHTTPClient implements LocationFetchService {

	private static final String GO_EURO_API_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";
	private HttpClient httpClient;
	
	@Autowired
	public LocationFetchHTTPClient(HttpClient client){
		this.httpClient = client;
	}
	
	/**
	 * Retrieves a String: a Json object array from the GoEuro service
	 * @param string the short name of the location the UI is looking the details for
	 * @throws ClientProtocolException when there's a protocol error
	 * @throws IOException on connection error
	 * @throws IllegalArgumentException when string is null or empty.
	 */
	public String getLocationDetails(String string) throws ClientProtocolException, IOException {
		if (string == null || string.length() < 1)
			throw new IllegalArgumentException("Argument string should be non-empty.");
		HttpResponse response = callRemoteApi(string);
		
		return getContent(response);
	}

	private HttpResponse callRemoteApi(String string) throws IOException, ClientProtocolException {
		String baseUrl = GO_EURO_API_URL;
		HttpGet request = new HttpGet(baseUrl + string.replaceAll(" ", "%20"));
		return  this.httpClient.execute(request);
	}

	private String getContent(HttpResponse response) throws IOException {
		return IOUtils.toString(response.getEntity().getContent(), "UTF-8");
	}
}
