package ModLog.Logging.Event.Loggers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

import ModLog.Logging.Event.EventSaver;

public class DropItemLog extends EventSaver {

	static final String[] ITEM_TYPE = {"ItemType"};
	static final String[] ITEM_AMOUNT = {"ItemAmount"};
	static final String[] ITEM_LOCATION = {"ItemLocation"};
	
	public DropItemLog(java.util.UUID uuid) {
		super(uuid);
	}

	@Override
	protected Map<String, Object> getValues() {
		Map<String, Object> map = new HashMap<>();
		Material material = Material.valueOf(get(String.class, ITEM_TYPE));
		map.put("Item type", material);
		
		int amount = get(Integer.class, ITEM_AMOUNT);
		map.put("Item amount", amount);
		
		String[] locS = get(String.class, ITEM_LOCATION).split(",");
		int x = Integer.parseInt(locS[0]);
		int y = Integer.parseInt(locS[1]);
		int z = Integer.parseInt(locS[2]);
		World world = Bukkit.getWorld(locS[3]);
		Location loc = new Location(world, x, y, z);
		map.put("Item location", loc);
		
		return map;
	}

	@Override
	protected void set(PlayerEvent event2) {
		PlayerDropItemEvent event = (PlayerDropItemEvent)event2;
		Item item = event.getItemDrop();
		Location loc = item.getLocation();
		ItemStack stack = item.getItemStack();
		set(stack.getType().name(), ITEM_TYPE);
		set(stack.getAmount(), ITEM_AMOUNT);
		set(loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + "," + loc.getWorld().getName(), ITEM_LOCATION);
		
	}

}
