package ModLog.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import ModLog.Logging.Event.Loggers.TeleportLog;

public class Listeners implements Listener {
	
	@EventHandler
	public void playerTeleportEvent(PlayerTeleportEvent event){
		TeleportLog log = new TeleportLog(event.getPlayer().getUniqueId());
		log.save(event);
	}

}
