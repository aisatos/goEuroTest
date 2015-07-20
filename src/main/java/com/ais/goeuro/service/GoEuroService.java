package com.ais.goeuro.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoEuroService {
	private LocationFetchService locationFetchService;
	private LocationFileWritterImpl locationFileWritterImpl;

	@Autowired
	public GoEuroService(LocationFetchService locationFetchService, LocationFileWritterImpl locationFileWritterImpl) {
		this.locationFetchService = locationFetchService;
		this.locationFileWritterImpl = locationFileWritterImpl;
	}
	
	/**
	 * Retrieves a Location array from the GoEuro service and outputs to a File
	 * 
	 * @param locationName the short name of the location the UI is looking the details for
	 * @throws IllegalArgumentException when string is null or empty.
	 * @throws RuntimeException on Connection error.
	 */
	public String getLocationsAndWriteToFile(String locationName) {
		validateInput(locationName);
		String locationsDetail = getLocationFromHttpClient(locationName);
		writeLocationsToFile(locationsDetail);
		return "Success";
	}

	private void writeLocationsToFile(String locationsDetail) {
		locationFileWritterImpl.write( locationsDetail);
	}

	private String getLocationFromHttpClient(String locationName) {
		String locationsDetail = null;
		try {
			locationsDetail = locationFetchService.getLocationDetails(locationName);
		} catch (Exception e) {
			throw new RuntimeException("Connection error: " + e.getMessage(), e);
		}
		return locationsDetail;
	}

	private void validateInput(String locationName) {
		if(locationName == null || locationName.isEmpty())
			throw new IllegalArgumentException("No location name."); 
		
		if(isNumeric(locationName))
			throw new IllegalArgumentException("Numbers are not location names.");
	}
	
	private static boolean isNumeric(String inputData) {
		  return inputData.matches("[-+]?\\d+(\\.\\d+)?");
		}
}
