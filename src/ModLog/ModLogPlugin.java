package ModLog;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

public class ModLogPlugin extends JavaPlugin {
	
	JavaPlugin PLUGIN;
	
	public static File EVENT_LOG = new File("plugins/ModLog/EventLog.yml");
	public static File COMMAND_LOG = new File("plugins/ModLog/CommandLog.yml");
	
	public void onEnable(){
		PLUGIN = this;
	}

}
