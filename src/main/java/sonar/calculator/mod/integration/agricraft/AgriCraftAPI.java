package sonar.calculator.mod.integration.agricraft;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

public class AgriCraftAPI {
	private static AgriCraftAPI INSTANCE;

	protected AgriCraftAPI() {
	}

	public static AgriCraftAPI getInstance() {
		if (INSTANCE == null) {
			if (!isLoaded()) {
				INSTANCE = new AgriCraftAPI();
			} else {
				INSTANCE = new AgriCraftAPIWrapper();
			}
		}
		return INSTANCE;
	}

	public static boolean isLoaded() {
		return Loader.isModLoaded("AgriCraft");
	}

	public boolean isHandledByAgricraft(ItemStack seed) {
		return false;
	}

	public boolean isPlantingDisabled(ItemStack stack) {
		return false;
	}

	public boolean isCrops(World world, int x, int y, int z) {
		return false;
	}

	public boolean canGrow(World world, int x, int y, int z) {
		return false;
	}

	public boolean canPlaceCrops(World world, int x, int y, int z, ItemStack crops) {
		return false;
	}

	public boolean placeCrops(World world, int x, int y, int z, ItemStack crops) {
		return false;
	}

	public boolean canApplySeeds(World world, int x, int y, int z, ItemStack seed) {
		return false;
	}

	public List<ItemStack> harvest(World world, int x, int y, int z) {
		return null;
	}

	public boolean removeWeeds(World world, int x, int y, int z, boolean byHand) {
		return false;
	}

	public boolean isMature(World world, int x, int y, int z) {
		return false;
	}

	public boolean applySeeds(World world, int x, int y, int z, ItemStack seed) {
		return false;
	}

}
