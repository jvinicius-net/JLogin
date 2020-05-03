package net.jvinicius.login.v1.captcha;

import net.jvinicius.login.v1.principal.MainClass;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

public class CaptchaEvents implements Listener {
    @EventHandler
    void andar(PlayerMoveEvent e) {
        if(MainClass.captchaPlayers.contains(e.getPlayer())){
            e.getPlayer().teleport(e.getPlayer().getLocation());
        }

    }




    @EventHandler
    void falar(AsyncPlayerChatEvent e) {

        if(MainClass.captchaPlayers.contains(e.getPlayer())){

            e.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    void comando(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        p.getName().toLowerCase();

        if(MainClass.captchaPlayers.contains(p)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    void interagir(PlayerInteractEvent e) {

        if(MainClass.captchaPlayers.contains(e.getPlayer())){
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void botarBlock(BlockPlaceEvent e)
    {
        @SuppressWarnings("unused")
        Player p = e.getPlayer();

        if(MainClass.captchaPlayers.contains(p)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void quebrarBloco(BlockBreakEvent e)
    {
        @SuppressWarnings("unused")
        Player p = e.getPlayer();

        if(MainClass.captchaPlayers.contains(p)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void droparItem(PlayerDropItemEvent e)
    {
        @SuppressWarnings("unused")
        Player p = e.getPlayer();

        if(MainClass.captchaPlayers.contains(p)){
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void noDamage (EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if(MainClass.captchaPlayers.contains(p)){
                e.setCancelled(true);
            }

        }
    }



    @EventHandler
    void chat(PlayerChatEvent e) {
        if(MainClass.captchaPlayers.contains(e.getPlayer()))
            e.setCancelled(true);
    }
}
