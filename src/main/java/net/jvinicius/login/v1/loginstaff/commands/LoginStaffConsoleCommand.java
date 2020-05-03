package net.jvinicius.login.v1.loginstaff.commands;

import net.jvinicius.login.v1.loginstaff.LoginStaff;
import net.jvinicius.login.v1.principal.MainClass;
import net.jvinicius.login.v1.sql.Functions;
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
            if(args.length > 0){
                if(args[0].equalsIgnoreCase("register")){
                    // register USER ID
                    if(args.length < 2){
                        Bukkit.getConsoleSender().sendMessage("§cUse: /stafflogin register (player/uuid) (Discord-ID)");
                        return true;
                    }
                    if(Functions.registerPlayerStaff(Bukkit.getOfflinePlayer(args[1]),args[2])){
                        Bukkit.getConsoleSender().sendMessage("§aPlayer Registrado com sucesso!");
                        return false;
                    }else{
                        Bukkit.getConsoleSender().sendMessage("§cErro interno!");
                        return true;
                    }
                }else if(args[0].equalsIgnoreCase("unregister")){
                    if(args.length < 1){
                        Bukkit.getConsoleSender().sendMessage("§cUse: /stafflogin unregister (player/uuid)");
                        return true;
                    }
                    if(Functions.deletePlayer(Bukkit.getOfflinePlayer(args[1]))){
                        Bukkit.getConsoleSender().sendMessage("§aRegistro do player removido com sucesso!");
                        return false;
                    }else{
                        Bukkit.getConsoleSender().sendMessage("§cErro interno!");
                        return true;
                    }

                }else{
                    Bukkit.getConsoleSender().sendMessage("§a/stafflogin register §7(Player/UUID) (Discord-ID) §e- §aRegistre um staff.");
                    Bukkit.getConsoleSender().sendMessage("§a/stafflogin unregister §7(Player/UUID) §e- §a Remova um registro de um staff.");
                    return true;
                }

            }else{
                Bukkit.getConsoleSender().sendMessage("§a/stafflogin register §7(Player/UUID) (Discord-ID) §e- §aRegistre um staff.");
                Bukkit.getConsoleSender().sendMessage("§a/stafflogin unregister §7(Player/UUID) §e- §a Remova um registro de um staff.");
                return true;
            }
        }else{
            if(!MainClass.plugin.getConfig().getBoolean("stafflogin.active")){
                sender.sendMessage("§cStaffLogin desativado na config!");
                return true;
            }
           Player p = (Player)sender;
            if(!p.hasPermission("jlogin.command.owner")){
                p.sendMessage("§cVocê não tem permissão!");
                return true;
            }
if(args.length > 0){
if(args[0].equalsIgnoreCase("register")) {
    if (args.length < 2) {
        p.sendMessage("§cUse: /stafflogin register (player/uuid) (Discord-ID)");
        return true;
    }


    if (Functions.registerPlayerStaff(Bukkit.getOfflinePlayer(args[1]), args[2])) {
        p.sendMessage("§aPlayer Registrado com sucesso!");
        return false;
    } else {
        p.sendMessage("§cErro interno!");
        return true;
    }


}else if(args[0].equalsIgnoreCase("unregister")){
    if (args.length < 2) {
        p.sendMessage("§cUse: /stafflogin register (player/uuid) (Discord-ID)");
        return true;
    }
    if(Functions.deletePlayer(Bukkit.getOfflinePlayer(args[1]))){
        p.sendMessage("§aRegistro do player removido com sucesso!");
        return false;
    }else{
        p.sendMessage("§cErro interno!");
        return true;
    }
}else{
    p.sendMessage("§a/stafflogin register §7(Player/UUID) (Discord-ID) §e- §aRegistre um staff.");
    p.sendMessage("§a/stafflogin unregister §7(Player/UUID) §e- §a Remova um registro de um staff.");
    return true;
}
}else{
    p.sendMessage("§a/stafflogin register §7(Player/UUID) (Discord-ID) §e- §aRegistre um staff.");
    p.sendMessage("§a/stafflogin unregister §7(Player/UUID) §e- §a Remova um registro de um staff.");
    return true;
}

        }

    }


}
