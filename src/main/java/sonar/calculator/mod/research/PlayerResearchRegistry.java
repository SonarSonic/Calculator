package sonar.calculator.mod.research;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerResearchRegistry {

	private static LinkedHashMap<String, ArrayList<IResearch>> research = new LinkedHashMap<String, ArrayList<IResearch>>();

	public static void clearNetworks() {
		research.clear();
	}

	public void saveResearch(NBTTagCompound tag){
		for()
	}
	
	public IResearch getSpecificResearch(EntityPlayer player, String researchName) {
		ArrayList<IResearch> playerResearch = getPlayerResearch(player);
		for (IResearch research : playerResearch) {
			if (research.getName() == researchName) {
				return research;
			}
		}
		return null;
	}

	public ArrayList<IResearch> getPlayerResearch(EntityPlayer player) {
		String name = player.getGameProfile().getName();
		if (name != null) {
			if (research.get(name) == null) {
				research.put(name, new ArrayList());
			}
			return research.get(name);
		}
		return new ArrayList();
	}
}
