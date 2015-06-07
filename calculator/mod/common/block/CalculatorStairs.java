package sonar.calculator.mod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class CalculatorStairs extends BlockStairs {

	public CalculatorStairs(Block block, int par) {
		super(block, par);
        this.setLightOpacity(0);
	}
	
}
