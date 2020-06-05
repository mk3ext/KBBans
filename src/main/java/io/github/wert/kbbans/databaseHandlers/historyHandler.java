package io.github.wert.kbbans.databaseHandlers;

import io.github.wert.kbbans.KBBans;
import org.bson.Document;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class historyHandler {

    private final KBBans instance = KBBans.getInstance();
    private MongoConnect mongoConnect = instance.mongoConnect;


    public historyHandler(Document doc,String type,String executor) {


        if(type.equals("ban")) {
            doc.append("removedBy", executor);
            String uuid = doc.getString("uuid");
            mongoConnect.getActive().deleteOne(and(eq("uuid", uuid),eq("type",type)));
        }
        mongoConnect.getHistory().insertOne(doc);


    }
}
