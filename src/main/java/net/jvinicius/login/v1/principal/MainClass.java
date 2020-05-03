package net.jvinicius.login.v1.principal;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


import com.google.common.io.Resources;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.jvinicius.login.v1.captcha.events.CaptchaEvents;
import net.jvinicius.login.v1.captcha.types.HeadCaptchaType;
import net.jvinicius.login.v1.captcha.types.ItemCaptchaType;
import net.jvinicius.login.v1.commands.*;
import net.jvinicius.login.v1.loginstaff.commands.LoginStaffCommand;
import net.jvinicius.login.v1.loginstaff.commands.LoginStaffConsoleCommand;
import net.jvinicius.login.v1.loginstaff.events.StaffEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


import net.jvinicius.login.v1.events.LoginEvent;
import net.jvinicius.login.v1.sql.Functions;

import javax.security.auth.login.LoginException;


public class MainClass extends JavaPlugin {
	public static ArrayList<String> auth = new ArrayList<String>();
	public static ArrayList<String> player = new ArrayList<String>();
	public static ArrayList<Player> captchaPlayers = new ArrayList<>();
	public static MainClass instance;
	public static Plugin plugin;
	public  static HashMap<Player, Integer> LIST = new HashMap<>();
	public static JDA jda;


	@SuppressWarnings("deprecation")
	public void onEnable() {
		for (Player playerc : Bukkit.getOnlinePlayers()) {
			player.remove(playerc.getName());
			auth.add(playerc.getName());
		}
		saveDefaultConfig();
		try {
		File file;
		String allText = null;

		allText = Resources.toString((file = new File(getDataFolder() + File.separator, "config.yml")).toURI().toURL(), Charset.forName("UTF-8"));

		getConfig().loadFromString(allText);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		registros();
		plugin = this;
		instance = this;
		antPirate();
		try {Functions.setSQL();} catch (SQLException e) {}
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage("JLogin - Plugin Habilitado");

		Bukkit.getConsoleSender().sendMessage("");
	}
	public void onDisable() {
	//	saveConfig();
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage("JLogin - Plugin Desabilitado");
		Bukkit.getConsoleSender().sendMessage("");
	}


	public boolean antPirate() {
		boolean pirata = false;

		if (plugin.getDescription().getAuthors().size() > 1) {
			pirata = true;
		}
		if (!plugin.getDescription().getAuthors().contains("JVinicius")) {
			pirata = true;
		}
		if (!plugin.getDescription().getName().equalsIgnoreCase("JLogin")) {
			pirata = true;
		}
		if (!plugin.getDescription().getWebsite().equalsIgnoreCase("https://jvinicius.net")) {
			pirata = true;
		}

		if (pirata == true) {
			Bukkit.getConsoleSender().sendMessage("");
			Bukkit.getConsoleSender().sendMessage("JLogin - Plugin Desabilitado");
			Bukkit.getConsoleSender().sendMessage("Motivo - Modificação indevida detectada no arquivo 'plugin.yml'");
			Bukkit.getConsoleSender().sendMessage("");


			getPluginLoader().disablePlugin(plugin);
		}

		return pirata;
	}


	void registros() {
		Bukkit.getPluginManager().registerEvents(new LoginEvent(), this);
		if(getConfig().getBoolean("captcha.active")){
		Bukkit.getPluginManager().registerEvents(new CaptchaEvents(), this);
		if(getConfig().getInt("captcha.type") == 1){
			Bukkit.getPluginManager().registerEvents(new HeadCaptchaType(), this);
			getLogger().info("O estilo 'Head' do captcha foi carregado com sucesso!");
		}else if(getConfig().getInt("captcha.type") == 2){
			Bukkit.getPluginManager().registerEvents(new ItemCaptchaType(), this);
			getLogger().info("O estilo 'Item' do captcha foi carregado com sucesso!");
		}else{
			Bukkit.getLogger().severe("Tipo de captcha não selecionada, desativando o plugin");
			getPluginLoader().disablePlugin(MainClass.plugin);
		}
		}

		if(getConfig().getBoolean("stafflogin.active")){
			if(getConfig().getString("stafflogin.discordtoken") != null){
				getCommand("loginstaff").setExecutor(new LoginStaffCommand());
				getCommand("stafflogin").setExecutor(new LoginStaffConsoleCommand());
				Bukkit.getPluginManager().registerEvents(new StaffEvent(), this);



				try {
				jda = new JDABuilder(getConfig().getString("stafflogin.discordtoken")).build();
			} catch (LoginException e) {
				e.printStackTrace();
			}
			}else{
				Bukkit.getLogger().severe("Token não encontrado, desativando o plugin!");
				getPluginLoader().disablePlugin(MainClass.plugin);
			}

		}


		getCommand("login").setExecutor(new LoginCommand());
		getCommand("accountcheck").setExecutor(new AccountCommand());
		getCommand("register").setExecutor(new RegisterCommand());
		getCommand("changepwd").setExecutor(new ChangePwdCommand());


	}
}
