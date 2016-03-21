package sonar.calculator.mod.common.block;

import sonar.calculator.mod.Calculator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class CalculatorStairs extends BlockStairs {

	public CalculatorStairs(Block block) {
		super(block.getStateFromMeta(0));
		this.setLightOpacity(0);    
		this.useNeighborBrightness = true;
		this.setCreativeTab(Calculator.Calculator);
	}

}
