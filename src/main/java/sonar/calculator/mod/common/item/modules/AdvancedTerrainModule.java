package sonar.calculator.mod.common.item.modules;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import sonar.calculator.mod.CalculatorConfig;

public class AdvancedTerrainModule extends BaseTerrainModule {

	public AdvancedTerrainModule() {
		super(CalculatorConfig.ADVANCED_TERRAIN_MODULE_STORAGE, CalculatorConfig.ADVANCED_TERRAIN_MODULE_USAGE);
		super.replacable = new Block[] { Blocks.GRASS, Blocks.DIRT, Blocks.STONE, Blocks.GRAVEL, Blocks.SAND, Blocks.COBBLESTONE };
		maxStackSize = 1;
	}
}
