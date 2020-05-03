package net.jvinicius.login.v1.sql;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.jvinicius.login.v1.principal.MainClass;


public class Functions {
	  private static SQL db;
	  public static MainClass plugin;
	  
	  public Functions(MainClass plugin2){
		  plugin = plugin2;
	  }
	  public static void setSQL()
			    throws SQLException
			  {


			  		db = new SQL(plugin);
			  		db.openConnection();
			      Statement statement = db.getConnection().createStatement();
				  statement.executeUpdate("CREATE TABLE IF NOT EXISTS `jvlogin_users` (`username` varchar(32), `password` varchar(32), `ipaddr` varchar(32),`last_seen` varchar(32));");

				  db.closeConnection();
			      
				  
			    }
	 

	 public static String md5hashing(String text)
	    {   String hashtext = null;
	        try 
	        {
	            String plaintext = text;
	            MessageDigest m = MessageDigest.getInstance("MD5");
	            m.reset();
	            m.update(plaintext.getBytes());
	            byte[] digest = m.digest();
	            BigInteger bigInt = new BigInteger(1,digest);
	            hashtext = bigInt.toString(16);
	            // Now we need to zero pad it if you actually want the full 32 chars.
	            while(hashtext.length() < 32 ){
	              hashtext = "0"+hashtext;   
	            }
	        } catch (Exception e1) 
	        {
	        }

	        return hashtext;//Hashing.sha512().hashString(, StandardCharsets.UTF_8).toString();
	    }
	  
		 public static boolean verifyLogin(Player p, String pwd){
				    String username = p.getName();
				    if (!db.checkConnection()) {
				      db.openConnection();
				    }
					try {
				    Statement s = db.getConnection().createStatement();
			
				    ResultSet rs;
				
						rs = s.executeQuery("SELECT * FROM jvlogin_users WHERE `username`='" + 
								username + "' AND `password`='"+md5hashing(pwd)+"';");
				
				    if (rs.next()) {
				    	  Statement statement = db.getConnection().createStatement();
				      	  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");  
				    	    Date date = new Date();  
				    	
				    	
				    	  
						statement.executeUpdate("UPDATE `jvlogin_users` SET `ipAddr`='"+p.getAddress()+"', `last_seen`='"+formatter.format(date)+"' WHERE `username`='"+p.getName()+"';");
								    	
					  	  db.closeConnection();	
					  	return true;
				    }else {
					  	  db.closeConnection();	
					  	return false;
				    }

					} catch (SQLException e) {
						e.printStackTrace();
					}

				 
					  
					return false;
				  }
		 
		  
		 public static boolean updateSair(Player p){
				    String username = p.getName();
				    if (!db.checkConnection()) {
				      db.openConnection();
				    }
					try {
				    Statement s = db.getConnection().createStatement();
			
				    ResultSet rs;
				
						rs = s.executeQuery("SELECT * FROM jvlogin_users WHERE `username`='" + 
								username + "';");
				
				    if (rs.next()) {
				    	  Statement statement = db.getConnection().createStatement();
				    	  
				    	  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");  
				    	    Date date = new Date();  
				    	
				    	  
						statement.executeUpdate("UPDATE `jvlogin_users` SET `last_seen`='"+formatter.format(date)+"' WHERE `username`='"+p.getName()+"';");
								    	
					  	  db.closeConnection();	
					  	return true;
				    }else {
					  	  db.closeConnection();	
					  	return false;
				    }

					} catch (SQLException e) {
						e.printStackTrace();
					}

				 
					  
					return false;
				  }


