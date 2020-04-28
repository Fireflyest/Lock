package com.fireflyest.lock.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class LockTab implements TabCompleter {

    private static List<String> list = new ArrayList<String>();

    public LockTab(){
        list.add("reload");
        list.add("default");
        list.add("help");
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        if(command.getName().equalsIgnoreCase("lock")){
            List<String> tab = new ArrayList<String>();
            if(args.length == 1){
                for(String sub : list){
                    if(sub.contains(args[0]))tab.add(sub);
                }
            }
            return tab;
        }
        return null;
    }

}
