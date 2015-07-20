package com.ais.goeuro.service;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LocationFetchServiceTest {

	private LocationFetchService locationFetchService;

	@Before
	public void before() {
		locationFetchService = new LocationFetchHTTPClient(new DefaultHttpClient());
	}
	
	@Test
	public void testHappy(){
		String locationsDetail = null;
		
		try {
			locationsDetail = locationFetchService.getLocationDetails("ber");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		assertNotNull(locationsDetail);
	}


	
	@Test
	public void testWrongLocale() {
		String locationsDetail = null;
		
		try {
			locationsDetail = locationFetchService.getLocationDetails("wrong or inexistent locale");
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		
		assertNotNull(locationsDetail);
	}
	

	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyLocale() {
		try {
			locationFetchService.getLocationDetails("");
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
		
	@Test(expected=IOException.class)
	public void testConnectErrorWithMock() throws ClientProtocolException, IOException {
		locationFetchService = new LocationFetchHTTPClient(getMock());
		
		locationFetchService.getLocationDetails("anything");
	}
	
	private HttpClient getMock() throws ClientProtocolException, IOException{
		HttpClient httpClient = mock(HttpClient.class);
		when(httpClient.execute((HttpUriRequest) anyObject())).thenThrow(new IOException());
		
		return httpClient;
	}
}
