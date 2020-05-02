package net.jvinicius.login.v1.commands;



import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.jvinicius.login.v1.principal.jvinicius;
import net.jvinicius.login.v1.sql.Functions;

public class login implements CommandExecutor {

	@SuppressWarnings("unused")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas players podem executar este comando!");
			return true;
	    }
		Player p = (Player)sender;
		
		if(jvinicius.player.contains(p.getName())) {
			p.sendMessage("§cVocê já esta logado!");
			return true;
		}
		
		
		if(jvinicius.auth.contains(p.getName()) && Functions.verifyRegister(p))
			if(args.length == 0) {
				p.sendMessage("§cUse /login (senha)");
				return true;
			} else if(args.length == 1) {
				if(!Functions.verifyRegister(p)){
					p.sendMessage("§cVocê não esta registrado.");
					return true;
				}
				if(Functions.verifyLogin(p, args[0])) {

						jvinicius.auth.remove(p.getName());
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);

						p.sendMessage("§aLogado com sucesso!");

						if(!jvinicius.player.contains(p.getName())) {
							jvinicius.player.add(p.getName());
						}


					return false;

				} else {

					int tentativas = jvinicius.LIST.get(p);


					if (tentativas > 0) {
						p.sendMessage("§cA senha que você digitou está incorreta! Você possui mais " + tentativas + " tentantivas.");
						int antesD = tentativas;
						tentativas -= 1;
						jvinicius.LIST.replace(p,antesD,tentativas);
						return true;
					} else {
						p.kickPlayer("§cVocê excedeu o limite de tentativas de se autenticar.");
						return true;
					}
				}
			} else if(args.length > 1) {
				p.sendMessage("§cDigite a senha apenas 1 vez!");
				return true;

			}
		return false;
	}

}
