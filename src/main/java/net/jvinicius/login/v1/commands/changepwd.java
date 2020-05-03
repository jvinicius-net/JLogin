package net.jvinicius.login.v1.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.jvinicius.login.v1.principal.jvinicius;
import net.jvinicius.login.v1.sql.Functions;

public class changepwd implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas players podem executar este comando!");
			return true;
	    }
		Player p = (Player)sender;
		if(!jvinicius.player.contains(p.getName())) {
			p.sendMessage("§cVocê não esta logado!");
			return true;
		}
		if(args.length == 0) {
			p.sendMessage("§aUse: /changepwd (senha-antiga) (nova-senha)");
			return true;

		}
		if(args.length < 1) {
			p.sendMessage("§aUse: /changepwd (senha-antiga) (nova-senha)");
			return true;

		}
		if(Functions.verifyRegister(p)) {
			if(args[1].length() < 5) {
				p.sendMessage("§cSua senha deve conter mais de 5 digitos!");
				return true;
			}
			if(Functions.changePassword(p, args[0], args[1])) {
				p.sendMessage("§aSenha alterada com sucesso!");
			} 
			
		} else {
			p.sendMessage("§cVocê não está registrado!");
		}
		return false;
	}

}
