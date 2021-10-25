package co.edu.escuelaing.log.repository;

import co.edu.escuelaing.log.model.LogData;
import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogRepository {

    private static final ConnectionString connectionString = new ConnectionString("mongodb://"
               + System.getenv("MONGODB_USERNAME") + ":" + System.getenv("MONGODB_PASSWORD") + "@"
               + System.getenv("MONGODB_HOST") + ":" + System.getenv("MONGODB_PORT"));

    public static MongoClient mongoClient;
    private static MongoClient getConnection() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(connectionString);
        }
        return mongoClient;
    }

    public static List<LogData> saveAndGetLast(LogData logData) {

        try {
            MongoCollection collection = getConnection().getDatabase(System.getenv("MONGODB_DB")).getCollection("logs");
            collection.insertOne(Document.parse(new Gson().toJson(logData)));
            FindIterable resultFind = collection.find()
                    .sort(BsonDocument.parse("{createdAt:-1}"))
                    .limit(10);
            System.out.println("--SaveAndGetLast() --Connected successfully to server: " + connectionString);
            List<LogData> mongoResponse = new ArrayList<>();
            for (Object o : resultFind) {
                mongoResponse.add(new Gson().fromJson(((Document) o).toJson(), LogData.class));
            }

            return mongoResponse;
        } catch (Exception me) {
            System.err.println("An error occurred while attempting to run a command in Mongo: " + me);
            return Collections.emptyList();
        }
    }

    public static List<LogData> getLast() {

        try {
            MongoCollection collection = getConnection().getDatabase(System.getenv("MONGODB_DB")).getCollection("logs");
            FindIterable resultFind = collection.find()
                    .sort(BsonDocument.parse("{createdAt:-1}"))
                    .limit(10);
            System.out.println("--GetLast() --Connected successfully to server: " + connectionString);
            List<LogData> mongoResponse = new ArrayList<>();
            for (Object o : resultFind) {
                mongoResponse.add(new Gson().fromJson(((Document) o).toJson(), LogData.class));
            }

            return mongoResponse;
        } catch (Exception me) {
            System.err.println("An error occurred while attempting to run a command in Mongo: " + me);
            return Collections.emptyList();
        }
    }
}
