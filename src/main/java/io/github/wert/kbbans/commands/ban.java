package io.github.wert.kbbans.commands;

import io.github.wert.kbbans.KBBans;
import io.github.wert.kbbans.databaseHandlers.createBan;
import io.github.wert.kbbans.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.wert.kbbans.utils.MessageManager.formatMsg;

public class ban implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;

        if(!p.hasPermission("kb.ban")) {
            MessageManager.playerError(p,"Insufficient permission(s)");
            return false;
        }

        if(args.length < 2) {
            MessageManager.playerInfo(p,"Usage: /ban <player> <time> <reason>");
            MessageManager.playerInfo(p,"Example: /ban player 1d <reason>");
            return false;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        StringBuilder reason = new StringBuilder();
        if(args.length == 2) {
            reason.append("Not specified");
        } else {
            for (int i = 1; i < args.length; i++) {
                reason.append(args[i]).append(" ");
            }
        }
        String r2 = String.valueOf(reason).trim();

        String ip = "";
        if(target.isOnline()) {
            Player t = Bukkit.getPlayer(args[0]);
            ip = t.getAddress().getAddress().toString();
            String msg = KBBans.getInstance().getMessagesConfig().getString("ban.banned_message");
            t.kickPlayer(formatMsg(msg,r2,p.getName(),args[1]));
        };

        new createBan(target.getUniqueId().toString(),ip,args[1],p.getName(),r2,target.getName());


        return false;
    }

}
