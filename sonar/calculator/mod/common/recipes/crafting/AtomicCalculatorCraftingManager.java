package sonar.calculator.mod.common.recipes.crafting;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;

public class AtomicCalculatorCraftingManager extends BaseManager {
    private static final AtomicCalculatorCraftingManager instance = new AtomicCalculatorCraftingManager();
  
    public static final AtomicCalculatorCraftingManager getInstance()
    {
        return instance;
    }  

    private AtomicCalculatorCraftingManager(){
    	super(3,1, false);
    	recipes = new ArrayList();
 	    this.addRecipe(new ItemStack(Calculator.itemScientificCalculator, 1), new Object[] {"ABA", 'A', Items.iron_ingot, 'B', Items.redstone});
 	    this.addRecipe(new ItemStack(Calculator.flawlessdiamond, 1), new Object[] {"ABA", 'A', Items.diamond, 'B', Calculator.atomic_binder}); 	    
 	    this.addRecipe(new ItemStack(Calculator.flawlessfirediamond, 1), new Object[] {"ABA", 'A', Items.diamond, 'B', Items.blaze_rod});
 	    this.addRecipe(new ItemStack(Calculator.enddiamond, 1), new Object[] {"BAC", 'A', Calculator.electricdiamond, 'B', Blocks.end_stone, 'C', Blocks.obsidian});		    
 	    this.addRecipe(new ItemStack(Calculator.PearSapling, 1), new Object[] {"ABC", 'A', Calculator.AmethystSapling, 'B', Blocks.end_stone, 'C', Calculator.tanzaniteSapling});
	 	this.addRecipe(new ItemStack(Calculator.itemAdvancedTerrainModule, 1), new Object[] {"ABC", 'A', Calculator.itemScientificCalculator, 'B', Calculator.atomic_binder, 'C', Calculator.redstone_ingot});
	 	this.addRecipe(new ItemStack(Calculator.tanzaniteSapling, 1), new Object[] {"ACB", 'A', Calculator.large_tanzanite, 'C', Calculator.atomic_binder, 'B', Blocks.sapling});
	 	this.addRecipe(new ItemStack(Calculator.tanzaniteSapling, 1), new Object[] {"ACB", 'A', Calculator.large_tanzanite, 'C', Calculator.atomic_binder, 'B', Blocks.leaves});
	 	this.addRecipe(new ItemStack(Calculator.tanzaniteSapling, 1), new Object[] {"ACB", 'A', Calculator.large_tanzanite, 'C', Calculator.atomic_binder, 'B', Blocks.leaves2});
	 	this.addRecipe(new ItemStack(Calculator.tanzaniteSapling, 1), new Object[] {"ACB", 'A', Calculator.tanzaniteLog, 'C', Calculator.atomic_binder, 'B', Calculator.tanzaniteLeaf});
	 	this.addRecipe(new ItemStack(Calculator.tanzaniteSapling, 1), new Object[] {"ACB", 'A', Calculator.tanzaniteLog, 'C', Calculator.atomic_binder, 'B', Calculator.small_tanzanite});
	 	this.addRecipe(new ItemStack(Calculator.tanzaniteSapling, 1), new Object[] {"ACB", 'A', Calculator.tanzaniteLog, 'C', Calculator.atomic_binder, 'B', Calculator.tanzaniteLog});
	 	this.addRecipe(new ItemStack(Calculator.itemNutritionModule, 1), new Object[] {"ACB", 'A', Calculator.healthprocessor, 'C', Calculator.itemEnergyModule, 'B', Calculator.hungerprocessor});
	 	this.addRecipe(new ItemStack(Calculator.speedUpgrade, 1), new Object[] {"ACB", 'A', new ItemStack(Calculator.circuitBoard,1, 4), 'C', Calculator.atomic_binder, 'B', Calculator.enrichedgold});
	 	this.addRecipe(new ItemStack(Calculator.energyUpgrade, 1), new Object[] {"ACB", 'A', new ItemStack(Calculator.circuitBoard,1, 5), 'C', Calculator.atomic_binder, 'B', Calculator.enrichedgold});
	 	this.addRecipe(new ItemStack(Calculator.voidUpgrade, 1), new Object[] {"ACB", 'A', new ItemStack(Calculator.circuitBoard,1, 6), 'C', Calculator.atomic_binder, 'B', Calculator.enrichedgold});
	 	this.addRecipe(new ItemStack(Calculator.glowstoneextractor, 1), new Object[] {"ACB", 'A', new ItemStack(Calculator.circuitBoard,1, 9), 'C', Calculator.redstoneextractor, 'B', Calculator.large_tanzanite});
	 	this.addRecipe(new ItemStack(Calculator.redstoneextractor, 1), new Object[] {"ACB", 'A', new ItemStack(Calculator.redstone_ingot), 'C', Calculator.starchextractor, 'B', Calculator.redstone_ingot});
	 	this.addRecipe(new ItemStack(Calculator.conductorMast, 1), new Object[] {"ABA", 'A', Calculator.itemEnergyModule, 'B', Calculator.flawlessfirediamond});
	 	this.addRecipe(new ItemStack(Calculator.storageChamber, 1), new Object[] {"ABA", 'A', Calculator.reinforced_iron_block, 'B', Blocks.chest});
	 	this.addRecipe(new ItemStack(Calculator.processingChamber, 1), new Object[] {"ABC", 'A', Calculator.reassemblyChamber, 'B', Calculator.flawlessdiamond , 'C', Calculator.restorationChamber});
	 	this.addRecipe(new ItemStack(Calculator.itemStorageModule, 1), new Object[] {"ABA", 'A', Calculator.reinforcediron_ingot, 'B', Blocks.chest});
	 	this.addRecipe(new ItemStack(Calculator.transmitter, 1), new Object[] {"ABA", 'A', Calculator.reinforcediron_ingot, 'B', Calculator.electricdiamond});
		this.addRecipe(new ItemStack(Calculator.weatherStation, 4), new Object[] {"ABA", 'A', Calculator.reinforcediron_ingot, 'B', Calculator.flawlessfirediamond});
		this.addRecipe(new ItemStack(Calculator.weatherController, 1), new Object[] {"ABA", 'A', Calculator.electricdiamond, 'B', Calculator.rainSensor});

	 	Collections.sort(this.recipes, new AbstractRecipeSorter(this));
    }

}
