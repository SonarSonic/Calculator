package sonar.calculator.mod.common.recipes.crafting;

import java.util.Collections;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.Calculator;

public class ManipulationCraftingManager extends BaseManager {
	private static final ManipulationCraftingManager instance = new ManipulationCraftingManager();

	public static final ManipulationCraftingManager getInstance() {
		return instance;
	}

	public ManipulationCraftingManager() {
		super(14, 1, true);
		addShapelessRecipe(new ItemStack(Calculator.atomic_assembly, 1),
				new Object[] { circuit(0, 1), circuit(1, 1), circuit(2, 1),
						circuit(3, 1), circuit(4, 1), circuit(5, 1),
						circuit(6, 1), circuit(7, 1), circuit(8, 1),
						circuit(9, 1), circuit(10, 1), circuit(11, 1),
						circuit(12, 1), circuit(13, 1) });
		
		addShapelessRecipe(new ItemStack(Calculator.atomic_module, 1),
				new Object[] { circuit(8, 1), circuit(8, 1), circuit(8, 1),
						circuit(8, 1), circuit(8, 1), circuit(8, 1),
						circuit(8, 1), circuit(8, 1), circuit(8, 1),
						circuit(8, 1), circuit(8, 1), circuit(8, 1),
						circuit(8, 1), circuit(8, 1) });
		
		addShapelessRecipe(new ItemStack(Calculator.atomic_binder, 1),
				new Object[] { circuit(3, 0),circuit(3, 0)});
		
		addShapelessRecipe(new ItemStack(Calculator.calculator_assembly, 1),
				new Object[] { circuit(0, 0)});
		
		addShapelessRecipe(new ItemStack(Calculator.calculator_screen, 1),
				new Object[] { circuit(1, 0)});

		Collections.sort(this.recipes, new AbstractRecipeSorter(this));
	}

	/**
	 * @param i
	 *            = damage value
	 * @param t
	 *            = type (1=Stable, 2=Damage, 3=Dirty else normal
	 */
	public ItemStack circuit(int i, int t) {
		ItemStack circuit = new ItemStack(circuitType(t), i);
		if (t == 1) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("Stable", 1);
			circuit.setTagCompound(tag);
		}
		return circuit;

	}

	public Item circuitType(int t) {
		switch (t) {
		case 2:
			return Calculator.circuitDamaged;
		case 3:
			return Calculator.circuitDirty;
		}
		return Calculator.circuitBoard;

	}
}
