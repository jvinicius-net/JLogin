package net.jvinicius.login.v1.loginstaff.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.jvinicius.login.v1.loginstaff.LoginStaff;
import net.jvinicius.login.v1.principal.MainClass;
import net.jvinicius.login.v1.sql.Functions;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginStaffCommand  implements CommandExecutor {

    @SuppressWarnings("unused")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas players podem executar este comando!");
            return true;
        }
        Player p = (Player)sender;

        if(MainClass.player.contains(p.getName())) {
            p.sendMessage("§cVocê já esta logado!");
            return true;
        }
        if(!p.hasPermission("jlogin.staff.login")){
            p.sendMessage("§cVocê não tem permissão a esta comando!");
            return true;
        }
if(args.length == 1){

    if(LoginStaff.staffPlayers.containsKey(p)){

        if(LoginStaff.staffPlayers.containsValue(args[0])){
            LoginStaff.staffPlayers.remove(p);
            if(!MainClass.player.contains(p.getName())) {
                MainClass.player.add(p.getName());
            }
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);

                p.sendMessage("§aAutenticado com sucesso!");
            return false;
        }else{
            if(LoginStaff.staffPlayers.containsKey(p)) {
                LoginStaff.staffPlayers.remove(p);
            }
            p.kickPlayer("§cVocê digitou a hash incorreta!");
        }

    }else{
        p.kickPlayer("§cVocê não esta na lista de staffs!");
    }

}else{
    p.sendMessage("§cUse /loginstaff (hash)");
}
        return true;
    }
}
