package io.github.wert.kbbans.commands;

import io.github.wert.kbbans.databaseHandlers.createKick;
import io.github.wert.kbbans.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.wert.kbbans.utils.MessageManager.chat;

public class kick implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if(!sender.hasPermission("kb.kick")) {
            if (sender instanceof Player) {
                MessageManager.playerError((Player) sender, "Insufficient permission(s)");
                return false;
            }
            return false;
        }

        if(args.length < 1) {
            if (sender instanceof Player) {
                MessageManager.playerInfo((Player) sender,"Usage: &c/kick <player>");
                return false;
            } else {
                MessageManager.consoleInfo("Usage: &c/kick <player>");
            }
        }

        Player t = Bukkit.getPlayer(args[0]);
        if(t == null) {sender.sendMessage(chat("&cInvalid player"));return false;}

        StringBuilder reason = new StringBuilder();
        if(args.length == 1) {
            reason.append("Not specified");
        } else {
            for (int i = 1; i < args.length; i++) {
                reason.append(args[i]).append(" ");
            }
        }
        String r2 = String.valueOf(reason).trim();

        t.kickPlayer(r2);
        new createKick(t.getUniqueId().toString(),t.getAddress().getAddress().toString(),(sender instanceof Player ? sender.getName() : "Console"),r2,t.getName());





        return false;
    }


}
