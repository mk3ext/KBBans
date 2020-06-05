package io.github.wert.kbbans;

import io.github.wert.kbbans.commands.ban;
import io.github.wert.kbbans.commands.kick;
import io.github.wert.kbbans.commands.mute;
import io.github.wert.kbbans.commands.unban;
import io.github.wert.kbbans.databaseHandlers.MongoConnect;
import io.github.wert.kbbans.databaseHandlers.createPlayer;
import io.github.wert.kbbans.listeners.playerChat;
import io.github.wert.kbbans.listeners.playerJoin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public final class KBBans extends JavaPlugin {

    private static KBBans instance;

    private final File messagesFile = new File(getDataFolder(), "messages.yml");
    private final FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);

    public MongoConnect mongoConnect;
    public HashMap<UUID, createPlayer> playerManagerHashMap = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        if(!messagesFile.exists()) {
            saveResource("messages.yml",false);
        }

        instanceClasses();
        mongoConnect.connect();

        getCommand("kban").setExecutor(new ban());
        getCommand("kunban").setExecutor(new unban());
        getCommand("kkick").setExecutor(new kick());
        getCommand("kmute").setExecutor(new mute());
        getServer().getPluginManager().registerEvents(new playerJoin(), this);
        getServer().getPluginManager().registerEvents(new playerChat(), this);

    }

    private void instanceClasses() {
        mongoConnect = new MongoConnect();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static KBBans getInstance() {
        return instance;
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    public File getMessagesFile(){
        return messagesFile;
    }
}
