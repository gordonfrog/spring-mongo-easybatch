package org.davromalc.tutorial;

import org.davromalc.tutorial.model.Customer;
import org.davromalc.tutorial.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
    public CommandLineRunner loadData(CustomerRepository repository) {
        return (args) -> {
        	Customer will = new Customer("will", "gordon", "dev", "santa fe, nm", "us", "2149064371", "freeman");
        	
            
        	repository.deleteAll();
        	
            repository.save(will);
            
        };
    }
	

}
