package ModLog.Logging.Event.Loggers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import ModLog.Logging.Event.EventSaver;

public class GamemodeChangeLog extends EventSaver{

	static final String[] GAMEMODE_TO = {"GamemodeTo"};
	
	public GamemodeChangeLog(UUID uuid) {
		super(uuid);
	}

	@Override
	protected Map<String, Object> getValues() {
		Map<String, Object> map = new HashMap<>();
		map.put("Gamemode to", GameMode.valueOf(get(String.class, GAMEMODE_TO)));
		return map;
	}

	@Override
	protected void set(PlayerEvent event2) {
		PlayerGameModeChangeEvent event = (PlayerGameModeChangeEvent)event2;
		set(event.getNewGameMode().name(), GAMEMODE_TO);
	}

}
