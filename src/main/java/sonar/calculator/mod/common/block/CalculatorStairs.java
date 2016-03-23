package sonar.calculator.mod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import sonar.calculator.mod.Calculator;

public class CalculatorStairs extends BlockStairs {

	public CalculatorStairs(Block block) {
		super(block.getStateFromMeta(0));
		this.setLightOpacity(0);    
		this.useNeighborBrightness = true;
		this.setCreativeTab(Calculator.Calculator);
	}

}
