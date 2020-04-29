package net.jvinicius.login.v1.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.jvinicius.login.v1.principal.jvinicius;
import net.jvinicius.login.v1.sql.Functions;

public class account implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (!(sender instanceof Player)) {
	        return true; 
	    }
		
		
		Player p = (Player)sender;
		if(!jvinicius.player.contains(p.getName())) {
			p.sendMessage("§cVocê não esta logado!");
			return true;
		}
		if(!p.hasPermission("jlogin.command.account")) {
			p.sendMessage("§cVocê não tem permissão!!");
			return true;
		}
		if(args.length == 0) {
			p.sendMessage("§cUse: /accountcheck (player)");
			return true;

		}
		Player p2 = Bukkit.getPlayer(args[0]);
		if(p2 == null) {
			p.sendMessage("§cEste jogador esta offline!");
			return true;
		}
		if(!Functions.verifyRegister(p2)) {
			p.sendMessage("§cEste jogador não é registrado!");
			return true;
		}
		p.sendMessage("");
		p.sendMessage("§aInformações da conta: §f"+p2.getName());
		p.sendMessage("");
		p.sendMessage("§aUUID: §f"+p2.getUniqueId().toString());
		p.sendMessage("§aIP: §f"+p2.getAddress().toString());
		if(Functions.getLastSeen(p2) != null) {
			p.sendMessage("§aUltimo login em: §f"+Functions.getLastSeen(p2));
		}
		p.sendMessage("");
			p.sendMessage("§aContas registradas no mesmo endereço de ip:");
			p.sendMessage(Functions.getAccounts(p2.getAddress().toString()));
			p.sendMessage("");

		


		
		

		return false;
	}

}
