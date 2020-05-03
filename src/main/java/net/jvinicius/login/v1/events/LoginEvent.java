package net.jvinicius.login.v1.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.base.Charsets;

import net.jvinicius.login.v1.principal.MainClass;
import net.jvinicius.login.v1.sql.Functions;

@SuppressWarnings("deprecation")
public class LoginEvent implements Listener {

	@EventHandler
	void andar(PlayerMoveEvent e) {
		if(MainClass.auth.contains(e.getPlayer().getName())){
			e.getPlayer().teleport(e.getPlayer().getLocation());
		}

	}

	@EventHandler
	void sair(PlayerQuitEvent e) {
		Functions.updateSair(e.getPlayer());
		MainClass.LIST.remove(e.getPlayer());
		if(MainClass.player.contains(e.getPlayer().getName())) {
			MainClass.player.remove(e.getPlayer().getName());
		}
		if(MainClass.auth.contains(e.getPlayer().getName())) {
			MainClass.auth.remove(e.getPlayer().getName());
		}
	}

	private String fetchUUID(String nick) {
		return UUID.nameUUIDFromBytes(("OfflinePlayer:" + nick).getBytes(Charsets.UTF_8)).toString().replaceAll("-", "");
	}

	@EventHandler
	void entrou(PlayerJoinEvent e) {
		String uuidPlayer = e.getPlayer().getUniqueId().toString().replace("-","");
		if(!uuidPlayer.equalsIgnoreCase(fetchUUID(e.getPlayer().getName()))) {
			e.getPlayer().kickPlayer("§4UUID Spoof não é permitido neste servidor!");
		}
		MainClass.auth.add(e.getPlayer().getName());
		MainClass.LIST.put(e.getPlayer(), 3);

		for(Player p : Bukkit.getOnlinePlayers()) {
			if(MainClass.auth.contains(p.getName()) && !MainClass.player.contains(p.getName())) {
				if(Functions.verifyRegister(p)) {
					p.sendMessage("§cUse: /login (senha)");
				} else {
					p.sendMessage("§cUse: /register (senha)");
				}
			}
			new BukkitRunnable() {

				@Override
				public void run() {
					if(MainClass.auth.contains(p.getName())) {
						p.kickPlayer("§cVocê demorou para se logar/registrar!");
					}else {
						this.cancel();
					}

				}
			}.runTaskLater(MainClass.instance, 20*15);
		}
	}



	@EventHandler
	void falar(AsyncPlayerChatEvent e) {

		if(MainClass.auth.contains(e.getPlayer().getName())) {
			if(Functions.verifyRegister(e.getPlayer())) {
				e.getPlayer().sendMessage("§cUse: /login (senha)");
			} else {
				e.getPlayer().sendMessage("§cUse: /register (senha)");
			}
			e.setCancelled(true);
		}

	}

	@EventHandler(priority = EventPriority.HIGH)
	void comando(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		p.getName().toLowerCase();

		if(MainClass.auth.contains(p.getName()) && !e.getMessage().toLowerCase().startsWith("/login") && !e.getMessage().toLowerCase().startsWith("/register")){
			e.setCancelled(true);
		}
	}

	@EventHandler
	void interagir(PlayerInteractEvent e) {

		if(MainClass.auth.contains(e.getPlayer().getName())){
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void botarBlock(BlockPlaceEvent e)
	{
		@SuppressWarnings("unused")
		Player p = e.getPlayer();

		if (MainClass.auth.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void quebrarBloco(BlockBreakEvent e)
	{
		@SuppressWarnings("unused")
		Player p = e.getPlayer();

		if (MainClass.auth.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void droparItem(PlayerDropItemEvent e)
	{
		@SuppressWarnings("unused")
		Player p = e.getPlayer();

		if (MainClass.auth.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
		}
	}


	@EventHandler
	public void noDamage (EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (MainClass.auth.contains(p.getName())) {
				e.setCancelled(true);
			}

		}
	}



	@EventHandler
	void chat(PlayerChatEvent e) {
		if(MainClass.auth.contains(e.getPlayer().getName()))
			e.setCancelled(true);
	}
}
