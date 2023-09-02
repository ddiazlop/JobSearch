package javalinkedin;

import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App {

    public static void main(String[] args) {
        // Replace the placeholder with your MongoDB deployment's connection string
        String uri = System.getenv("MONGODB_URI");
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("JobSearch");
            MongoCollection<Document> collection = database.getCollection("myself");
            Document doc = collection.find().first();
            System.out.println("#######################\n");
            System.out.println(doc.toJson());
        }

    }

}
