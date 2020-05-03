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

public class RegisterCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas players podem executar este comando!");
	        return true; 
	    }
		Player p = (Player)sender;
		if(Functions.verifyRegister(p)) {
			p.sendMessage("§cVocê já esta registrado!");
			return true;
		}
		if(MainClass.player.contains(p.getName())) {
			p.sendMessage("§cVocê já esta logado!");
			return true;
		}
		
		
		if(MainClass.auth.contains(p.getName()))
			if(!Functions.verifyRegister(p)) {
		
				if(args.length == 0) {
					p.sendMessage("§cUse: /register (senha)");
				} else if(args.length == 1) {

					for (int i = 0; i < args[0].length(); i++) {
						char c = args[0].charAt(i);
						if (c == 'a' || c == 'A' || c == 'b' || c == 'B' || c == 'c' || c == 'C' || c == 'd' || c == 'D' || c == 'e'
								|| c == 'E' || c == 'f' || c == 'F' || c == 'g' || c == 'G' || c == 'h' || c == 'H' || c == 'i'
								|| c == 'I' || c == 'j' || c == 'J' || c == 'k' || c == 'K' || c == 'l' || c == 'L' || c == 'm'
								|| c == 'M' || c == 'n' || c == 'N' || c == 'o' || c == 'O' || c == 'p' || c == 'P' || c == 'q'
								|| c == 'Q' || c == 'r' || c == 'R' || c == 's' || c == 'S' || c == 't' || c == 'T' || c == 'u'
								|| c == 'U' || c == 'v' || c == 'V' || c == 'w' || c == 'w' || c == 'x' || c == 'X' || c == 'y'
								|| c == 'W' || c == 'z' || c == 'Z' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4'
								|| c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '!' || c == '@' || c == '$'
								|| c == '%' || c == '&' || c == '*' || c == '+' || c == '-' || c == '_' || c == '=' || c == '?'
								|| c == '>' || c == '<' || c == '/' || c == '.' || c == ',' || c == '(' || c == ')') {
							continue;
						} else {
							p.sendMessage("§cO caractere '" + c + "' não pode ser usado na senha!");
							return true;
						}
					}


					if (args[0].length() < 5) {
						p.sendMessage("§cSenha muito curta! A sua senha deve conter no mínimo 5 caracteres.");
						return true;
					}
					if (args[0].length() > 15) {
						p.sendMessage("§cSenha muito longa! A sua senha deve conter no máximo 15 caracteres.");
						return true;
					}

					if (Functions.registerPlayer(p, args[0])) {
						p.sendMessage("§aRegistrado com sucesso!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);

						MainClass.auth.remove(p.getName());

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
