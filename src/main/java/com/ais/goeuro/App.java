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
		// Spring for Dependency Injection
		SpringApplication app = new SpringApplication(App.class);
        app.setShowBanner(false);
        ConfigurableApplicationContext ctx = app.run(args);
		
	   	// check that it has arguments, one argument only
    	if (args.length != 1 )
    		System.out.println("Please use one parameter for the location reference.");
    			
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
