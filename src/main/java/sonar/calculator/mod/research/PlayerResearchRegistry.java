package sonar.calculator.mod.research;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.research.types.ResearchTypes;
import sonar.core.helpers.NBTHelper.SyncType;

public class PlayerResearchRegistry {

	private static LinkedHashMap<UUID, ArrayList<IResearch>> research = new LinkedHashMap<UUID, ArrayList<IResearch>>();
	public static final String UUID = "uuid", LIST = "list", RESEARCH = "rese";

	public static void writeData(NBTTagCompound nbt, SyncType type) {
		NBTTagList research = new NBTTagList();
		for (Entry<UUID, ArrayList<IResearch>> entry : PlayerResearchRegistry.research.entrySet()) {
			NBTTagCompound playerTag = new NBTTagCompound();
			playerTag.setUniqueId(UUID, entry.getKey());
			writePlayerData(entry.getValue(), playerTag, type);
			research.appendTag(playerTag);
		}
		if (!research.hasNoTags()) {
			nbt.setTag(LIST, research);
		}
	}

	public static void writePlayerData(EntityPlayer player, NBTTagCompound nbt, SyncType type) {
		ArrayList<IResearch> playerResearch = new ArrayList();
		if (research.get(player.getName()) != null) {
			playerResearch = research.get(player.getName());
		}
		writePlayerData(playerResearch, nbt, type);
	}

	public static void writePlayerData(ArrayList<IResearch> research, NBTTagCompound nbt, SyncType type) {
		NBTTagList researchList = new NBTTagList();
		for (IResearch r : research) {			
			NBTTagCompound researchTag = new NBTTagCompound();
			researchTag.setString(RESEARCH, r.getName());
			r.writeData(researchTag, type);
			if (!researchTag.hasNoTags())
				researchList.appendTag(researchTag);
		}
		if (!researchList.hasNoTags())
			nbt.setTag(LIST, researchList);
	}

	public static void readData(NBTTagCompound nbt, SyncType type) {
		NBTTagList research = nbt.getTagList(LIST, 10);
		for (int i = 0; i < research.tagCount(); i++) {
			NBTTagCompound playerTag = research.getCompoundTagAt(i);
			if (playerTag != null) {
				PlayerResearchRegistry.research.put(playerTag.getUniqueId(UUID), readPlayerData(playerTag, type));
			}
		}
	}

	public static ArrayList<IResearch> readPlayerData(NBTTagCompound nbt, SyncType type) {
		ArrayList<IResearch> playerResearch = new ArrayList<IResearch>();
		if (nbt.hasKey(RESEARCH)) {
			NBTTagList researchList = nbt.getTagList(RESEARCH, 10);
			for (int j = 0; j < researchList.tagCount(); j++) {
				NBTTagCompound researchTag = researchList.getCompoundTagAt(j);
				String researchType = researchTag.getString("researchType");
				IResearch rtype = Calculator.research.getRegisteredObject(researchType);
				if (rtype != null) {
					IResearch toAdd = rtype.getInstance();
					toAdd.readData(researchTag, type);
					playerResearch.add(toAdd);
				}
			}
		}
		return playerResearch;
	}

	public static IResearch getSpecificResearch(UUID uuid, ResearchTypes researchType) {
		ArrayList<IResearch> playerResearch = getPlayerResearch(uuid);
		for (IResearch research : playerResearch) {
			if (research.getName().equals(researchType.name())) {
				return research;
			}
		}
		IResearch type = Calculator.research.getRegisteredObject(researchType.name());
		if (type != null) {
			research.get(uuid).add(type.getInstance());
			return getSpecificResearch(uuid, researchType);
		}
		return null;
	}

	public static ArrayList<IResearch> getPlayerResearch(UUID uuid) {
		if (uuid != null) {
			if (research.get(uuid) == null) {
				research.put(uuid, new ArrayList());
			}
			return research.get(uuid);
		}
		return new ArrayList();
	}
}
