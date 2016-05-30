package me.BeanMC.dev;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FormatHandler
{
	public static List<Format<String, String>> formats(Player player)
	{
		User user = new User(player);
		
		List<Format<String, String>> formats = new ArrayList<Format<String, String>>();
		
		Format<String, String> coins0 = new Format<>("{COIN}", String.valueOf(user.getCoins()));
		Format<String, String> coins1 = new Format<>("{COINS}", String.valueOf(user.getCoins()));
		Format<String, String> deaths0 = new Format<>("{DEATH}", String.valueOf(user.getDeaths()));
		Format<String, String> deaths1 = new Format<>("{DEATHS}", String.valueOf(user.getDeaths()));
		Format<String, String> displayName0 = new Format<>("{DISPLAY}", player.getDisplayName());
		Format<String, String> displayName1 = new Format<>("{DISPLAYNAME}", player.getDisplayName());
		Format<String, String> kdr0;
		Format<String, String> kdr1;
		NumberFormat format = NumberFormat.getInstance();
		format.setRoundingMode(RoundingMode.DOWN);
		format.setMaximumFractionDigits(2);
		try
		{
			kdr0 = new Format<>("{K/D}", String.valueOf(format.format(user.getKills()/user.getDeaths())));
			kdr1 = new Format<>("{KDR}", String.valueOf(format.format(user.getKills()/user.getDeaths())));
		}
		catch(ArithmeticException e)
		{
			kdr0 = new Format<>("{K/D}", String.valueOf(0));
			kdr1 = new Format<>("{KDR}", String.valueOf(0));
		}
		Format<String, String> kills0 = new Format<>("{KILL}", String.valueOf(user.getKills()));
		Format<String, String> kills1 = new Format<>("{KILLS}", String.valueOf(user.getKills()));
		Format<String, String> level = new Format<>("{LEVEL}", String.valueOf(user.getLevel()));
		Format<String, String> online = new Format<>("{ONLINE}", String.valueOf(Bukkit.getOnlinePlayers().size()));
		Format<String, String> playerName0 = new Format<>("{NAME}", player.getName());
		Format<String, String> playerName1 = new Format<>("{PLAYER}", player.getName());
		Format<String, String> timePlayed0 = new Format<>("{TIMEPLAYED-MILLIS}", String.valueOf(user.getTimePlayedMillis()));
		Format<String, String> timePlayed1 = new Format<>("{PLAYTIME-MILLIS}", String.valueOf(user.getTimePlayedMillis()));
		Format<String, String> timePlayed2 = new Format<>("{TIMEPLAYED-TICKS}", String.valueOf(user.getTimePlayedTicks()));
		Format<String, String> timePlayed3 = new Format<>("{PLAYTIME-TICKS}", String.valueOf(user.getTimePlayedTicks()));
		Format<String, String> timePlayed4 = new Format<>("{TIMEPLAYED-SECS}", String.valueOf(user.getTimePlayedSecs()));
		Format<String, String> timePlayed5 = new Format<>("{PLAYTIME-SECS}", String.valueOf(user.getTimePlayedSecs()));
		Format<String, String> timePlayed6 = new Format<>("{TIMEPLAYED-MINS}", String.valueOf(user.getTimePlayedMins()));
		Format<String, String> timePlayed7 = new Format<>("{PLAYTIME-MINS}", String.valueOf(user.getTimePlayedMins()));
		Format<String, String> timePlayed8 = new Format<>("{TIMEPLAYED-HOURS}", String.valueOf(user.getTimePlayedHours()));
		Format<String, String> timePlayed9 = new Format<>("{PLAYTIME-HOURS}", String.valueOf(user.getTimePlayedHours()));
		Format<String, String> timePlayed10 = new Format<>("{TIMEPLAYED-DAYS}", String.valueOf(user.getTimePlayedDays()));
		Format<String, String> timePlayed11 = new Format<>("{PLAYTIME-DAYS}", String.valueOf(user.getTimePlayedDays()));
		Format<String, String> xp = new Format<>("{XP}", String.valueOf(user.getXpLevel()));
		
		formats.add(coins0);
		formats.add(coins1);
		formats.add(deaths0);
		formats.add(deaths1);
		formats.add(displayName0);
		formats.add(displayName1);
		formats.add(kdr0);
		formats.add(kdr1);
		formats.add(kills0);
		formats.add(kills1);
		formats.add(level);
		formats.add(online);
		formats.add(playerName0);
		formats.add(playerName1);
		formats.add(timePlayed0);
		formats.add(timePlayed1);
		formats.add(timePlayed2);
		formats.add(timePlayed3);
		formats.add(timePlayed4);
		formats.add(timePlayed5);
		formats.add(timePlayed6);
		formats.add(timePlayed7);
		formats.add(timePlayed8);
		formats.add(timePlayed9);
		formats.add(timePlayed10);
		formats.add(timePlayed11);
		formats.add(xp);
		return formats;
	}
	
	public static String formatMessage(Player player, String message)
	{
		List<Format<String, String>> formats = formats(player);
		String format = message;
		for(Format<String, String> tempformat : formats) format = format.replace(tempformat.getKey(), tempformat.getValue());
		return format;
	}
	
	public static List<String> formatMessage(Player player, List<String> messages)
	{
		List<String> format = messages;
		for(String message : messages) formatMessage(player, message);
		return format;
	}
}
