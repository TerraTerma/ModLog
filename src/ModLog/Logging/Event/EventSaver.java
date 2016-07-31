package ModLog.Logging.Event;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import ModLog.ModLogPlugin;
import ModLog.Logging.Saver;

public abstract class EventSaver<E extends PlayerEvent> extends Saver{

	public static final String[] DATE = {"Date"};
	public static final String[] TIME = {"Time"};
	public static final String[] IP = {"IP"};
	public static final String[] LOCATION = {"Location"};
	public static final String[] GAMEMODE = {"Gamemode"};
	
	E EVENT;

	public EventSaver(E event) {
		super(ModLogPlugin.EVENT_LOG);
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		InetSocketAddress address = player.getAddress();
		setSection(event.getEventName() + "." + player.getUniqueId().toString());
		LocalDateTime dateTime = LocalDateTime.now();
		set(dateTime.getDayOfMonth() + "/" + dateTime.getMonthValue() + "/" + dateTime.getYear(), DATE);
		set(dateTime.getHour() + ":" + dateTime.getMinute(), TIME);
		set(address.getHostName() + ":" + address.getPort(), IP);
		set(loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + "," + loc.getWorld().getName(), LOCATION);
		set(player.getGameMode().name(), GAMEMODE);
	}
	
	public E getEvent(){
		return EVENT;
	}
}
