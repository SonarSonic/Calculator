package sonar.calculator.mod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class CalculatorPlanks extends Block {

	public CalculatorPlanks() {
		super(Material.WOOD);
		setHarvestLevel("axe", 0);
		setHardness(0.7F);
	}
}
