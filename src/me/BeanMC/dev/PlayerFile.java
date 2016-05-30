package me.BeanMC.dev;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerFile
{
	
    Royale plugin = Royale.plugin;
    
    private FileConfiguration config;
    private File file;
    private Player player;
    @SuppressWarnings("unused")
	private String name;
	
    public PlayerFile(Player player)
    {
        this.player = player;
        file = new File((new StringBuilder("plugins")).append(File.separator).append(plugin.getName()).append(File.separator).append("players").append(File.separator).append(player.getUniqueId().toString()).append(".yml").toString());
        config = YamlConfiguration.loadConfiguration(file);
        name = file.getName();
    }

    public void config()
    {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void file()
    {
        file = new File((new StringBuilder("plugins")).append(File.separator).append(plugin.getName()).append(File.separator).append("players").append(File.separator).append(player.getUniqueId().toString()).append(".yml").toString());
    }

    public void reloadConfig()
    {
        config();
    }

    @SuppressWarnings("deprecation")
	public void saveDefaultConfig()
    {
        if(file == null)
            file();
        if(config == null)
            config();
        if(!file.exists())
            config = YamlConfiguration.loadConfiguration(plugin.getResource("player-template.yml"));
        try
        {
            config.save(file);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saveConfig(FileConfiguration tempConfig)
    {
        try
        {
            tempConfig.save(file);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig()
    {
        return config;
    }

    public File getFile()
    {
        return file;
    }
}
