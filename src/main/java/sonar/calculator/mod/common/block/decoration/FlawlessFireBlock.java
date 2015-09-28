package sonar.calculator.mod.common.block.decoration;

import net.minecraft.block.Block;
import sonar.calculator.mod.Calculator;

public class FlawlessFireBlock extends Block
{
  public FlawlessFireBlock()
  {
    super(net.minecraft.block.material.Material.rock);
    setBlockName("FlawlessFireBlock");
    setHardness(1.0F);
    setCreativeTab(Calculator.Calculator);
    setBlockTextureName("Calculator:flawless_fire_block");
  }

}
