package com.fireflyest.lock.manager;

import com.fireflyest.lock.Lock;
import com.fireflyest.lock.data.Language;
import com.fireflyest.lock.data.YamlManager;
import com.fireflyest.lock.time.LockTime;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class LockManager {

    private LockManager(){

    }

    public static boolean lockContainer(Container container, String lock){
        if(container.getInventory() instanceof DoubleChestInventory){
            DoubleChestInventory doubleChest = (DoubleChestInventory)container.getInventory();
            Container left = (Container)doubleChest.getLeftSide().getLocation().getBlock().getState();
            Container right = (Container)doubleChest.getRightSide().getLocation().getBlock().getState();
            setContainerLock(left, lock);
            setContainerLock(right, lock);
            return left.getLock().equals(lock) && right.getLock().equals(lock);
        }else{
            setContainerLock(container, lock);
            return container.getLock().equals(lock);
        }
    }

    public static void useContainer(Container container, Player player){
        if(!container.isLocked())return;
        String lock = container.getLock();
        String[] data = lock.split(",");
        if(data[0].equalsIgnoreCase(player.getName())){
            lockContainer(container, null);
            List<String> friends = YamlManager.getPlayerData(data[0]).getStringList(data[1]+".friends");
            if(!friends.isEmpty())player.sendMessage(Language.FRIENDS.replace("%friends%", friends.toString()));
        }else if(LockDataManager.isFriend(data[0], data[1], player.getName())){
            lockContainer(container, null);
            LockDataManager.addData(data[0], data[1]+".traces",player.getName()+","+ LockTime.getDate());
        }else{
            player.sendMessage(Language.OWNER.replace("%player%", data[0]));
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                lockContainer(container, lock);
            }
        }.runTask(Lock.getInstance());
    }

    public static void setContainerLock(Container container, String lock){
        if(container.isLocked() && lock !=null)return;
        container.setLock(lock);
        container.update();
    }

}
