package io.github.wert.kbbans.databaseHandlers;

import io.github.wert.kbbans.KBBans;
import io.github.wert.kbbans.utils.timeUtils;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static io.github.wert.kbbans.utils.MessageManager.broadcast;
import static java.lang.Integer.parseInt;


public class createBan {


    private final KBBans instance = KBBans.getInstance();
    private MongoConnect mongoConnect = instance.mongoConnect;


    public createBan(String uuid, String ip, String time, String by, String reason, String target) {

        mongoConnect.setPlayerDoc(true,"banned",uuid);
        Document document = new Document("uuid",uuid);
        if(mongoConnect.getActive().find(and(eq("uuid", uuid),eq("type","ban"))).first() == null) {

            String x = "";
            String cur = (LocalDateTime.now()).toString();
            if (time.contains("s")) {
                x = timeUtils.addHours(cur,parseInt(time.replace("s","")),"seconds");
            } else if(time.contains("m")) {
                x = timeUtils.addHours(cur,parseInt(time.replace("m","")),"minutes");
            } else if(time.contains("hr")) {
                x = timeUtils.addHours(cur,parseInt(time.replace("hr","")),"hours");
            } else if(time.contains("d")) {
                x = timeUtils.addHours(cur,parseInt(time.replace("d","")),"days");
            } else if(time.contains("perm")) {
                x = "never";
            }

            document.append("uuid",uuid);
            document.append("ip",ip);
            document.append("by",by);
            document.append("reason",reason);
            document.append("expires",x);
            document.append("type","ban");

            document.append("addedOn",cur);

            mongoConnect.getActive().insertOne(document);

            broadcast("ban.player_banned",target,by,reason,"kb.notify.ban");
        }
    }
}
