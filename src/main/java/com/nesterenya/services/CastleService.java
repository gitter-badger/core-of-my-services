package com.nesterenya.services;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.nesterenya.modal.Castle;

@Component

public class CastleService {

	// TODO MONGO ENABLE
	
	/*private MongoClient mongoClient;
	private Morphia morphia;
	*/
	/*{	
		mongoClient = new MongoClient();
		morphia = new Morphia();
		
		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		morphia.mapPackage("org.mongodb.morphia.example");
	}*/
		
	public void put(Castle castle) {
		/*final Datastore datastore = morphia.createDatastore(new MongoClient(), "journeydb");
		datastore.ensureIndexes();
		datastore.save(castle);*/
	}
	
	public List<Castle> getAll(){
		
		// create the Datastore connecting to the default port on the local host
		/*final Datastore datastore = morphia.createDatastore(new MongoClient(), "journeydb");
		datastore.ensureIndexes();
		
		final Query<Castle> query = datastore.createQuery(Castle.class);
		final List<Castle> castles = query.asList();
		return castles;
		*/
        return new ArrayList<Castle>();
    }
}