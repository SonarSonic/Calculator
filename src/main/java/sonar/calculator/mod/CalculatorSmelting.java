package sonar.calculator.mod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CalculatorSmelting extends Calculator implements IFuelHandler {

	public static void addSmeltingRecipes() {
		addSmelting(enrichedGold, new ItemStack(enrichedgold_ingot), 0.8F);
		addSmelting(broccoli, new ItemStack(cookedBroccoli), 0.2F);
		//addSmelting(firediamond, new ItemStack(electricDiamond), 1.0F);
	}

	public static void addSmelting(Item input, ItemStack output, float xp) {
		if (input != null && output != null && CalculatorConfig.isEnabled(output)) {
			GameRegistry.addSmelting(input, output, xp);
		}
	}

	@Override
	public int getBurnTime(ItemStack fuel) {
		if(fuel.isEmpty()){
			return 0;
		}
        if (fuel.getItem() == enriched_coal)
			return 5000;
		if (fuel.getItem() == coal_dust)
			return 1000;
		if (fuel.getItem() == purified_coal)
			return 10000;
		if (fuel.getItem() == firecoal)
			return 25000;
		if (fuel.getItem() == firediamond)
			return 160000;
		if (fuel.getItem() == controlled_Fuel)
			return 80000;

		return 0;
	}
}
