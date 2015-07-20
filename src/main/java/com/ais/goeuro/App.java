package com.ais.goeuro;

import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.ais.goeuro.service.FileWriteWrapper;
import com.ais.goeuro.service.GoEuroService;

/**
 * GoEuroTest Console version
 *
 */
@SpringBootApplication
public class App 
{
	@Bean
	public FileWriteWrapper getBWrapper(){
		FileWriteWrapper writerWrapper = new FileWriteWrapper("GoEuroLocations.csv", "UTF-8");
		return writerWrapper;
	}
	
	@Bean
	public DefaultHttpClient getClient(){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		return httpClient;
	}
	
	public static void main( String[] args )
    {
		ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);
		
	   	// check that it has arguments, one argument only
    	if (args.length != 1 )
    		System.out.println("Wrong number of parameters");
    			
    	GoEuroService goEuroService = (GoEuroService) ctx.getBean("goEuroService");
    	
    	try{
    		goEuroService.getLocationsAndWriteToFile(args[0]);
    	}
    	catch (Exception e){
    		System.out.println(e.getMessage());
    	}
    	
    	System.out.println("Service executed successfully ");
    }
}
