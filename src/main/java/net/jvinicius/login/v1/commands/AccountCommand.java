package net.jvinicius.login.v1.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.jvinicius.login.v1.principal.MainClass;
import net.jvinicius.login.v1.sql.Functions;

public class AccountCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas players podem executar este comando!");
	        return true;
	    }
		
		
		Player p = (Player)sender;
		if(!MainClass.player.contains(p.getName())) {
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
		OfflinePlayer p2 = Bukkit.getOfflinePlayer(args[0]);
		if(!Functions.verifyRegister(p2)) {
			p.sendMessage("§cEste jogador não é registrado!");
			return true;
		}
		p.sendMessage("");
		p.sendMessage("§aInformações da conta: §f"+p2.getName());
		p.sendMessage("");
		p.sendMessage("§aUUID: §f"+p2.getUniqueId().toString());
		p.sendMessage("§aIP: §f"+Functions.getUserIP(p2.getName()));
		if(Functions.getLastSeen(p2) != null) {
			p.sendMessage("§aUltimo login em: §f"+Functions.getLastSeen(p2));
		}
		p.sendMessage("");
		p.sendMessage("§aContas registradas no mesmo endereço de ip:");
		p.sendMessage("§7(§2ONLINE§7) §7(§7OFFLINE§7) §7(§cBANIDO§7)");
		p.sendMessage("");

		p.sendMessage(Functions.getAccounts(Functions.getUserIP(p2.getName())).replace("&","§"));
			p.sendMessage("");

		


		
		

		return false;
	}

}
