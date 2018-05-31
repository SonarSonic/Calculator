package sonar.calculator.mod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CalculatorSmelting extends Calculator {

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
}
