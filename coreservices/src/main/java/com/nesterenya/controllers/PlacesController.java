package com.nesterenya.controllers;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.EphemeralMappedField;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import com.nesterenya.modal.Castle;
import com.nesterenya.modal.Place;
import com.nesterenya.services.CastleService;


@RestController
@RequestMapping("/journey/places")
public class PlacesController {

	//@Autowired
	//private CastleRepository repository;
	//@Autowired
	//private CastleService service;
	static MongoClient mongoClient;
	static {
		
			mongoClient = new MongoClient();
	
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Place> getAll() {
		
		Place[] places = new Place[6];
		places[0] = new Place(1, "53.451281, 26.473303", "Мирский замок");
		places[1] = new Place(2, "53.222892, 26.691773", "Несвижский замок");
		places[2] = new Place(3, "52.083109, 23.658875", "Брестская крепость");
		places[3] = new Place(4, "54.251562, 26.020300", "Гольшанский замок");
		places[4] = new Place(5, "52.422152, 31.016734", "Дворец Румянцевых-Паскевичей");
		places[5] = new Place(6, "53.677282, 23.822971", "Старый замок Гродно");
		return Arrays.asList(places);
	}
	
	@RequestMapping(value = "/cast", method = RequestMethod.GET)
	public List<Castle> getCast() {
		
		
		
		//MongoDatabase db = mongoClient.getDatabase("journeydb");
		
		
		final Morphia morphia = new Morphia();

		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		morphia.mapPackage("org.mongodb.morphia.example");

		// create the Datastore connecting to the default port on the local host
		final Datastore datastore = morphia.createDatastore(new MongoClient(), "journeydb");
		datastore.ensureIndexes();

		//final Castle cs = new Castle("54 8888", "sdf");
		//datastore.save(cs);
		
		
		
		final Query<Castle> query = datastore.createQuery(Castle.class);
		final List<Castle> castles = query.asList();
		
		
		return castles;
	}
}
