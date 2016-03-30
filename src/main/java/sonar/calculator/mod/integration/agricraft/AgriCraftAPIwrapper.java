package sonar.calculator.mod.integration.agricraft;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.InfinityRaider.AgriCraft.api.API;
import com.InfinityRaider.AgriCraft.api.APIBase;
import com.InfinityRaider.AgriCraft.api.v1.APIv1;

public class AgriCraftAPIWrapper extends AgriCraftAPI {
    private APIv1 api;

    protected AgriCraftAPIWrapper() {
        super();
        APIBase api = API.getAPI(1);
        if (api instanceof APIv1) {
            this.api = (APIv1) api;
        }
        
    }

    public boolean isOk() {
        return api != null;
    }

    @Override
    public boolean isPlantingDisabled(ItemStack stack) {
        return isOk() && api.isNativePlantingDisabled(stack);
    }
    

    @Override
	public boolean isHandledByAgricraft(ItemStack seed) {
		return isOk() && api.isHandledByAgricraft(seed);
	}

    @Override
	public boolean isCrops(World world, int x, int y, int z) {
		return isOk() && api.isCrops(world, x, y, z);
	}

    @Override
	public boolean canGrow(World world, int x, int y, int z) {
		return isOk() && api.canGrow(world, x, y, z);
	}

    @Override
	public boolean canPlaceCrops(World world, int x, int y, int z, ItemStack crops) {
		return isOk() && api.canPlaceCrops(world, x, y, z, crops);
	}

    @Override
	public boolean placeCrops(World world, int x, int y, int z, ItemStack crops) {
		return isOk() && api.placeCrops(world, x, y, z, crops);
	}

    @Override
	public boolean canApplySeeds(World world, int x, int y, int z, ItemStack seed) {
		return isOk() && api.canPlaceCrops(world, x, y, z, seed);
	}

    @Override
	public List<ItemStack> harvest(World world, int x, int y, int z) {
		return isOk() ? api.harvest(world, x, y, z) : null;
	}

    @Override
	public boolean removeWeeds(World world, int x, int y, int z, boolean byHand) {
		return isOk() && api.removeWeeds(world, x, y, z, byHand);
	}

    @Override
	public boolean isMature(World world, int x, int y, int z) {
		return isOk() && api.isMature(world, x, y, z);
	}

    @Override
	public boolean applySeeds(World world, int x, int y, int z, ItemStack seed) {
		return isOk() && api.applySeeds(world, x, y, z, seed);
	}
}
