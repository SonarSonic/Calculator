package sonar.calculator.mod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CalculatorPlanks extends Block {

	public CalculatorPlanks() {
		super(Material.wood);
		setHarvestLevel("axe", 0);
		setHardness(0.7F);
	}
}
