package sonar.calculator.mod.common.block.decoration;

import net.minecraft.block.Block;
import sonar.calculator.mod.Calculator;

public class AmethystBlock extends Block {
	
	public AmethystBlock() {
		super(net.minecraft.block.material.Material.rock);
		setBlockName("AmethystBlock");
		setHardness(1.0F);
		setCreativeTab(Calculator.Calculator);
		setBlockTextureName("Calculator:amethyst_block");
	}
}
