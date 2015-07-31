package com.nesterenya.services;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.nesterenya.config.MongoConnectionConfig;
import com.nesterenya.modal.Wish;

@Component
public class WishService {
	
	MongoClientURI uri;
	MongoClient mongoClient;
	Datastore storage;
	
	@Autowired
	public WishService(MongoConnectionConfig config) {
		uri = new MongoClientURI(config.getConnectionURI());
		mongoClient = new MongoClient(uri);
		//commentsDB = mongoClient.getDatabase(config.getDataBase());
		Morphia morphia = new Morphia();
		
		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		morphia.mapPackage("org.mongodb.morphia.example");
		
		storage = morphia.createDatastore(mongoClient, config.getDataBase());
		storage.ensureIndexes();
	}
	
	public Wish add(Wish wish) {
		storage.save(wish);
		
		// return with id
		return wish;
	}
	
	public List<Wish> getAll() {
		Query<Wish> query = storage.createQuery(Wish.class).order(("-date"));
		List<Wish> wishes = query.asList();
		return wishes;
	}
	
	public Wish get(String id) {
		ObjectId oid = new ObjectId(id);
		Query<Wish> find = storage.createQuery(Wish.class).field("_id").equal(oid);
		return find.get();
	}

	public void like(String id) {
		ObjectId oid = new ObjectId(id);
		UpdateOperations<Wish> ops = storage.createUpdateOperations(Wish.class).inc("likes",1);
		Query<Wish> updateQuery = storage.createQuery(Wish.class).field("_id").equal(oid);
		storage.update(updateQuery, ops);
	}
	
	public void dislike(String id) {
		ObjectId oid = new ObjectId(id);
		UpdateOperations<Wish> ops = storage.createUpdateOperations(Wish.class).inc("dislikes",1);
		Query<Wish> updateQuery = storage.createQuery(Wish.class).field("_id").equal(oid);
		storage.update(updateQuery, ops);
	}
}
