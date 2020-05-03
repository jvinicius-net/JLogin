package net.jvinicius.login.v1.captcha.types;


import java.util.Random;

import net.jvinicius.login.v1.loginstaff.LoginStaff;
import net.jvinicius.login.v1.principal.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class HeadCaptchaType implements Listener {
    public static void sendCaptcha(Player p) {
        Inventory inv = Bukkit.createInventory((InventoryHolder)p, 27, "Captcha");
        ItemStack cabeca;
        SkullMeta cabeca2;
        (cabeca2 = (SkullMeta)(cabeca = new ItemStack(Material.SKULL_ITEM, 1, (short)3)).getItemMeta()).setDisplayName("�7Clique na cabe�a verde!");
        cabeca2.setOwner("Lightnen");
        cabeca.setItemMeta((ItemMeta)cabeca2);
        for (int i = 0; i != 27; i++)
            inv.setItem(i, cabeca);
        SkullMeta cabeca2c;
        ItemStack cabecac;
        (cabeca2c = (SkullMeta)(cabecac = new ItemStack(Material.SKULL_ITEM, 1, (short)3)).getItemMeta()).setDisplayName("�aClique aqui");
        cabeca2c.setOwner("Lime");
        cabecac.setItemMeta((ItemMeta)cabeca2c);
        inv.setItem((new Random()).nextInt(26), cabecac);
        p.openInventory(inv);

        new BukkitRunnable() {
            public final void run() {
                if (!MainClass.captchaPlayers.contains(p)) {
                    p.closeInventory();
                    this.cancel();
                }
                if (MainClass.captchaPlayers.contains(p) && !p.getOpenInventory().equals(inv)) {
                    p.openInventory(inv);
                }
            }
        }.runTaskTimer((Plugin)MainClass.instance, 0L, 35L);


    }

    @EventHandler
    private static void Evento(InventoryClickEvent e) {
        Inventory invs = e.getClickedInventory();
        Player p = (Player)e.getWhoClicked();
        if (MainClass.captchaPlayers.contains(p) && invs.getName().equals("Captcha")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {

                p.closeInventory();
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals("�7Clique na cabe�a verde!"))
                    p.kickPlayer("�cVoc� errou o captcha!\n�cEntre novamente no servidor!");
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals("�aClique aqui")) {
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                    p.sendMessage("�aCaptcha verificado com sucesso!");
                    MainClass.captchaPlayers.remove(p);

                    if (MainClass.plugin.getConfig().getBoolean("stafflogin.active")) {


                        if (p.hasPermission("jlogin.staff.login")) {
                            LoginStaff.StaffLogin(p);
                        } else {
                            if (!MainClass.player.contains(p.getName())) {
                                MainClass.player.add(p.getName());
                            }
                        }
                    } else {
                        if (!MainClass.player.contains(p.getName())) {
                            MainClass.player.add(p.getName());
                        }
                    }

                }
            }
        }
    }
}
