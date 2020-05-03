package net.jvinicius.login.v1.commands;



import net.jvinicius.login.v1.captcha.types.HeadCaptchaType;
import net.jvinicius.login.v1.captcha.types.ItemCaptchaType;
import net.jvinicius.login.v1.loginstaff.LoginStaff;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.jvinicius.login.v1.principal.MainClass;
import net.jvinicius.login.v1.sql.Functions;

public class LoginCommand implements CommandExecutor {

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
		
		
		if(MainClass.auth.contains(p.getName()) && Functions.verifyRegister(p))
			if(args.length == 0) {
				p.sendMessage("§cUse /login (senha)");
				return true;
			} else if(args.length == 1) {
				if(!Functions.verifyRegister(p)){
					p.sendMessage("§cVocê não esta registrado.");
					return true;
				}
				if(Functions.verifyLogin(p, args[0])) {

						MainClass.auth.remove(p.getName());
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);

						p.sendMessage("§aLogado com sucesso!");
					if(!p.hasPermission("jlogin.captcha.bypass")){


						if(MainClass.plugin.getConfig().getBoolean("captcha.active")){
							MainClass.captchaPlayers.add(p);
							if(MainClass.plugin.getConfig().getInt("captcha.type") == 1) {
								HeadCaptchaType.sendCaptcha(p);
							}else if(MainClass.plugin.getConfig().getInt("captcha.type") == 2){
								ItemCaptchaType.sendCaptcha(p);
							}else{
								Bukkit.getLogger().severe("Tipo de captcha não selecionada. Desativando o plugin");
								MainClass.plugin.getPluginLoader().disablePlugin(MainClass.plugin);
							}
						}else{
							if(MainClass.plugin.getConfig().getBoolean("stafflogin.active")){


								if(p.hasPermission("jlogin.staff.login")){
									LoginStaff.StaffLogin(p);
								}else{
									if(!MainClass.player.contains(p.getName())) {
										MainClass.player.add(p.getName());
									}
								}
							}else{
								if(!MainClass.player.contains(p.getName())) {
									MainClass.player.add(p.getName());
								}
							}
						}
					}else{
						if(MainClass.plugin.getConfig().getBoolean("stafflogin.active")){


							if(p.hasPermission("jlogin.staff.login")){
								LoginStaff.StaffLogin(p);
							}else{
								if(!MainClass.player.contains(p.getName())) {
									MainClass.player.add(p.getName());
								}
							}
						}else{
							if(!MainClass.player.contains(p.getName())) {
								MainClass.player.add(p.getName());
							}
						}
					}


					return false;

				} else {

					int tentativas = MainClass.LIST.get(p);


					if (tentativas > 0) {
						p.sendMessage("§cA senha que você digitou está incorreta! Você possui mais " + tentativas + " tentantivas.");
						int antesD = tentativas;
						tentativas -= 1;
						MainClass.LIST.replace(p,antesD,tentativas);
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
