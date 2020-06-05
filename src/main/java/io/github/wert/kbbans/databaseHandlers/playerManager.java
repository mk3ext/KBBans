package io.github.wert.kbbans.databaseHandlers;

import io.github.wert.kbbans.KBBans;
import org.bson.Document;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class playerManager {

    public static Boolean isBanned(String uuid) {
        try {
            Document data = (Document) KBBans.getInstance().mongoConnect.getPlayers().find(eq("uuid", uuid)).first();
            return data.getBoolean("banned");
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static Boolean isMuted(String uuid) {
        Document data = (Document) KBBans.getInstance().mongoConnect.getPlayers().find(eq("uuid", uuid)).first();
        return data.getBoolean("muted");
    }

    public static Document getMuteInfo(String uuid) {
        return (Document) KBBans.getInstance().mongoConnect.getActive().find(and(eq("uuid", uuid),eq("type","mute"))).first();

    }

    public static Document getBanInfo(String uuid) {
        return (Document) KBBans.getInstance().mongoConnect.getActive().find(and(eq("uuid", uuid),eq("type","ban"))).first();

    }

    public static void unBan(Document doc, String uuid, String executor) {
        KBBans.getInstance().mongoConnect.setPlayerDoc(false,"banned",uuid);

        new historyHandler(doc,"ban",executor);


    };

}
