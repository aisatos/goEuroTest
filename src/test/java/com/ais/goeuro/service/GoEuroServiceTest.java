package com.ais.goeuro.service;

import static org.junit.Assert.*;

import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GoEuroServiceTest {
	private GoEuroService goEuroService;

	@Before
	public void before(){
		LocationFetchService locationFetchService = new LocationFetchHTTPClient(new DefaultHttpClient());
		
		LocationFileWritterImpl locationFileWritterImpl = new LocationFileWritterImpl(new FileWriteWrapper("GoEuroLocations.csv", "UTF-8"));

		goEuroService = new GoEuroService(locationFetchService, locationFileWritterImpl);
	}

	@Test
	public void testEmptyArgument() {		
		try {
			goEuroService.getLocationsAndWriteToFile("");
			fail();
		} catch (RuntimeException e) {
			assertNotNull(e.getMessage());
		}
		
	}
	
	@Test
	public void testNumberArgument() {
		
		try {
			goEuroService.getLocationsAndWriteToFile("29064");
			fail();
		} catch (RuntimeException e) {
			assertNotNull(e.getMessage());
		}
		
	}
	
	@Test
	public void testNullArgument() {
		try {
			goEuroService.getLocationsAndWriteToFile(null);
			fail();
		} catch (RuntimeException e) {
			assertNotNull(e.getMessage());
		}
	}

	
	@Test

	public void testImputLengthArgument() {
		String returnValue = goEuroService.getLocationsAndWriteToFile("Very long argument.Very long argument.Very long argument.Very long argument.Very long argument.Very long argument.Very long argument.Very long argument.Very long argument.Very long argument.Very long argument.Very long argument.Very long argument.Very long argument.Very long argument.Very long argument.Very long argument.");
		assertEquals("Success", returnValue);
	}
	
	@Test
	public void testSpaceArgument() {

		String returnValue = goEuroService.getLocationsAndWriteToFile("ber lin");
		assertEquals("Success", returnValue);
	}

	@Test
	public void testOkArgument() {

		String returnValue = goEuroService.getLocationsAndWriteToFile("ber");
		assertEquals("Success", returnValue);
	}
	
	
}
