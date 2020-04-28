package com.fireflyest.lock.manager;

import com.fireflyest.lock.data.YamlManager;

import java.util.List;

public class LockDataManager {

    private LockDataManager(){

    }

    public static void addData(String owner, String key, String value){
        if("".equals(value))return;
        List<String> friends = YamlManager.getPlayerData(owner).getStringList(key);
        friends.add(value);
        YamlManager.setPlayerData(owner, key, friends);
    }

    public static boolean isFriend(String owner, String id, String name){
        List<String> friends = YamlManager.getPlayerData(owner).getStringList(id+".friends");
        return friends.contains(name);
    }

}
