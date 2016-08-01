package ModLog.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import ModLog.Logging.Event.Loggers.DropItemLog;
import ModLog.Logging.Event.Loggers.GamemodeChangeLog;
import ModLog.Logging.Event.Loggers.TeleportLog;

public class Listeners implements Listener {
	
	@EventHandler
	public void playerTeleportEvent(PlayerTeleportEvent event){
		TeleportLog log = new TeleportLog(event.getPlayer().getUniqueId());
		log.save(event);
	}
	
	@EventHandler
	public void playerDropItemEvent(PlayerDropItemEvent event){
		DropItemLog log = new DropItemLog(event.getPlayer().getUniqueId());
		log.save(event);
	}
	
	@EventHandler
	public void playerChangeGamemodeEvent(PlayerGameModeChangeEvent event){
		GamemodeChangeLog log = new GamemodeChangeLog(event.getPlayer().getUniqueId());
		log.save(event);
	}

}
