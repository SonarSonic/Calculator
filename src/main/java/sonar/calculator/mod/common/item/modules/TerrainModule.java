package sonar.calculator.mod.common.item.modules;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import sonar.calculator.mod.CalculatorConfig;

public class TerrainModule extends BaseTerrainModule {

	public TerrainModule() {
		super(CalculatorConfig.getInteger("Terrain Module"),CalculatorConfig.getInteger("Terrain Module"),CalculatorConfig.getInteger("Terrain Module"));
		super.replacable = new Block[]{ Blocks.GRASS, Blocks.DIRT, Blocks.STONE};
		maxStackSize = 1;
	}
}
