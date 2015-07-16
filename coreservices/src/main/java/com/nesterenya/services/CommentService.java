package com.nesterenya.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBList;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import com.nesterenya.modal.Castle;
import com.nesterenya.modal.Comment;

@Component
public class CommentService {
	final static MongoClient mongoClient = new MongoClient();
	MongoDatabase commentsDB = mongoClient.getDatabase("blogdb");

	public void push(String id, Comment comment) {
		MongoCollection<Document> commentCollection = commentsDB
				.getCollection("posts");
		
		//http://localhost:8080/api/blog/comments/add/55a6c0b8c9d11d263046182d
		Document document = new Document();
		document.put("user", comment.getUser());
		document.put("text", comment.getText());
		document.put("date", comment.getDate());
		//if (email != null && !email.equals("")) {
        //    comment.append("email", email);
        //}
		// WriteResult result =
		
       commentCollection.updateOne(new Document("_id", new ObjectId(id) ),
                new Document("$push", new Document("comments", document)), new UpdateOptions().upsert(false));
	}

	public String create() {
		MongoCollection<Document> commentCollection = commentsDB
				.getCollection("posts");
		
		Document document = new Document();
		document.put("comments", new ArrayList<Document>());
		commentCollection.insertOne(document);
		
		String generatedId = document.getObjectId("_id").toHexString();
		
		return generatedId;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> get(String id) {
		MongoCollection<Document> commentCollection = commentsDB
				.getCollection("posts");
		List<Comment> comments = new ArrayList<Comment>();
		// 55a68f793066cfbef2fd729a
		Document query = new Document();
		query.put("_id", new ObjectId(id));

		Document document = commentCollection.find(query).first();

		if (document != null) {
			ArrayList<Document> list = (ArrayList<Document>) document
					.get("comments");

			for (int i = 0; i < list.size(); i++) {
				Document com = (Document) list.get(i);
				Comment comment = new Comment();
				comment.setUser(com.getString("user"));
				comment.setText(com.getString("text"));
				comment.setDate(com.getDate("date"));
				comments.add(comment);
			}
		}

		return comments;
	}
}
