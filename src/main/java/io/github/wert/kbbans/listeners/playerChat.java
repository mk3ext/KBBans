package io.github.wert.kbbans.listeners;

import io.github.wert.kbbans.KBBans;
import io.github.wert.kbbans.utils.MessageManager;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static io.github.wert.kbbans.databaseHandlers.playerManager.getMuteInfo;
import static io.github.wert.kbbans.databaseHandlers.playerManager.isMuted;
import static io.github.wert.kbbans.utils.timeUtils.durationLeft;

public class playerChat implements Listener {

    private final KBBans instance = KBBans.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTalk(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();
        String puuid = p.getUniqueId().toString();

        if(!isMuted(puuid)) {return;};
        e.setCancelled(true);

        Document mute = getMuteInfo(puuid);
        String expires = mute.getString("expires");
        String left = expires.equals("never") ? "permanently" : durationLeft(expires);
        String msg = instance.getMessagesConfig().getString("mute.try_to_talk");
        msg = msg.replaceAll("\\{durationLeft}", left);

        MessageManager.playerError(p,msg);

    }


}
