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
    private static SQL db;
    public static void StaffLogin(Player p){
if(!verifyRegister(p)){
    p.kickPlayer("§cSeu ID do discord não esta registrado!\nFale a um superior para te cadastrar!");
}
        String hashPlayer = Functions.md5hashing(UUID.randomUUID().toString() + p.getName());
        staffPlayers.put(p, hashPlayer);
        MainClass.jda.getUserById(getDiscord(p)).openPrivateChannel().queue((channel) ->
        {
            EmbedBuilder eb = new EmbedBuilder();


            channel.sendMessage(new EmbedBuilder()
                    .setTitle("Autenticação da Staff")
                    .setDescription("Execute o comando: ```/loginstaff "+hashPlayer+"```")
                    .setColor(new Color(10551320))
                    .setThumbnail("https://img.icons8.com/cotton/2x/security-checked.png")
                    .setAuthor("JViinicius", null, "https://minotar.net/avatar/"+p.getName())
                    .addField("IP:", "127.0.0.1", true)
                    .addField("Data e Horario:", "03 de maio de 2020 as 12:07 AM", true)
                    .build()).queue();
        });;
    }
    public static Boolean verifyRegister(Player p){
        Boolean status = false;
        String username = p.getUniqueId().toString();
        if (!db.checkConnection()) {
            db.openConnection();
        }
        try {
            Statement s = db.getConnection().createStatement();

            ResultSet rs;

            rs = s.executeQuery("SELECT * FROM jvlogin_stafflogin WHERE `uuid`='" +
                    username + "';");

            if (rs.next()) {
                db.closeConnection();
                status= true;
            }else {
                db.closeConnection();
                status = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return status;
    }

    public static Boolean verifyRegister(OfflinePlayer p){
        Boolean status = false;
        String username = p.getUniqueId().toString();
        if (!db.checkConnection()) {
            db.openConnection();
        }
        try {
            Statement s = db.getConnection().createStatement();

            ResultSet rs;

            rs = s.executeQuery("SELECT * FROM jvlogin_stafflogin WHERE `uuid`='" +
                    username + "';");

            if (rs.next()) {
                db.closeConnection();
                status= true;
            }else {
                db.closeConnection();
                status = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return status;
    }

    public static Boolean deletePlayer(OfflinePlayer p){
        Boolean status = false;
        String username = p.getUniqueId().toString();
        if (!db.checkConnection()) {
            db.openConnection();
        }
        try {
            Statement s = db.getConnection().createStatement();

            ResultSet rs;

            rs = s.executeQuery("SELECT * FROM jvlogin_stafflogin WHERE `uuid`='" +
                    username + "';");


            if(rs.next()){
                status = true;
                db.closeConnection();
            }else{
                db.closeConnection();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }



        return status;
    }
    public static Boolean deletePlayer(Player p){
        Boolean status = false;
        String username = p.getUniqueId().toString();
        if (!db.checkConnection()) {
            db.openConnection();
        }
        try {
            Statement s = db.getConnection().createStatement();

            ResultSet rs;

            rs = s.executeQuery("SELECT * FROM jvlogin_stafflogin WHERE `uuid`='" +
                    username + "';");


            if(rs.next()){
                status = true;
                db.closeConnection();
            }else{
                db.closeConnection();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }



        return status;
    }

    public static String getDiscord(Player p){
        String idDiscord = "";
        if (!db.checkConnection()) {
            db.openConnection();
        }
        try {
            Statement s = db.getConnection().createStatement();

            ResultSet rs;

            rs = s.executeQuery("SELECT * FROM jvlogin_stafflogin WHERE `uuid`='" +
                    p.getUniqueId().toString() + "';");

            if(rs.next()) {

                idDiscord = rs.getString("discordid");




            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        db.closeConnection();

        return idDiscord;
    }
    public static String getDiscord(OfflinePlayer p){
        String idDiscord = "";
        if (!db.checkConnection()) {
            db.openConnection();
        }
        try {
            Statement s = db.getConnection().createStatement();

            ResultSet rs;

            rs = s.executeQuery("SELECT * FROM jvlogin_stafflogin WHERE `uuid`='" +
                    p.getUniqueId().toString() + "';");

            if(rs.next()) {

                idDiscord = rs.getString("discordid");




            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        db.closeConnection();

        return idDiscord;
    }

    public static boolean registerPlayer(Player p, String id)
    {
        String name = p.getUniqueId().toString();
        if (!db.checkConnection()) {
            db.openConnection();
        }

        Statement s;
        try {
            s = db.getConnection().createStatement();


            ResultSet rs = s.executeQuery("SELECT * FROM jvlogin_stafflogin WHERE `uuid`='" +
                    name + "';");
            @SuppressWarnings("unused")
            String retorno = "";
            if (!rs.next()) {
                Statement statement = db.getConnection().createStatement();

                statement.executeUpdate("INSERT INTO `jvlogin_stafflogin`(`username`, `discordid`) VALUES ('"+p.getUniqueId()+"','"+id+"');");
                db.closeConnection();
                return true;
            }else {
                db.closeConnection();
                return false;

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }
    public static boolean registerPlayer(OfflinePlayer p, String id)
    {
        String name = p.getUniqueId().toString();
        if (!db.checkConnection()) {
            db.openConnection();
        }

        Statement s;
        try {
            s = db.getConnection().createStatement();


            ResultSet rs = s.executeQuery("SELECT * FROM jvlogin_stafflogin WHERE `uuid`='" +
                    name + "';");
            @SuppressWarnings("unused")
            String retorno = "";
            if (!rs.next()) {
                Statement statement = db.getConnection().createStatement();

                statement.executeUpdate("INSERT INTO `jvlogin_stafflogin`(`username`, `discordid`) VALUES ('"+p.getUniqueId()+"','"+id+"');");
                db.closeConnection();
                return true;
            }else {
                db.closeConnection();
                return false;

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }




}
