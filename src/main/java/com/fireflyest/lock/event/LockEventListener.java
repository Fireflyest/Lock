package com.fireflyest.lock.event;

import com.fireflyest.lock.data.YamlManager;
import com.fireflyest.lock.manager.LockDataManager;
import com.fireflyest.lock.manager.LockManager;
import com.fireflyest.lock.time.LockTime;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.Lockable;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class LockEventListener  implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        YamlManager.setup("PlayerData", event.getPlayer().getName());
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event){
        if(event.isCancelled())return;
        if(!"lock".equalsIgnoreCase(event.getLines()[0]))return;
        Directional data = (Directional)event.getBlock().getBlockData();
        Block block = event.getBlock().getLocation().add(data.getFacing().getOppositeFace().getDirection()).getBlock();
        if(!(block.getState() instanceof Lockable))return;
        Container container = (Container)block.getState();
        long date = LockTime.getDate();
        String name = event.getPlayer().getName();
        if(LockManager.lockContainer(container, name+","+ date)){
            LockDataManager.addData(name, date+".friends", event.getLine(1));
            LockDataManager.addData(name, date+".friends", event.getLine(2));
            LockDataManager.addData(name, date+".friends", event.getLine(3));
            event.getBlock().setType(Material.AIR);
            event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("成功给该容器上锁").create());
        }else {
            event.setLine(0, "§cError");
            event.setLine(1, "容器已被锁");
            event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("容器上锁失败").create());
        }
    }

    @EventHandler
    public void onPlayerPlayerInteract(PlayerInteractEvent event){
        if(!event.hasBlock())return;
        if(!(event.getClickedBlock().getState() instanceof Lockable))return;
        Container container = (Container)event.getClickedBlock().getState();
        LockManager.useContainer(container, event.getPlayer());
    }

}
