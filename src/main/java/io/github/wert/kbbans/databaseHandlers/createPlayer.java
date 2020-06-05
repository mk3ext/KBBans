package io.github.wert.kbbans.databaseHandlers;

import io.github.wert.kbbans.KBBans;
import org.bson.Document;


public class createPlayer {

    private String uuid;
    private String ip;
    private boolean banned;

    private final KBBans instance = KBBans.getInstance();
    private MongoConnect mongoConnect = instance.mongoConnect;


    public createPlayer(String uuid, String ip) {
        this.uuid = uuid;
        this.ip = ip;

        Document document = new Document("uuid",uuid);
        if(mongoConnect.getPlayers().find(document).first() == null){
            document.append("ip",ip);
            document.append("banned",false);
            document.append("muted",false);
            mongoConnect.getPlayers().insertOne(document);
        }
    }


}
