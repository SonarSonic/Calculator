package sonar.calculator.mod.common.block.decoration;

import net.minecraft.block.Block;
import sonar.calculator.mod.Calculator;

public class WeakenedBlock extends Block
{
  public WeakenedBlock()
  {
    super(net.minecraft.block.material.Material.rock);
    setBlockName("WeakenedBlock");
    setHardness(1.0F);
    setCreativeTab(Calculator.Calculator);
    setBlockTextureName("Calculator:weakened_diamond_block");
  }
}
