package net.jvinicius.login.v1.captcha.types;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class ItemCaptchaType implements Listener {
    public static void sendCaptcha(Player p) {
        String nome;
        List<Integer> lista = Arrays.asList(new Integer[] { Integer.valueOf(261), Integer.valueOf(311), Integer.valueOf(1), Integer.valueOf(138), Integer.valueOf(340), Integer.valueOf(257), Integer.valueOf(268), Integer.valueOf(384), Integer.valueOf(329) });
        Inventory inv;
        if ((inv = Bukkit.createInventory((InventoryHolder)p, 9, "Captcha [" + lista.get((new Random()).nextInt(lista.size())) + "]")).getName().contains("261")) {
            nome = "Arco";
        } else if (inv.getName().contains("311")) {
            nome = "Peitoral";
        } else if (inv.getName().contains("1")) {
            nome = "Pedra";
        } else if (inv.getName().contains("138")) {
            nome = "Sinalizador";
        } else if (inv.getName().contains("340")) {
            nome = "Livro";
        } else if (inv.getName().contains("257")) {
            nome = "Picareta";
        } else if (inv.getName().contains("268")) {
            nome = "Espada";
        } else if (inv.getName().contains("384")) {
            nome = "Frasco de EXP";
        } else {
            nome = "Sela";
        }
        if ((new Random()).nextInt(100) < 50)
            Collections.sort(lista);
        for (int i = 0; i != 9; i++) {
            ItemStack item;
            ItemMeta item2;
            (item2 = (item = new ItemStack(Material.getMaterial(((Integer)lista.get(i)).intValue()))).getItemMeta()).setDisplayName("§aClique no " + nome);
            item.setItemMeta(item2);
            inv.addItem(new ItemStack[] { item });
        }
        p.openInventory(inv);
        (new BukkitRunnable() {
            public final void run() {
                if (!MainClass.captchaPlayers.contains(p)) {
                    p.closeInventory();
                    cancel();
                }
                if (MainClass.captchaPlayers.contains(p) && !p.getOpenInventory().equals(inv))
                   p.openInventory(inv);
            }
        }).runTaskTimer((Plugin) MainClass.plugin, 0L, 35L);
    }

    @EventHandler
    private static void a(InventoryClickEvent e) {
        Inventory invs = e.getClickedInventory();
        Player p = (Player)e.getWhoClicked();
        if (MainClass.captchaPlayers.contains(p) && invs.getName().contains("Captcha [")) {
            e.setCancelled(true);

            p.closeInventory();
            if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {
                int id = Integer.parseInt(invs.getName().replace("Captcha [", "").replace("]", ""));
                if (e.getCurrentItem().getTypeId() != id) {
                    p.kickPlayer("§cVocê errou o captcha!\n§cEntre novamente no servidor!");
                    return;
                }
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                p.sendMessage("§aCaptcha verificado com sucesso!");
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

                MainClass.captchaPlayers.remove(p);
            }
        }
    }
}
