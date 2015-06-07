package sonar.calculator.mod.common.block.decoration;

import net.minecraft.block.Block;
import sonar.calculator.mod.Calculator;

public class EnrichedBlock extends Block
{
  public EnrichedBlock()
  {
    super(net.minecraft.block.material.Material.rock);
    setBlockName("EnrichedBlock");
    setHardness(1.0F);
    setCreativeTab(Calculator.Calculator);
    setBlockTextureName("Calculator:enriched_gold_block");
  }
}
