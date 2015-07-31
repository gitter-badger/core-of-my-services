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
import com.nesterenya.modal.Link;

@Component
public class LinkService {

	private Datastore storage;
	
	@Autowired
	public LinkService(MongoConnectionConfig config) {
		MongoClientURI  uri = new MongoClientURI(config.getConnectionURI());
		MongoClient mongoClient = new MongoClient(uri);
		Morphia morphia = new Morphia();
		
		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		morphia.mapPackage("org.mongodb.morphia.example");
		
		storage = morphia.createDatastore(mongoClient, config.getDataBase());
		storage.ensureIndexes();
	}
	
	public Link add(Link link) {
		storage.save(link);
		
		// Return with generated id
		return link;
	}
	
	public List<Link> getAll() {
		Query<Link> query = storage.createQuery(Link.class).order(("-date"));
		List<Link> links= query.asList();
		return links;
	}
	
	public void like(String id) {
		ObjectId oid = new ObjectId(id);
		UpdateOperations<Link> ops = storage.createUpdateOperations(Link.class).inc("likes",1);
		Query<Link> updateQuery = storage.createQuery(Link.class).field("_id").equal(oid);
		storage.update(updateQuery, ops);
	}
	
	public void dislike(String id) {
		ObjectId oid = new ObjectId(id);
		UpdateOperations<Link> ops = storage.createUpdateOperations(Link.class).inc("dislikes",1);
		Query<Link> updateQuery = storage.createQuery(Link.class).field("_id").equal(oid);
		storage.update(updateQuery, ops);
	}
}
