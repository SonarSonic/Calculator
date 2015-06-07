package sonar.calculator.mod.common.block.decoration;

import net.minecraft.block.Block;
import sonar.calculator.mod.Calculator;

public class TanzaniteBlock extends Block
{
  public TanzaniteBlock()
  {
    super(net.minecraft.block.material.Material.rock);
    setBlockName("TanzaniteBlock");
    setHardness(1.0F);
    setCreativeTab(Calculator.Calculator);
    setBlockTextureName("Calculator:tanzanite_block");
  }
}
