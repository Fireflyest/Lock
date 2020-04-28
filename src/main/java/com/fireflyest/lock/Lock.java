package com.fireflyest.lock;

import com.fireflyest.lock.command.LockCommand;
import com.fireflyest.lock.data.YamlManager;
import com.fireflyest.lock.event.LockEventListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name="Lock", version="1.0.0-SNAPSHOT")
@Author(value = "Fireflyest")
@Command(name = "lock", usage = "/lock <reload|help|default>")
@Command(name = "l")
@ApiVersion(value = ApiVersion.Target.v1_14)
public class Lock extends JavaPlugin {

    private static JavaPlugin plugin;

    public static JavaPlugin getInstance() {return plugin;}

    @Override
    public void onEnable() {

        plugin = this;

        YamlManager.iniYamlManager(this);

        this.getServer().getPluginManager().registerEvents( new LockEventListener(), this );

        this.getCommand("lock").setExecutor(new LockCommand());
    }

    @Override
    public void onDisable() {

    }

}
