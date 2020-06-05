package io.github.wert.kbbans.databaseHandlers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.wert.kbbans.KBBans;
import io.github.wert.kbbans.utils.MessageManager;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoConnect {
    private MongoDatabase database;
    private MongoCollection players;
    private MongoCollection active;
    private MongoCollection history;
    private final KBBans instance = KBBans.getInstance();

    public void connect(){
        MongoClient client = MongoClients.create(instance.getConfig().getString("mongo.uri"));
        setDatabase(client.getDatabase("kbbans"));
        setPlayers(database.getCollection("players"));
        setActive(database.getCollection("active"));
        setHistory(database.getCollection("history"));
        MessageManager.consoleInfo("Database connected");
    }

    public void setPlayerDoc(Object value, String identifier, String uuid) {
        Document document = new Document("uuid", uuid);
        Bson newValue = new Document(identifier, value);
        Bson updateOp = new Document("$set", newValue);
        players.updateOne(document, updateOp);
    }

    public Object getPlayerBanDoc(String identifier, String uuid) {
        Document document = new Document("uuid",uuid);
        if(active.find(document).first() != null) {
            Document found = (Document) active.find().first();
            return found.get(identifier);
        } else {
            MessageManager.consoleError("Document is null for UUID: "+uuid);
            return null;
        }
    };

    public Object getPlayerDoc(String identifier, String uuid) {
        Document document = new Document("uuid",uuid);
        if(players.find(document).first() != null) {
            Document found = (Document) players.find().first();
            return found.get(identifier);
        } else {
            MessageManager.consoleError("Document is null for UUID: "+uuid);
            return null;
        }
    };

    public MongoCollection getPlayers() {
        return players;
    }
    public MongoCollection getActive() { return active; }
    public MongoCollection getHistory() { return history; }

    public void setPlayers(MongoCollection players) {
        this.players = players;
    }
    public void setActive(MongoCollection active) { this.active = active; }
    public void setHistory(MongoCollection history) { this.history = history; }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }
}
