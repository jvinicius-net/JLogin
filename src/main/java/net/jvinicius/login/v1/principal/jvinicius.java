package net.jvinicius.login.v1.principal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


import net.jvinicius.login.v1.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


import net.jvinicius.login.v1.events.loginevents;
import net.jvinicius.login.v1.sql.Functions;



public class jvinicius extends JavaPlugin {
	public static ArrayList<String> auth = new ArrayList<String>();
	public static ArrayList<String> player = new ArrayList<String>();
	public static jvinicius instance;
	public static Plugin plugin;
	public  static HashMap<Player, Integer> LIST = new HashMap<>();



	@SuppressWarnings("deprecation")
	public void onEnable() {
		for (Player playerc : Bukkit.getOnlinePlayers()) {
			player.remove(playerc.getName());
			auth.add(playerc.getName());
		}
		saveDefaultConfig();
		saveConfig();

		registros();
		plugin = this;
		instance = this;
		
		try {Functions.setSQL();} catch (SQLException e) {}

	}
	public void onDisable() {
		saveConfig();
	}
	
	void registros() {
		Bukkit.getPluginManager().registerEvents(new loginevents(), this);


		getCommand("login").setExecutor(new login());
		getCommand("accountcheck").setExecutor(new account());
		getCommand("register").setExecutor(new register());
		getCommand("changepwd").setExecutor(new changepwd());


	}
}
