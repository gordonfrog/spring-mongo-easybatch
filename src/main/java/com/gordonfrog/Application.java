package com.gordonfrog;

import java.io.File;

import org.easybatch.core.filter.HeaderRecordFilter;
import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.core.writer.FileRecordWriter;
import org.easybatch.extensions.mongodb.MongoDBRecordWriter;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.DelimitedRecordMarshaller;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.gordonfrog.batch.ProductItemProcessor;
import com.gordonfrog.batch.ProductItems;

import com.gordonfrog.repository.CustomerRepository;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

import common.GenericProcessor;
import common.Customer;

@EnableMongoRepositories
@SpringBootApplication
public class Application {
	
	@Value("classpath:./SampleCSVFile.csv")
	Resource resourceFile;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
    public CommandLineRunner loadData(CustomerRepository repository) {
        return (args) -> {
//        	Customer will = new Customer("will", "gordon", "dev", "santa fe, nm", "us", "2149064371", "freeman");
//        	
//            
//        	repository.deleteAll();
//        	
//            repository.save(will);
        	
//        	File csvfile = new ClassPathResource(
//        		      "./SampleCSVFile.csv").getFile();
//    		
    		Mongo mongo = new Mongo("localhost",27017);
    		DB db = mongo.getDB("bulk");
    		
    		DBCollection dbColl = db.getCollection("bulkcsv");
//    		
//    		DelimitedRecordMapper<ProductItems> delimitedRecordMapper = new DelimitedRecordMapper<ProductItems>(ProductItems.class,"description","owner","count","value","tax","qty","region","type","probability");
//    		//delimitedRecordMapper.setQualifier("\"");
        	
        	long startTime = System.currentTimeMillis();

            //File datasource = new File(getTempDir() + "customers_in.csv");
        	File datasource = new File(getTempDir() + "customers_in_1.txt");
            //File datasink = new File(getTempDir() + "customers_out.csv");
            String[] fields = {"id", "firstName", "lastName", "birthDate", "email", "phone", "street", "zipCode", "city", "country"};

            Job job = new JobBuilder()
                    .reader(new FlatFileRecordReader(datasource))
                    .mapper(new DelimitedRecordMapper<>(Customer.class, fields))
                    .processor(new GenericProcessor())
                    .marshaller(new DelimitedRecordMarshaller<>(Customer.class,fields))
                    //.writer(new FileRecordWriter(datasink))
                    .writer(new MongoDBRecordWriter(dbColl))
                    .batchSize(10)
                    .build();

            JobExecutor jobExecutor = new JobExecutor();
            JobReport result = jobExecutor.execute(job);
            long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println("Easy Batch took: " + (elapsedTime  / 1000) + "s");
            System.out.println(" result is "+result);
            
    		mongo.close();
    		jobExecutor.shutdown();

            

//    		Job job = new JobBuilder().aNewJob()
//    				.reader(new FlatFileRecordReader(csvfile))
//    				.filter(new HeaderRecordFilter())
//    				.mapper(delimitedRecordMapper)
//    				.processor(new ProductItemProcessor())
//    				.writer(new MongoDBRecordWriter(dbColl))
//    				.build();
//    		
//    		JobExecutor jobExecutor = new JobExecutor();
//    		JobReport result = jobExecutor.execute(job);
//    		System.out.println(" result is "+result);
//    		mongo.close();
//    		jobExecutor.shutdown();
            
        };
    }
	
	private static String getTempDir() {
        return System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");
    }
	

}
