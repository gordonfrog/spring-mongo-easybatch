package com.chetan.nosql.bulk.batch;

import java.io.File;

import org.easybatch.core.filter.HeaderRecordFilter;
import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.extensions.mongodb.MongoDBRecordWriter;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;

import com.chetan.nosql.bulk.product.ProductItemProcessor;
import com.chetan.nosql.bulk.product.ProductItems;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class SampleMain {

	public static void main(String[] args) {
		File csvfile = new File("SampleCSVFile.csv");
		
		Mongo mongo = new Mongo("localhost",27017);
		DB db = mongo.getDB("bulk");
		
		DBCollection dbColl = db.getCollection("bulkcsv");
		
		DelimitedRecordMapper<ProductItems> delimitedRecordMapper = new DelimitedRecordMapper<ProductItems>(ProductItems.class,"description","owner","count","value","tax","qty","region","type","probability");
		//delimitedRecordMapper.setQualifier("\"");
		Job job = new JobBuilder().aNewJob()
				.reader(new FlatFileRecordReader(csvfile))
				.filter(new HeaderRecordFilter())
				.mapper(delimitedRecordMapper)
				.processor(new ProductItemProcessor())
				.writer(new MongoDBRecordWriter(dbColl))
				.build();
		
		JobExecutor jobExecutor = new JobExecutor();
		JobReport result = jobExecutor.execute(job);
		System.out.println(" result is "+result);
		mongo.close();
		jobExecutor.shutdown();
		
	}

}
