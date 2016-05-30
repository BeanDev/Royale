package me.BeanMC.dev;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R2.PacketPlayOutPlayerListHeaderFooter;

public class Royale extends JavaPlugin implements Listener
{
	
	static Royale plugin;
	
	public void onEnable()
	{
		plugin = this;
		
		saveDefaultConfig();
		
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			int i = 0;
			int i0 = 0;
				
			@Override
			public void run()
			{
				for(Player player : Bukkit.getOnlinePlayers())
				{
					if(player.isOnline())
					{
						User user = new User(player);
						user.getPlayerFile().reloadConfig();
						
						i++;
						if(i > getConfig().getStringList("playerlist.animated.header").size()-1) i = 0;
						String header = FormatHandler.formatMessage(player, "{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', getConfig().getStringList("playerlist.animated.header").get(i).replace('\n', '\n')) +"\"}");
						String footer = FormatHandler.formatMessage(player, "{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', getConfig().getStringList("playerlist.animated.footer").get(i).replace('\n', '\n')) +"\"}");
						
						PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(ChatSerializer.a(header));
						
						try
						{
							Field field = packet.getClass().getDeclaredField("b");
							field.setAccessible(true);
							field.set(packet, ChatSerializer.a(footer));
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						
						((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
						
						i0++;
						
						if(i0 > getConfig().getStringList("scoreboard.sidebar.animated.displayName").size()-1) i0 = 0;
						
						ScoreboardManager manager = Bukkit.getScoreboardManager();
						Scoreboard scoreboard = manager.getNewScoreboard();
						
						Objective sidebar = scoreboard.registerNewObjective("sidebar", "dummy");
						
						sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
						sidebar.setDisplayName(FormatHandler.formatMessage(player, ChatColor.translateAlternateColorCodes('&', getConfig().getStringList("scoreboard.sidebar.animated.displayName").get(i0))));
						for(int i1 = 0; i1 < getConfig().getStringList("scoreboard.sidebar.animated.display." + i0).size(); i1++)
						{
							String team = FormatHandler.formatMessage(player, getConfig().getStringList("scoreboard.sidebar.animated.display." + i0).get(i1));
							sidebar.getScore(ChatColor.translateAlternateColorCodes('&', team)).setScore(getConfig().getStringList("scoreboard.sidebar.animated.display." + i0).size()-i1);;
						}
						
						player.setScoreboard(scoreboard);
					}
				}
			}
		}, 0, 2);
	}
	
	public void onDisable()
	{
		plugin = null;
		
		for(Player player : Bukkit.getOnlinePlayers())
		{
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(args.length < 2) return true;
		
		if(args[0].equalsIgnoreCase("motd"))
		{
			FileConfiguration config = getConfig();
			
			config.set("ping.serverMOTD", args[1].replace("_", " "));
			try
			{
				config.save("plugin" + File.separator + "Royale" + File.separator + "config.yml");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return true;
		}
		
		if(args[0].equalsIgnoreCase("sidebar"))
		{
			if(args[1].equalsIgnoreCase("display") && args.length >= 4)
			{
				FileConfiguration config = getConfig();
				
				config.set("scoreboard.sidebar.animated.display." + args[2], args[3].replace("_", " "));
				try
				{
					config.save("plugin" + File.separator + "Royale" + File.separator + "config.yml");
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
			if(args[1].equalsIgnoreCase("displayname") && args.length >= 4)
			{
				FileConfiguration config = getConfig();
				
				config.set("scoreboard.sidebar.animated.displayName." + args[2], args[3].replace("_", " "));
				try
				{
					config.save("plugin" + File.separator + "Royale" + File.separator + "config.yml");
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
			return true;
		}
		
		if(args[0].equalsIgnoreCase("playerlist"))
		{
			if(args[1].equalsIgnoreCase("header") && args.length >= 4)
			{
				FileConfiguration config = getConfig();
				
				config.set("playerlist.animated.header", config.getStringList("playerlist.animated.header").set(Integer.parseInt(args[2]), args[3].replace("_", " ")));
				try
				{
					config.save("plugin" + File.separator + "Royale" + File.separator + "config.yml");
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
			if(args[1].equalsIgnoreCase("footer") && args.length >= 4)
			{
				FileConfiguration config = getConfig();
				
				config.set("playerlist.animated.footer", config.getStringList("playerlist.animated.footer").set(Integer.parseInt(args[2]), args[3].replace("_", " ")));
				try
				{
					config.save("plugin" + File.separator + "Royale" + File.separator + "config.yml");
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
			return true;
		}
		return true;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		User user = new User(event.getPlayer());
		user.getPlayerFile().saveDefaultConfig();
	}
	
	@EventHandler
	public void onPing(ServerListPingEvent event)
	{
		event.setMaxPlayers(getConfig().getInt("ping.fakeMaxSlots"));
		event.setMotd(ChatColor.translateAlternateColorCodes('&', getConfig().getString("ping.serverMOTD").replace('\n', '\n')));
	}
}
