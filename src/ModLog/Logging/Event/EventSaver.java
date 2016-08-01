package ModLog.Logging.Event;

import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import ModLog.ModLogPlugin;
import ModLog.Logging.Saver;
import ModLog.Logging.Event.Loggers.TeleportLog;

public abstract class EventSaver extends Saver {
	
	public static final String[] DATE = {"Date"};
	public static final String[] TIME = {"Time"};
	public static final String[] IP = {"IP"};
	public static final String[] LOCATION = {"Location"};
	public static final String[] GAMEMODE = {"Gamemode"};
	
	public static final List<Class<? extends EventSaver>> SUPPORTED_CLASSES = Arrays.asList(
		TeleportLog.class
	);
	
	UUID UUID;
	
	protected abstract Map<String, Object> getValues();
	protected abstract void set(PlayerEvent event);
	protected abstract String getEventName();
	
	public EventSaver(UUID uuid) {
		super(ModLogPlugin.EVENT_LOG);
		UUID = uuid;
		setSection(getEventName() + "." + uuid.toString());
	}
	
	public void save(PlayerEvent event){
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
		set(event);
	}

	public Map<String, Object> getData(){
		Map<String, Object> map = new HashMap<>();
		
		String[] date = get(String.class, EventSaver.DATE).split("/");
		LocalDate localDate = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
		map.put("Date", localDate);
		
		String[] time = get(String.class, EventSaver.TIME).split(":");
		LocalTime localTime = LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
		map.put("Time", localTime);
		
		
		String[] IP = get(String.class, EventSaver.IP).split(":");
		InetSocketAddress socket = new InetSocketAddress(IP[0], Integer.parseInt(IP[1]));
		map.put("IP", socket);
		
		GameMode mode = GameMode.valueOf(get(String.class, EventSaver.GAMEMODE));
		map.put("Gamemode", mode);
		
		String[] locS = get(String.class, EventSaver.LOCATION).split(",");
		int x = Integer.parseInt(locS[0]);
		int y = Integer.parseInt(locS[1]);
		int z = Integer.parseInt(locS[2]);
		World world = Bukkit.getWorld(locS[3]);
		Location loc = new Location(world, x, y, z);
		map.put("Location", loc);
		
		map.putAll(getValues());
		return map;
	}
	
	public boolean hasEventLogged(){
		String value = get(String.class, DATE);
		return (value != null);
	}
	
	public UUID getUUID(){
		return UUID;
	}
	
	public static List<EventSaver> getEvents(UUID uuid){
		List<EventSaver> list = new ArrayList<>();
		SUPPORTED_CLASSES.stream().forEach(c ->{
			try {
				EventSaver saver = c.getConstructor(uuid.getClass()).newInstance(uuid);
				if(saver.hasEventLogged()){
					list.add(saver);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return list;
	}

}
