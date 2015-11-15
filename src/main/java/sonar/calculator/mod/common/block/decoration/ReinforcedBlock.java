package sonar.calculator.mod.common.block.decoration;

import net.minecraft.block.Block;
import sonar.calculator.mod.Calculator;

public class ReinforcedBlock extends Block
{
  public ReinforcedBlock()
  {
    super(net.minecraft.block.material.Material.rock);
    setBlockName("ReinforcedBlock");
    setHardness(1.0F);
    setCreativeTab(Calculator.Calculator);
    setBlockTextureName("Calculator:reinforced_iron_block");
  }
}
