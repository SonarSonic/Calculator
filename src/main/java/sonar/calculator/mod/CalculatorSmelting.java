package sonar.calculator.mod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class CalculatorSmelting extends Calculator implements IFuelHandler {

	public static void addSmeltingRecipes() {

		GameRegistry.addSmelting(enrichedgold, new ItemStack(enrichedgold_ingot), 0.8F);
		GameRegistry.addSmelting(broccoli, new ItemStack(CookedBroccoli), 0.2F);

	}

	public static void addSmelting(Item input, ItemStack output, float xp) {
		if (CalculatorConfig.isEnabled(output)) {
			GameRegistry.addSmelting(input, output, xp);
		}
	}

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel.getItem() == enriched_coal)
			return 5000;
		if (fuel.getItem() == coal_dust)
			return 1000;
		if (fuel.getItem() == purified_coal)
			return 10000;
		if (fuel.getItem() == firecoal)
			return 25000;
		if (fuel.getItem() == flawlessfirediamond)
			return 160000;
		if (fuel.getItem() == controlled_Fuel)
			return 80000;

		return 0;
	}
}
