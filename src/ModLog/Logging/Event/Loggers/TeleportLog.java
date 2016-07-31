package ModLog.Logging.Event.Loggers;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerTeleportEvent;

import ModLog.Logging.Event.EventReader;
import ModLog.Logging.Event.EventSaver;

public class TeleportLog extends EventSaver<PlayerTeleportEvent>{

	static final String[] TELEPORT_TO = {"Teleport"};
	static final String[] CAUSE = {"Cause"};
	
	public TeleportLog(PlayerTeleportEvent event) {
		super(event);
		Location loc = event.getTo();
		set(loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + "," + loc.getWorld().getName(), TELEPORT_TO);
		set(event.getCause(), CAUSE);
	}
	
	public static class TeleportReader extends EventReader {

		public TeleportReader(java.util.UUID uuid) {
			super(uuid);
		}
		
	}

}
