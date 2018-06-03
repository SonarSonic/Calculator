package sonar.calculator.mod.common.item.modules;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import sonar.calculator.mod.CalculatorConfig;

public class TerrainModule extends BaseTerrainModule {

	public TerrainModule() {
		super(CalculatorConfig.TERRAIN_MODULE_STORAGE, CalculatorConfig.TERRAIN_MODULE_USAGE);
		super.replacable = new Block[]{ Blocks.GRASS, Blocks.DIRT, Blocks.STONE};
		maxStackSize = 1;
	}
}
