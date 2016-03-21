package sonar.calculator.mod.common.item.modules;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import sonar.calculator.mod.CalculatorConfig;

public class AdvancedTerrainModule extends BaseTerrainModule {

	public AdvancedTerrainModule() {
		super.replacable = new Block[] { Blocks.grass, Blocks.dirt, Blocks.stone, Blocks.gravel, Blocks.sand, Blocks.cobblestone };
		maxStackSize=1;	
		capacity = CalculatorConfig.getInteger("Advanced Terrain Module");
		maxReceive = CalculatorConfig.getInteger("Advanced Terrain Module");
		maxExtract = CalculatorConfig.getInteger("Advanced Terrain Module");
		maxTransfer = CalculatorConfig.getInteger("Advanced Terrain Module");
	}

}
