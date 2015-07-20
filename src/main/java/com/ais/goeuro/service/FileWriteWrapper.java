package com.ais.goeuro.service;

import java.io.PrintWriter;

public class FileWriteWrapper {
	private String fileName;
	private String encoding;

	
	public FileWriteWrapper(String fileName, String encoding) {
		this.fileName = fileName;
		this.encoding = encoding;
	}

	public void write(String[] locations){
		
		try {
			PrintWriter writer = new PrintWriter(this.fileName, this.encoding);
			
			for (int i= 0; i < locations.length; i++){
				writer.println( locations[i]);
			}
			
			writer.close();
			
		} catch (Exception e) {
			throw new IllegalStateException("Unable to write to file: " + e.getMessage(), e);
		}
	}
}
