package ModLog.Logging.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ModLog.ModLogPlugin;
import ModLog.Logging.Saver;
import ModLog.Logging.Event.Loggers.TeleportLog;

public abstract class EventReader extends Saver {
	
	public static final List<Class<? extends EventReader>> SUPPORTED_CLASSES = Arrays.asList(
		TeleportLog.TeleportReader.class
	);
	
	UUID UUID;
	
	protected abstract Map<String, Object> getValues();
	
	public EventReader(UUID uuid) {
		super(ModLogPlugin.EVENT_LOG);
		UUID = uuid;
	}

	public Map<String, Object> getData(){
		Map<String, Object> map = new HashMap<>();
		
		String[] date = get(String.class, EventSaver.DATE).split("/");
		LocalDate localDate = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
		map.put("Date", localDate);
		
		String[] time = get(String.class, EventSaver.TIME).split(":");
		LocalTime localTime = LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
		map.put("Time", localTime);
		
		//TODO others needed
		
		map.putAll(getValues());
		return map;
		
	}
	
	public UUID getUUID(){
		return UUID;
	}

}
