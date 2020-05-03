package net.jvinicius.login.v1.loginstaff;

import com.sun.media.sound.SoftFilter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.jvinicius.login.v1.principal.MainClass;
import net.jvinicius.login.v1.sql.Functions;
import net.jvinicius.login.v1.sql.SQL;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class LoginStaff {
    public static HashMap<Player, String> staffPlayers = new HashMap<Player, String>();
    public static SQL db;
    public static void StaffLogin(Player p){
if(!Functions.verifyRegisterStaff(p)){
    p.kickPlayer("§cSeu ID do discord não esta registrado!\nFale a um superior para te cadastrar!");
}
p.sendMessage("§aVá até seu discord e pegue a hash!");
        String hashPlayer = Functions.md5hashing(UUID.randomUUID().toString() + p.getName());
        staffPlayers.put(p, hashPlayer);
        MainClass.jda.getUserById(Functions.getDiscord(p)).openPrivateChannel().queue((channel) ->
        {


            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
            Date date = new Date();
            channel.sendMessage(new EmbedBuilder()
                    .setTitle("Autenticação da Staff")
                    .setDescription("Execute o comando: ```/loginstaff "+hashPlayer+"```")
                    .setColor(new Color(10551320))
                    .setThumbnail("https://img.icons8.com/cotton/2x/security-checked.png")
                    .setAuthor("JViinicius", null, "https://minotar.net/avatar/"+p.getName())
                    .addField("IP:", p.getAddress().toString(), true)
                    .addField("Data e Horario:", formatter.format(date), true)
                    .build()).queue();
        });;
    }




}
