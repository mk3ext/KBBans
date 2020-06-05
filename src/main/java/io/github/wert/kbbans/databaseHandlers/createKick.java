package io.github.wert.kbbans.databaseHandlers;

import io.github.wert.kbbans.KBBans;
import org.bson.Document;

import java.time.LocalDateTime;

import static io.github.wert.kbbans.utils.MessageManager.broadcast;

public class createKick {

    private final KBBans instance = KBBans.getInstance();
    private MongoConnect mongoConnect = instance.mongoConnect;


    public createKick(String uuid, String ip, String by, String reason, String target) {

        Document document = new Document("uuid",uuid);
            String cur = (LocalDateTime.now()).toString();

            document.append("uuid",uuid);
            document.append("ip",ip);
            document.append("by",by);
            document.append("reason",reason);
            document.append("type","kick");

            document.append("addedOn",cur);

            new historyHandler(document,"kick",by);

            broadcast("kick.player_kick",target,by,reason,"kb.notify.kick");
        }
}
