package sonar.calculator.mod.common.recipes.crafting;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;

public class FlawlessCalculatorCraftingManager extends BaseManager {
	private static final FlawlessCalculatorCraftingManager instance = new FlawlessCalculatorCraftingManager();

	public static final FlawlessCalculatorCraftingManager getInstance() 
	{
		return instance;
	}

	private FlawlessCalculatorCraftingManager() {
		super(4,1, false);
		recipes = new ArrayList();

		//Calculator Recipes
		this.addRecipe(new ItemStack(Calculator.diamondSapling, 1),new Object[] {"BAAC",'A', Calculator.enddiamond, 'B',Calculator.PearSapling,'C', Blocks.end_stone});
		this.addRecipe(new ItemStack(Calculator.stablestoneBlock, 1),new Object[] { "AAAA", 'A', Calculator.reinforcedstoneBlock });
		this.addRecipe(new ItemStack(Calculator.fiddledewFruit, 1),new Object[] { "AAAA", 'A', Calculator.broccoli });
		this.addRecipe(new ItemStack(Calculator.purifiedobsidianBlock, 1),new Object[] { "AAAA", 'A', Blocks.obsidian });		
		this.addRecipe(new ItemStack(Calculator.flawlessGlass, 4),new Object[] { "BAAB", Character.valueOf('A'), Blocks.glass,'B', Calculator.flawlessdiamond });
		this.addRecipe(new ItemStack(Calculator.flawlessGlass, 4),new Object[] { "BAAB", Character.valueOf('A'),Blocks.stained_glass, 'B', Calculator.flawlessdiamond });	
		this.addRecipe(new ItemStack(Calculator.carbondioxideGenerator, 1),new Object[] { "BAAB", Character.valueOf('A'),new ItemStack(Calculator.circuitBoard, 1, 8), 'B', new ItemStack(Calculator.gas_lantern_off,1) });		
		this.addRecipe(new ItemStack(Calculator.controlled_Fuel, 1),new Object[] { "BAAB", Character.valueOf('A'),Calculator.enriched_coal, 'B', new ItemStack(Calculator.circuitBoard,1) });		
		this.addRecipe(new ItemStack(Calculator.itemLocatorModule, 1),new Object[] { "BAAB", Character.valueOf('A'),new ItemStack(Calculator.itemCalculator,1), 'B',new ItemStack(Calculator.itemEnergyModule,1) });		

		//minium stone recipes
		this.addRecipe(new ItemStack(Items.diamond, 1), new Object[] { "AAAA",'A', Items.gold_ingot });
		this.addRecipe(new ItemStack(Items.emerald, 1), new Object[] { "AAAA",'A', Items.diamond });
		this.addRecipe(new ItemStack(Items.ender_pearl, 1), new Object[] {"AAAA", 'A', Items.iron_ingot });
		this.addRecipe(new ItemStack(Blocks.obsidian, 1), new Object[] {"AAAA", 'A', Blocks.log });
		
		//extra recipes
		this.addRecipe(new ItemStack(Items.blaze_rod, 1), new Object[] {"AAAA", 'A', Items.blaze_powder });
		this.addRecipe(new ItemStack(Items.ghast_tear, 1), new Object[] {"AAAA", 'A', Items.blaze_rod });
		
		//hidden
		this.addShapelessRecipe(new ItemStack(Items.emerald, 1), new Object[] { Items.diamond, Items.diamond, Blocks.cactus, Blocks.cactus });
		
		
		Collections.sort(this.recipes, new AbstractRecipeSorter(this));
	}

}
