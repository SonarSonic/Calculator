package sonar.calculator.mod.network;

import gnu.trove.map.hash.THashMap;

import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import sonar.calculator.mod.common.recipes.RecipeRegistry.CalculatorRecipes;

public class PlayerResearch implements IExtendedEntityProperties {

	public final static String tagName = "Calculator - Research";

	private final EntityPlayer player;

	public Map<Integer, Integer> unblocked = new THashMap<Integer, Integer>();

	public PlayerResearch(EntityPlayer player) {
		this.player = player;
	}

	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(PlayerResearch.tagName, new PlayerResearch(player));
	}

	public static final PlayerResearch get(EntityPlayer player) {
		return (PlayerResearch) player.getExtendedProperties(tagName);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		CalculatorRecipes.instance().writeToNBT(properties, unblocked, "unblocked");
		compound.setTag(tagName, properties);

	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(tagName);
		this.unblocked = CalculatorRecipes.instance().readFromNBT(properties, "unblocked");
	}

	@Override
	public void init(Entity entity, World world) {
	}

	public void copy(PlayerResearch props) {
		this.unblocked = props.unblocked;
	}

}
