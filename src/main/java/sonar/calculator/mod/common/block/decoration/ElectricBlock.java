package sonar.calculator.mod.common.block.decoration;

import net.minecraft.block.Block;
import sonar.calculator.mod.Calculator;

public class ElectricBlock extends Block {
	
	public ElectricBlock() {
		super(net.minecraft.block.material.Material.rock);
		setBlockName("ElectricBlock");
		setHardness(1.0F);
		setCreativeTab(Calculator.Calculator);
		setBlockTextureName("Calculator:electric_diamond_block");
	}
}
