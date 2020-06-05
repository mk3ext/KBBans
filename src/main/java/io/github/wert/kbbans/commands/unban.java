package io.github.wert.kbbans.commands;

import io.github.wert.kbbans.KBBans;
import io.github.wert.kbbans.databaseHandlers.playerManager;
import io.github.wert.kbbans.utils.MessageManager;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.wert.kbbans.utils.MessageManager.broadcast;

public class unban implements CommandExecutor {

    private final KBBans instance = KBBans.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        String tarUUID = target.getUniqueId().toString();
        if(!(sender instanceof Player)) {
            if(args.length != 1) {
                MessageManager.consoleInfo("Usage: /unban <player> ");
                return false;
            }
            if(!(playerManager.isBanned(tarUUID))) {
                MessageManager.consoleInfo("Player is not banned");
                return false;
            };

            Document ban = playerManager.getBanInfo(tarUUID);
            playerManager.unBan(ban,tarUUID,"Console");

            if(instance.getConfig().getBoolean("notify_unban")) {
                broadcast("ban.player_unbanned",target.getName(),"Console","","kb.notify.unban");
            }
            return false;
        }

        Player p = (Player) sender;

        if(!(playerManager.isBanned(tarUUID))) {
            MessageManager.playerInfo(p,"Player is not banned");
            return false;
        };

        if(!p.hasPermission("kb.unban")) {
            MessageManager.playerError(p,"Insufficient permission(s)");
            return false;
        }

        if(args.length != 1) {
            MessageManager.playerInfo(p,"Usage: /unban <player> ");
            return false;
        }

        Document ban = playerManager.getBanInfo(tarUUID);
        playerManager.unBan(ban,tarUUID,p.getName());

        if(instance.getConfig().getBoolean("notify.notify_unban")) {
            broadcast("ban.player_unbanned",target.getName(),p.getName(),"","kb.notify.unban");
        }


        return false;
    }

}
