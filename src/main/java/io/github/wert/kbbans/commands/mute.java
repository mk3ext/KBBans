package io.github.wert.kbbans.commands;

import io.github.wert.kbbans.databaseHandlers.createMute;
import io.github.wert.kbbans.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.wert.kbbans.databaseHandlers.playerManager.isMuted;
import static io.github.wert.kbbans.utils.MessageManager.chat;

public class mute implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;

        if(!p.hasPermission("kb.mute")) {
            MessageManager.playerError(p,"Insufficient permission(s)");
            return false;
        }

        if(args.length < 1) {
            MessageManager.playerInfo(p,"Usage: &c/mute <player> <time> <reason>");
            MessageManager.playerInfo(p,"Example: &c/mute player 1d <reason>");
            return false;
        }

        Player t = Bukkit.getPlayer(args[0]);
        if(t == null) {
            MessageManager.playerInfo(p,"Invalid player");
            return false;
        }

        if(isMuted(t.getUniqueId().toString())) {
            MessageManager.playerInfo(p,"Player is already muted");
            return false;
        }


        StringBuilder reason = new StringBuilder();
        if(args.length == 1) {
            reason.append("Not specified");
        } else {
            for (int i = 1; i < args.length; i++) {
                reason.append(args[i]).append(" ");
            }
        }
        String r2 = String.valueOf(reason).trim();
        String time = args.length == 1 ? "perm" : args[1];

        new createMute(t.getUniqueId().toString(),time,p.getName(),r2,t.getName());


        return false;
    }

}
