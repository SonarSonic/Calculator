package sonar.calculator.mod.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.CalculatorAPI;

/** Uses the config BlackList file to create a Map which can be easily accessed */
public class AtomicMultiplierBlacklist {
	private static final AtomicMultiplierBlacklist blacklist = new AtomicMultiplierBlacklist();

	private Map bannedList = new HashMap();

	public static AtomicMultiplierBlacklist blacklist() {
		return blacklist;
	}

	public AtomicMultiplierBlacklist() {
		String[] blacklisted = CalculatorConfig.atomicblackList.getStringList();
		for (int i = 0; i < blacklisted.length; i++) {
			String[] parts = blacklisted[i].split(":");			
			Item itemBan = Item.REGISTRY.getObject(new ResourceLocation(parts[0], parts[1]));
			if (itemBan != null) {
				addBan(itemBan);
			} else {
				Block blockBan = Block.REGISTRY.getObject(new ResourceLocation(parts[0], parts[1]));
				if (blockBan != null) {
					addBan(blockBan);
				}
			}
		}
		List<ResourceLocation> apiBlocked = CalculatorAPI.getItemBlackList();
		for (ResourceLocation item : apiBlocked) {		
			Item itemBan; Block blockBan;
			if ((itemBan = Item.REGISTRY.getObject(item)) != null) {
				addBan(itemBan);
			} else if ((blockBan = Block.REGISTRY.getObject(item)) != null) {
				addBan(blockBan);
			}
		}
	}

	public void addBan(Block input) {
		addBan(Item.getItemFromBlock(input));
	}

	public void addBan(Item input) {
		this.bannedList.put(input, false);
	}

	public boolean isAllowed(Item item) {
		List<String> apiBlocked = CalculatorAPI.getModBlackList();
		for (String modid : apiBlocked) {
			if (item.getRegistryName().getResourceDomain().equals(modid)) {
				return false;
			}
		}
		Iterator iterator = this.bannedList.entrySet().iterator();

		Map.Entry entry;
		do {
			if (!iterator.hasNext()) {
				return true;
			}

			entry = (Map.Entry) iterator.next();
		} while (!(item == (Item) entry.getKey()));

		return (Boolean) entry.getValue();
	}
	public Map getSmeltingList() {
		return this.bannedList;
	}

}
