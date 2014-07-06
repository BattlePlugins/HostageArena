package mc.euro.extraction.util;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
 
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin; 
import org.bukkit.util.Vector;

/**
 * https://www.youtube.com/watch?v=pyBM83LbG9U <br/>
 * https://dl.dropboxusercontent.com/u/48481378/MyConfig.java <br/>
 * 
 * @author Appljuze
 */
public class CustomConfig 
{
    private int comments;
    private ConfigManager manager;
 
    private File file;
    private FileConfiguration config;
 
    public CustomConfig(InputStream configStream, File configFile, int comments, JavaPlugin plugin) 
    {
        this.comments = comments;
        this.manager = new ConfigManager(plugin);
        this.file = configFile;
        this.config = YamlConfiguration.loadConfiguration(configStream);
    }
    
    public ItemStack getItemStack(String path) { return this.config.getItemStack(path);}
    
    public ItemStack getItemStack(String path, ItemStack item) { return this.config.getItemStack(path, item);}
    
    public Vector getVector(String path) {return this.config.getVector(path);}
 
    public Object get(String path) {return this.config.get(path);}
 
    public Object get(String path, Object def) {return this.config.get(path, def);}
 
    public String getString(String path) {return this.config.getString(path);}
 
    public String getString(String path, String def) {return this.config.getString(path, def);}
 
    public int getInt(String path) {return this.config.getInt(path);}
 
    public int getInt(String path, int def) {return this.config.getInt(path, def);}
 
    public boolean getBoolean(String path) {return this.config.getBoolean(path);}
 
    public boolean getBoolean(String path, boolean def) {return this.config.getBoolean(path, def);}
 
    public void createSection(String path) {this.config.createSection(path);}
 
    public ConfigurationSection getConfigurationSection(String path) {return this.config.getConfigurationSection(path);}
 
    public double getDouble(String path) {return this.config.getDouble(path);}
 
    public double getDouble(String path, double def) {return this.config.getDouble(path, def);}
 
    public List<?> getList(String path) {return this.config.getList(path);}
 
    public List<?> getList(String path, List<?> def) {return this.config.getList(path, def);}
 
    public boolean contains(String path) {return this.config.contains(path);}
 
    public void removeKey(String path) {this.config.set(path, null);}
 
    public void set(String path, Object value) {this.config.set(path, value);}
 
    public void set(String path, Object value, String comment)
    {
        if(!this.config.contains(path)) 
        {
            this.config.set(manager.getPluginName() + "_COMMENT_" + comments, " " + comment);
            comments++;
        }
        this.config.set(path, value);
    }
 
    public void set(String path, Object value, String[] comment) 
    {
        for(String comm : comment) 
        {
            if(!this.config.contains(path)) 
            {
                this.config.set(manager.getPluginName() + "_COMMENT_" + comments, " " + comm);
                comments++;
            }
        }
        this.config.set(path, value);
    }
 
    public void setHeader(String[] header) 
    {
        manager.setHeader(this.file, header);
        this.comments = header.length + 2;
        this.reloadConfig();
    }
 
    public void reloadConfig() {this.config = YamlConfiguration.loadConfiguration(manager.getConfigContent(file));}
 
    public void saveConfig() 
    {
        String config = this.config.saveToString();
        manager.saveConfig(config, this.file);
    }
 
    public Set<String> getKeys() {return this.config.getKeys(false);}
}
