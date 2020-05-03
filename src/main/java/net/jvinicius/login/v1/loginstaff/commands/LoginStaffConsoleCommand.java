package net.jvinicius.login.v1.loginstaff.commands;

import net.jvinicius.login.v1.principal.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginStaffConsoleCommand implements CommandExecutor {

    @SuppressWarnings("unused")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            if(!MainClass.plugin.getConfig().getBoolean("stafflogin.active")){
                Bukkit.getConsoleSender().sendMessage("§cStaffLogin desativado na config!");
            }
            if(args.length == 2){
                if(args[0].equalsIgnoreCase("register")){

                    return false;
                }else if(args[0].equalsIgnoreCase("unregister")){


                    return false;
                }

            }
            return true;
        }else{
            if(!MainClass.plugin.getConfig().getBoolean("stafflogin.active")){
                sender.sendMessage("§cStaffLogin desativado na config!");
            }
            sender.sendMessage("§cApenas o console pode usar este comando!");
        }
        return true;

    }


}