	public static Boolean verifyRegister(Player p){
		Boolean status = false;
		String username = p.getName();
		if (!db.checkConnection()) {
			db.openConnection();
		}
		try {
			Statement s = db.getConnection().createStatement();

			ResultSet rs;

			rs = s.executeQuery("SELECT * FROM jvlogin_users WHERE `username`='" +
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
		String username = p.getName();
		if (!db.checkConnection()) {
			db.openConnection();
		}
		try {
			Statement s = db.getConnection().createStatement();

			ResultSet rs;

			rs = s.executeQuery("SELECT * FROM jvlogin_users WHERE `username`='" +
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


	public static String getAccounts(String ip){
		String accounts = "";
		if (!db.checkConnection()) {
			db.openConnection();
		}
		try {
			Statement s = db.getConnection().createStatement();

			ResultSet rs;

			rs = s.executeQuery("SELECT * FROM jvlogin_users WHERE `ipaddr`='" +
					ip + "';");

			String playerChecks = "";
			while(rs.next()) {
				if(Bukkit.getOfflinePlayer(rs.getString("username").toString()).isBanned()){
					playerChecks = "&c"+rs.getString("username");
				}else if(Bukkit.getOfflinePlayer(rs.getString("username").toString()).isOnline()){
					playerChecks = "&2"+rs.getString("username");
				}else{
					playerChecks = "&7"+rs.getString("username");
				}



				if(accounts == "") {
					accounts += "&r"+playerChecks;
				}else {
					accounts += "&7,&r"+playerChecks;

				}

			}


		} catch (SQLException e) {
			e.printStackTrace();
		}


		db.closeConnection();

		return accounts;
	}

	public static String getUserIP(String username){
		String accounts = "";
		if (!db.checkConnection()) {
			db.openConnection();
		}
		try {
			Statement s = db.getConnection().createStatement();

			ResultSet rs;

			rs = s.executeQuery("SELECT * FROM jvlogin_users WHERE `username`='" +
					username + "';");

			if(rs.next()) {




					accounts = rs.getString("ipaddr");


			}


		} catch (SQLException e) {
			e.printStackTrace();
		}


		db.closeConnection();

		return accounts;
	}
	public static String getLastSeen(Player p){
		String last = "";
		if (!db.checkConnection()) {
			db.openConnection();
		}
		try {
			Statement s = db.getConnection().createStatement();

			ResultSet rs;

			rs = s.executeQuery("SELECT * FROM jvlogin_users WHERE `username`='" +
					p.getName() + "';");

			if (rs.next()) {

				last = rs.getString("last_seen");
				db.closeConnection();
			}else {
				db.closeConnection();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}



		return last;
	}


	public static String getLastSeen(OfflinePlayer p){
		String last = "";
		if (!db.checkConnection()) {
			db.openConnection();
		}
		try {
			Statement s = db.getConnection().createStatement();

			ResultSet rs;

			rs = s.executeQuery("SELECT * FROM jvlogin_users WHERE `username`='" +
					p.getName() + "';");

			if (rs.next()) {

				last = rs.getString("last_seen");
				db.closeConnection();
			}else {
				db.closeConnection();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}



		return last;
	}



	public static Boolean changePassword(Player p, String pwdOld, String pwdNew){
		Boolean status = false;

		String username = p.getName();
		if (!db.checkConnection()) {
			db.openConnection();
		}
		try {
			Statement s = db.getConnection().createStatement();

			ResultSet rs;

			rs = s.executeQuery("SELECT * FROM jvlogin_users WHERE `username`='" +
					username + "' AND `password`='"+md5hashing(pwdOld)+"';");

			if (rs.next()) {
				//
				Statement statement = db.getConnection().createStatement();
				statement.executeUpdate("UPDATE `jvlogin_users` SET `password`='"+md5hashing(pwdNew)+"' WHERE `username`='"+p.getName()+"' AND `password` = '"+md5hashing(pwdOld)+"';");

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







	public static boolean registerPlayer(Player p, String pwd)
	{
		String name = p.getName();
		if (!db.checkConnection()) {
			db.openConnection();
		}

		Statement s;
		try {
			s = db.getConnection().createStatement();


			ResultSet rs = s.executeQuery("SELECT * FROM jvlogin_users WHERE `username`='" +
					name + "';");
			@SuppressWarnings("unused")
			String retorno = "";
			if (!rs.next()) {
				Statement statement = db.getConnection().createStatement();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
				Date date = new Date();

				statement.executeUpdate("INSERT INTO `jvlogin_users`(`username`, `password`, `ipaddr`, `last_seen`) VALUES ('"+p.getName()+"','"+md5hashing(pwd)+"','"+p.getAddress()+"','"+formatter.format(date)+"');");
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
