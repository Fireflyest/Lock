package com.fireflyest.lock.data;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Language {

    public FileConfiguration getLang() {
        return lang;
    }

    private FileConfiguration lang;

    public static List<String>HELP = new ArrayList<>();
    public static String VERSION;
    public static String TITLE;
    public static String PLAYER_COMMAND;
    public static String RELOADING;
    public static String RELOADED;
    public static String OWNER;
    public static String FRIENDS;

    public Language(FileConfiguration lang){
        this.lang = lang;
        this.setUp();
    }

    private void setUp(){
        VERSION = lang.getString("Version");
        HELP = lang.getStringList("Help");
        TITLE = lang.getString("Title").replace("&", "§");
        PLAYER_COMMAND = TITLE + lang.getString("PlayerCommand").replace("&", "§");
        RELOADING = TITLE + lang.getString("Reloading").replace("&", "§");
        RELOADED = TITLE + lang.getString("Reloaded").replace("&", "§");
        OWNER = TITLE + lang.getString("Owner").replace("&", "§");
        FRIENDS = TITLE + lang.getString("Friends").replace("&", "§");
    }

}
