package io.github.wert.kbbans.listeners;

import io.github.wert.kbbans.KBBans;
import io.github.wert.kbbans.databaseHandlers.createPlayer;
import io.github.wert.kbbans.databaseHandlers.playerManager;
import io.github.wert.kbbans.utils.MessageManager;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static io.github.wert.kbbans.databaseHandlers.playerManager.unBan;
import static io.github.wert.kbbans.utils.MessageManager.chat;
import static io.github.wert.kbbans.utils.timeUtils.durationLeft;

public class playerJoin implements Listener {

    private final KBBans instance = KBBans.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        String puuid = p.getUniqueId().toString();

        if (instance.mongoConnect.getPlayers().find(new Document("uuid", puuid)).first() == null) {
            instance.playerManagerHashMap.put(p.getUniqueId(), new createPlayer(puuid,p.getAddress().getAddress().toString().replace("/","")));
        }else{
            if(!playerManager.isBanned(puuid)) return;

            Document ban = playerManager.getBanInfo(puuid);

            String expires = ban.getString("expires");
            String left = expires.equals("never") ? "permanently" : durationLeft(expires);
            String message = instance.getMessagesConfig().getString("ban.banned_message");

            if(left == null) {unBan(ban,puuid,"expired");return;};

            String msg = MessageManager.formatBanMsgDoc(message,ban,left);

            if(instance.getConfig().getBoolean("banned_player_join")) {Bukkit.broadcast(instance.getMessagesConfig().getString("ban.banned_player_join").replaceAll("\\{username\\}}",p.getName()),"kb.notify.bannedJoin");}

            p.kickPlayer(chat(msg));


        }
    }

}
