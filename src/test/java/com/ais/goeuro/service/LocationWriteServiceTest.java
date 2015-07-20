package com.ais.goeuro.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;


public class LocationWriteServiceTest {
	
private LocationFileWritterImpl locationFileWritterImpl;
	
	@Before
	public void before() throws Exception{
		locationFileWritterImpl = new LocationFileWritterImpl(new FileWriteWrapper("GoEuroLocations.csv", "UTF-8"));
	}

	@Test
	public void test() {
		String locationsDetail = "[{\"_id\":376217,\"key\":null,\"name\":\"Berlin\",\"fullName\":\"Berlin, Germany\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":52.52437,\"longitude\":13.41053},\"locationId\":8384,\"inEurope\":true,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null},{\"_id\":393496,\"key\":null,\"name\":\"Bergen\",\"fullName\":\"Bergen, Norway\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Norway\",\"geo_position\":{\"latitude\":60.39292907714844,\"longitude\":5.324578762054443},\"locationId\":25708,\"inEurope\":true,\"countryCode\":\"NO\",\"coreCountry\":false,\"distance\":null},{\"_id\":373033,\"key\":null,\"name\":\"Berne\",\"fullName\":\"Berne, Switzerland\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Switzerland\",\"geo_position\":{\"latitude\":46.94809,\"longitude\":7.44744},\"locationId\":5194,\"inEurope\":true,\"countryCode\":\"CH\",\"coreCountry\":true,\"distance\":null},{\"_id\":460643,\"key\":null,\"name\":\"Berdyans'k\",\"fullName\":\"Berdyans'k, Ukraine\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Ukraine\",\"geo_position\":{\"latitude\":46.7663,\"longitude\":36.79882},\"locationId\":161793,\"inEurope\":true,\"countryCode\":\"UA\",\"coreCountry\":false,\"distance\":null},{\"_id\":314826,\"key\":null,\"name\":\"Berlin Tegel\",\"fullName\":\"Berlin Tegel (TXL), Germany\",\"iata_airport_code\":\"TXL\",\"type\":\"airport\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":52.5548,\"longitude\":13.28903},\"locationId\":null,\"inEurope\":true,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null},{\"_id\":314827,\"key\":null,\"name\":\"Berlin Schönefeld\",\"fullName\":\"Berlin Schönefeld (SXF), Germany\",\"iata_airport_code\":\"SXF\",\"type\":\"airport\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":52.3887261,\"longitude\":13.5180874},\"locationId\":null,\"inEurope\":true,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null}]";
		
		locationFileWritterImpl.write( locationsDetail);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmpty() {
		String locationsDetail = "";
		
		locationFileWritterImpl.write( locationsDetail);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNull() {
		
		locationFileWritterImpl.write( null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testInvalidJson() {
		String locationsDetail = "Not-a-valid-Json-format";
		locationFileWritterImpl.write( locationsDetail);
	}
	
	@Test
	public void testEmptyJsonArray() {
		String locationsDetail = "[]";
		locationFileWritterImpl.write( locationsDetail);
	}
	
	@Test(expected= IllegalStateException.class)
	public void testEmptyJsonObject() {
		String locationsDetail = "{}";
		locationFileWritterImpl.write( locationsDetail);
	}
	
	@Test
	public void testWriteWrapperIsCalled() {
		String locationsDetail = "[]";
		FileWriteWrapper writerWrapper = mock(FileWriteWrapper.class);

		locationFileWritterImpl = new LocationFileWritterImpl(writerWrapper);
		
		locationFileWritterImpl.write( locationsDetail);
		
		verify(writerWrapper).write(new String[]{"_type,_id,name,type,latitude,longitude"});
	}
	
	@Test(expected=IllegalStateException.class)
	public void testWriteWrapperThrowsExc() {
		String locationsDetail = "[]";
		FileWriteWrapper writerWrapper = mock(FileWriteWrapper.class);
		
		// Stubbed call to write with Throw exception
		doThrow(new IllegalStateException()).when(writerWrapper).write(new String[]{"_type,_id,name,type,latitude,longitude"});

		locationFileWritterImpl = new LocationFileWritterImpl(writerWrapper);
		
		locationFileWritterImpl.write( locationsDetail);
	}

}
