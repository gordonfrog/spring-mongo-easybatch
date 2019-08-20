package com.chetan.nosql.bulk.product;

import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.record.Record;
import org.easybatch.extensions.mongodb.MongoDBRecord;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ProductItemProcessor implements RecordProcessor<Record<ProductItems>, Record<DBObject>> {

	public Record<DBObject> processRecord(Record<ProductItems> record) throws Exception {
		ProductItems productItems = record.getPayload();
		productItems.setOwner("chetan");
		
		DBObject dbObject = new BasicDBObject();
		dbObject.put("description", productItems.getDescription());
		dbObject.put("owner", productItems.getOwner());
		dbObject.put("count", productItems.getCount());
		dbObject.put("value", productItems.getValue());
		dbObject.put("tax", productItems.getTax());
		dbObject.put("qty", productItems.getQty());
		dbObject.put("region", productItems.getRegion());
		dbObject.put("type", productItems.getType());
		dbObject.put("probability", productItems.getProbability());
		return new MongoDBRecord(record.getHeader(), dbObject);
	}

}
