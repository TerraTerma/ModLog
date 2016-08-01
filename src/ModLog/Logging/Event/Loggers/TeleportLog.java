package ModLog.Logging.Event.Loggers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import ModLog.Logging.Event.EventSaver;

public class TeleportLog extends EventSaver {

	static final String[] TELEPORT_TO = {
			"Teleport"
	};
	static final String[] CAUSE = {
			"Cause"
	};

	public TeleportLog(java.util.UUID uuid) {
		super(uuid);
	}

	@Override
	protected Map<String, Object> getValues() {
		Map<String, Object> map = new HashMap<>();
		String[] locS = get(String.class, TELEPORT_TO).split(",");
		int x = Integer.parseInt(locS[0]);
		int y = Integer.parseInt(locS[1]);
		int z = Integer.parseInt(locS[2]);
		World world = Bukkit.getWorld(locS[3]);
		Location loc = new Location(world, x, y, z);
		map.put("Teleport", loc);
		
		TeleportCause cause = TeleportCause.valueOf(get(String.class, CAUSE));
		map.put("Cause", cause);
		return map;
	}

	@Override
	protected void set(PlayerEvent event2) {
		PlayerTeleportEvent event = (PlayerTeleportEvent) event2;
		Location loc = event.getTo();
		set(loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + "," + loc.getWorld().getName(),
				TELEPORT_TO);
		set(event.getCause().name(), CAUSE);
	}

}
