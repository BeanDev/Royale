package me.BeanMC.dev;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class User
{
	
	Royale plugin = Royale.plugin;
	
	private Player player;
	private PlayerFile playerfile;
	private FileConfiguration config;
	private long startTime;
	private long stopTime;

	public User(Player player)
	{
		this.player = player;
		this.playerfile = new PlayerFile(player);
		this.config = playerfile.getConfig();
	}
	
	public User(String name)
	{
		this.player = Bukkit.getPlayer(name);
		this.playerfile = new PlayerFile(player);
	}
	
	public void addTimePlayed()
	{
		config.set("timeplayed", getTimePlayedMillis() + (stopTime-startTime));
	}
	
	public int getCoins()
	{
		return config.getInt("coins");
	}
	
	public FileConfiguration getDatafile()
	{
		return config;
	}
	
	public int getDeaths()
	{
		return config.getInt("deaths");
	}
	
	public int getKills()
	{
		return config.getInt("kills");
	}
	
	public int getLevel()
	{
		return config.getInt("level");
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public PlayerFile getPlayerFile()
	{
		return playerfile;
	}
	
	public int getTimePlayedDays()
	{
		return getTimePlayedHours()/24;
	}
	
	public int getTimePlayedHours()
	{
		return getTimePlayedMins()/60;
	}
	
	public int getTimePlayedMillis()
	{
		return (int) (config.getLong("timeplayed")/1);
	}
	
	public int getTimePlayedMins()
	{
		return getTimePlayedSecs()/60;
	}
	
	public int getTimePlayedSecs()
	{
		return getTimePlayedTicks()/20;
	}
	
	public int getTimePlayedTicks()
	{
		return getTimePlayedMillis()/50;
	}
	
	public int getXpLevel()
	{
		return config.getInt("xp");
	}
	
	public void setOfflineAt(long time)
	{
		this.stopTime = time;
	}
	
	public void setOnlineAt(long time)
	{
		this.startTime = time;
	}
}
