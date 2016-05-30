package me.BeanMC.dev;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config
{
	
	Royale plugin = Royale.plugin;
	
    private File file;
    private FileConfiguration config;
    private String name;
	
    public Config(String name)
    {
        this.name = name;
        file();
        config();
    }

    public void config()
    {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void file()
    {
        file = new File(plugin.getDataFolder(), name);
    }

    public void reloadConfig()
    {
        config();
    }

    public void saveDefaultConfig()
    {
        if(file == null)
            file();
        if(config == null)
            config();
        if(!file.exists())
        {
            config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), name));
            try
            {
                config.save(file);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
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
