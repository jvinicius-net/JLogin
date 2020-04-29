package net.jvinicius.login.v1.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.jvinicius.login.v1.principal.jvinicius;
import net.jvinicius.login.v1.sql.Functions;

public class register implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (!(sender instanceof Player)) {
	        return true; 
	    }
		Player p = (Player)sender;

		if(jvinicius.player.contains(p.getName())) {
			p.sendMessage("§cVocê já esta logado!");
			return true;
		}
		
		
		if(jvinicius.auth.contains(p.getName()))
			if(!Functions.verifyRegister(p)) {
		
				if(args.length == 0) {
					p.sendMessage("§cUse: /register (senha)");
				} else if(args.length == 1) {

					if(args[0].length() < 8) {
						p.sendMessage("§cSua senha deve conter mais de 8 digitos!");
						return true;
					}

					if(Functions.registerPlayer(p, args[0])) {
						p.sendMessage("§aRegistrado com sucesso!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);

						jvinicius.auth.remove(p.getName());
						if(!jvinicius.player.contains(p.getName()))
							jvinicius.player.add(p.getName());
					}

				} else if(args.length != 1) {
					p.sendMessage("§cDigite sua senha apenas 1 vez!");
				}
			} else {
				p.sendMessage("§cVocê já está registrado!");
			}
		return false;
	}

}
