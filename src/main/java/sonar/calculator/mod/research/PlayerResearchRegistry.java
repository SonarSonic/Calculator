package sonar.calculator.mod.research;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.research.types.ResearchTypes;
import sonar.core.api.nbt.INBTSyncable;
import sonar.core.helpers.NBTHelper.SyncType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;

public class PlayerResearchRegistry {

	private static LinkedHashMap<String, ArrayList<IResearch>> research = new LinkedHashMap<String, ArrayList<IResearch>>();

	public static void writeData(NBTTagCompound nbt, SyncType type) {
		NBTTagList research = new NBTTagList();
		for (Entry<String, ArrayList<IResearch>> entry : PlayerResearchRegistry.research.entrySet()) {
			NBTTagCompound playerTag = new NBTTagCompound();
			playerTag.setString("player", entry.getKey());
			writePlayerData(entry.getValue(), playerTag, type);
			research.appendTag(playerTag);
		}
		if (!research.hasNoTags()) {
			nbt.setTag("research", research);
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
			researchTag.setString("researchType", r.getName());
			r.writeData(researchTag, type);
			if (!researchTag.hasNoTags())
				researchList.appendTag(researchTag);

		}
		if (!researchList.hasNoTags())
			nbt.setTag("researchList", researchList);
	}

	public static void readData(NBTTagCompound nbt, SyncType type) {
		NBTTagList research = nbt.getTagList("research", 10);
		for (int i = 0; i < research.tagCount(); i++) {
			NBTTagCompound playerTag = research.getCompoundTagAt(i);
			if (playerTag != null) {
				PlayerResearchRegistry.research.put(playerTag.getString("player"), readPlayerData(playerTag, type));
			}
		}
	}

	public static ArrayList<IResearch> readPlayerData(NBTTagCompound nbt, SyncType type) {
		ArrayList<IResearch> playerResearch = new ArrayList<IResearch>();
		if (nbt.hasKey("researchList")) {
			NBTTagList researchList = nbt.getTagList("researchList", 10);
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

	public static IResearch getSpecificResearch(String player, ResearchTypes researchType) {
		ArrayList<IResearch> playerResearch = getPlayerResearch(player);
		for (IResearch research : playerResearch) {
			if (research.getName().equals(researchType.name())) {
				return research;
			}
		}
		IResearch type = Calculator.research.getRegisteredObject(researchType.name());
		if(type!=null){
			research.get(player).add(type.getInstance());
			return getSpecificResearch(player, researchType);
		}
		return null;
	}

	public static ArrayList<IResearch> getPlayerResearch(String name) {
		if (name != null) {
			if (research.get(name) == null) {
				research.put(name, new ArrayList());
			}
			return research.get(name);
		}
		return new ArrayList();
	}
}
