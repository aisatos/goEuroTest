package com.ais.goeuro.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public interface LocationFetchService {

	public String getLocationDetails(String string) throws ClientProtocolException, IOException;
	
}
