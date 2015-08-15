package com.nesterenya.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.nesterenya.authorization.Account;
import com.nesterenya.modal.Wish;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nesterenya.modal.Ad;

@Component
public class RentService {

	final int DEFAULT_CARD_OF_PAGE = 5;
	final String DEFAULT_SORT_CRITERIA = "-date";

	// TODO добавить DAO
	private Logger log = LoggerFactory.getLogger(RentService.class);

	@Autowired
	Datastore storage;

	private List<Ad> getAllAds() {
		return storage.find(Ad.class).asList();
	}

	public List<Ad> getAll() {
		List<Ad> ads = getAllAds();
		return ads;
	}


	public int getCountPages(int cardOnPage) {
		if(cardOnPage<=0)
			cardOnPage = DEFAULT_CARD_OF_PAGE;
		double fullSize = storage.getCount(Ad.class);;
		return (int) Math.ceil(fullSize/cardOnPage);
	}
	
	public Ad get(String id) {
		ObjectId key = new ObjectId(id);
		Ad one = storage.get(Ad.class, key);
		return one;
	}

	private boolean checkPossibleSortCriteria(String criteria) {
		if(criteria!=null) {
			String[] validCriteriaList = {"date", "-date", "roomCount", "-roomCount", "cost", "-cost"};
			for(String c : validCriteriaList) {
				if(c.equals(criteria)) {
					return true;
				}
			}
		}
		return false;
	}

	private String normalizationCriteria(String criteria) {
		String sortC = (checkPossibleSortCriteria(criteria))?criteria:DEFAULT_SORT_CRITERIA;

		if(sortC.contains("cost")) {
			sortC = sortC.replace("cost", "costValue");
		}

		return sortC;
	}
	
	public List<Ad> getPage(int pageNumber, int cardOnPage, String sortCriteria) {
		if(cardOnPage<=0)
			cardOnPage = DEFAULT_CARD_OF_PAGE;

		String sortC = normalizationCriteria(sortCriteria);

		int first = (pageNumber-1)*cardOnPage;
		if(first<0) { first = 0; }

		Query<Ad> query = storage.createQuery(Ad.class).order(sortC).offset(first).limit(cardOnPage);
		return query.asList();
	}

	public void addView(String id) {
		ObjectId oid = new ObjectId(id);
		UpdateOperations<Ad> ops = storage.createUpdateOperations(Ad.class).inc("views",1);
		Query<Ad> updateQuery = storage.createQuery(Ad.class).field("_id").equal(oid);
		storage.update(updateQuery, ops);
	}

	public Ad add(Ad ad) {

		if(ad.getImages()==null)
			ad.setImages(new ArrayList<String>());
		storage.save(ad);
		return ad;
	}
}