package com.chetan.nosql.bulk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@PropertySource({"classpath:local-application.properties"})
public class LocalSpringMongoConfig extends AbstractMongoConfiguration {

	@Value("${spring.data.mongodb.database}")
	private String dbName;
	
	@Value("${spring.data.mongodb.host}")
	private String mongoHost;
	
	@Value("${spring.data.mongodb.port}")
	private int mongoPort;
	
	
	@Override
	protected String getDatabaseName() {
		// TODO Auto-generated method stub
		return dbName;
	}

	@Override
	public Mongo mongo() throws Exception {
		// TODO Auto-generated method stub
		//return new MongoClient(mongoHost, mongoPort);
		return null;
	}

}
