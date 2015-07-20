package com.ais.goeuro.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ais.goeuro.model.Location;

@Service
public class LocationFileWritterImpl implements LocationWriteService{
	private FileWriteWrapper writer;
	
	@Autowired
	public LocationFileWritterImpl(FileWriteWrapper fileWriteWrapper) {
		this.writer = fileWriteWrapper;
	}

	/**
	 * Writes string passed as parameter to File
	 * @param locationsJsonArray Array of locations in Json format
	 * @throws IllegalArgumentException when locationsJsonArray is null or empty.
	 * @throws IllegalStateException when parsing the Json Fails or is unable to write to File
	 */
	public String write(String locationsJsonArray) {
		if (locationsJsonArray == null || locationsJsonArray.length() == 0)
			throw new IllegalArgumentException("Empty location not accepted.");
		
		Location locations[] = parseLocations(locationsJsonArray);
		
		writeLocationsToCSV(locations);
		
		return "Success";
	}
	
	private Location[] parseLocations (String locationsDetail){
		ObjectMapper mapper = new ObjectMapper();
		Location locations[] = null;  
		
		try {
			locations = mapper.readValue(locationsDetail, Location[].class);
			
		} catch (Exception e) {
			throw new IllegalStateException("Error mapping Json to a Location");
	 
		}
		
		return locations;
	}
	
	private void writeLocationsToCSV(Location[] locations){
		
		String[] locationsToWrite = new String[locations.length + 1];
		
		locationsToWrite[0] = "_type,_id,name,type,latitude,longitude";
		
		for (int i= 1; i < locations.length; i++){
			locationsToWrite[i] = locations[i].getType() + "," + locations[i].get_id() 
						+ "," + locations[i].getName() + ","  + locations[i].getType() 
						+ "," + locations[i].getGeo_position().getLatitude()  
						+ "," + locations[i].getGeo_position().getLongitude();
			}
		this.writer.write(locationsToWrite);
	}
}
