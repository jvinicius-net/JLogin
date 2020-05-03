package net.jvinicius.login.v1.loginstaff.events;

import com.google.common.base.Charsets;
import net.jvinicius.login.v1.loginstaff.LoginStaff;
import net.jvinicius.login.v1.principal.MainClass;
import net.jvinicius.login.v1.sql.Functions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class StaffEvent implements Listener {
    @EventHandler
    void andar(PlayerMoveEvent e) {
        if(LoginStaff.staffPlayers.containsKey(e.getPlayer())){
            e.getPlayer().teleport(e.getPlayer().getLocation());
        }

    }

    @EventHandler
    void sair(PlayerQuitEvent e) {

        if(LoginStaff.staffPlayers.containsKey(e.getPlayer())) {
            LoginStaff.staffPlayers.remove(e.getPlayer());
        }

    }

    @EventHandler
    void falar(AsyncPlayerChatEvent e) {

        if(LoginStaff.staffPlayers.containsKey(e.getPlayer())) {
            e.getPlayer().sendMessage("§aVá até seu discord e pegue o codigo!");
            e.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    void comando(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        p.getName().toLowerCase();

        if(LoginStaff.staffPlayers.containsKey(p) && !e.getMessage().toLowerCase().startsWith("/loginstaff")){
            e.setCancelled(true);
        }
    }

    @EventHandler
    void interagir(PlayerInteractEvent e) {

        if(LoginStaff.staffPlayers.containsKey(e.getPlayer())){
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void botarBlock(BlockPlaceEvent e)
    {
        @SuppressWarnings("unused")
        Player p = e.getPlayer();

        if (LoginStaff.staffPlayers.containsKey(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void quebrarBloco(BlockBreakEvent e)
    {
        @SuppressWarnings("unused")
        Player p = e.getPlayer();

        if (LoginStaff.staffPlayers.containsKey(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void droparItem(PlayerDropItemEvent e)
    {
        @SuppressWarnings("unused")
        Player p = e.getPlayer();

        if (LoginStaff.staffPlayers.containsKey(e.getPlayer())) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void noDamage (EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (LoginStaff.staffPlayers.containsKey(p)) {
                e.setCancelled(true);
            }

        }
    }



    @EventHandler
    void chat(PlayerChatEvent e) {
        if(LoginStaff.staffPlayers.containsKey(e.getPlayer())){
            e.setCancelled(true);

        }
    }
}
