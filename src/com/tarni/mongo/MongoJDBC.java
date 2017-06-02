package com.tarni.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class MongoJDBC {

	DB db = null;

	public void connectToDB() {
		try {
			// To connect to mongodb server
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			// connect to your databases
			db = mongoClient.getDB("tarni");
			System.out.println("Connect to database successfully");

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public DBCollection createCollection() {
		DBCollection coll = db.createCollection("test2", null);
		System.out.println("Collection created successfully");

		DBCollection coll2 = db.getCollection("test2");
		System.out.println("Collection test2 selected successfully");
		return coll2;
	}

	public void addDocumentToCollection(DBCollection coll2) {
		DBObject doc = new BasicDBObject("title", "MongoDBNew").append("description", "database").append("likes", 10)
				.append("url", "http://www.tutorialspoint.com/mongodb/").append("by", "Tarni");

		coll2.insert(doc);
		System.out.println("Document inserted successfully");
	}

	public void showDocuments(DBCollection coll) {
		DBCursor cursor = coll.find();
		int i = 1;

		while (cursor.hasNext()) {
			System.out.println("Inserted Document: " + i);
			System.out.println(cursor.next());
			i++;
		}
	}

	public void find(DBCollection coll) {
		BasicDBObject query = new BasicDBObject();
		query.put("title", "MongoDB");
		DBCursor cursor = coll.find(query);
		int i = 1;
		while (cursor.hasNext()) {
			System.out.println("Document found: " + i);
			System.out.println(cursor.next());
			i++;
		}
	}

	public void delete(DBCollection coll) {
		BasicDBObject query = new BasicDBObject();
		query.put("title", "MongoDBTarni");
		WriteResult result = coll.remove(query);
		System.out.println("Deletion Result "+result.toString());
		DBCursor cursor = coll.find();
		int i = 1;
		while (cursor.hasNext()) {
			System.out.println("Document left: " + i);
			System.out.println(cursor.next());
			i++;
		}
	}

	public void updateDocument(DBCollection coll) {
		BasicDBObject query = new BasicDBObject();
		query.put("title", "MongoDB");
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("title", "MongoDBTarni");
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);
		WriteResult result = coll.update(query, updateObj);
		System.out.println("Update Result "+result.toString());
	}

	public static void main(String args[]) {
		MongoJDBC mongoJDBCConnector = new MongoJDBC();
		mongoJDBCConnector.connectToDB();
		DBCollection coll = mongoJDBCConnector.createCollection();
		mongoJDBCConnector.addDocumentToCollection(coll);
		mongoJDBCConnector.find(coll);
		mongoJDBCConnector.updateDocument(coll);
		mongoJDBCConnector.delete(coll);
	}
}
