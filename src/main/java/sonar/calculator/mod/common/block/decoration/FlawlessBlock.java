package sonar.calculator.mod.common.block.decoration;

import net.minecraft.block.Block;
import sonar.calculator.mod.Calculator;

public class FlawlessBlock extends Block
{
  public FlawlessBlock()
  {
    super(net.minecraft.block.material.Material.rock);
    setBlockName("FlawlessBlock");
    setHardness(1.0F);
    setCreativeTab(Calculator.Calculator);
    setBlockTextureName("Calculator:flawless_block");
  }
}
