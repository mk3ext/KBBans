package io.github.wert.kbbans.utils;

import io.github.wert.kbbans.KBBans;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageManager {

    public static String prefix = KBBans.getInstance().getConfig().getString("message.prefix");

    public static String chat(String s){
        return ChatColor.translateAlternateColorCodes('&',s);
    }

    public static void consoleInfo(String s) {
        Bukkit.getConsoleSender().sendMessage(chat(prefix + "&a" + s));
    }

    public static void consoleError(String s) {
        Bukkit.getConsoleSender().sendMessage(chat(prefix + "&c" + s));
    }

    public static void playerInfo(Player player, String s) {
        player.sendMessage(chat(prefix + "&7" + s));
    }

    public static void playerError(Player player, String s) {
        player.sendMessage(chat(prefix + "&c" + s));
    }

    public static void broadcast(String messagePath, String target, String by, String reason, String perm) {
        String msg = KBBans.getInstance().getMessagesConfig().getString(messagePath);
        assert msg != null;
        msg = msg.replaceAll("\\{executor}",by);
        msg = msg.replaceAll("\\{target}",target);
        msg = msg.replaceAll("\\{reason}",reason);

        Bukkit.broadcast(chat(msg),perm);

    }

    public static String formatBanMsgDoc(String message, Document doc, String left) {

        String reason = doc.getString("reason");
        String by = doc.getString("by");
        String expires = doc.getString("expires");

        message = message.replaceAll("\\{reason}", reason);
        message = message.replaceAll("\\{durationLeft}", left);
        message = message.replaceAll("\\{expires}", expires);
        message = message.replaceAll("\\{executor}", by);

        return message;
    }

    public static String formatMsg(String message, String reason, String by, String left) {
        message = message.replaceAll("\\{reason}", reason);
        message = message.replaceAll("\\{durationLeft}", left);
        message = message.replaceAll("\\{executor}", by);

        return message;
    };

}
