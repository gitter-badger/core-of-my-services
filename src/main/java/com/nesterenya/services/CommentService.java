package com.nesterenya.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.nesterenya.modal.Comment;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentService {

	@Autowired
	MongoDatabase mongoDatabase;

	public void push(String id, Comment comment) {
		MongoCollection<Document> commentCollection = mongoDatabase
				.getCollection("posts");
		
		Document document = new Document();
		document.put("user", comment.getUser());
		document.put("text", comment.getText());
		document.put("date", comment.getDate());

       	commentCollection.updateOne(new Document("_id", new ObjectId(id) ),
                new Document("$push", new Document("comments", document)), new UpdateOptions().upsert(false));
	}

	public String create() {
		MongoCollection<Document> commentCollection = mongoDatabase
				.getCollection("posts");
		
		Document document = new Document();
		document.put("comments", new ArrayList<Document>());
		commentCollection.insertOne(document);
		
		String generatedId = document.getObjectId("_id").toHexString();
		
		return generatedId;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> get(String id) {
		MongoCollection<Document> commentCollection = mongoDatabase
				.getCollection("posts");
		List<Comment> comments = new ArrayList<Comment>();
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
