package sonar.calculator.mod.common.block.decoration;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EndBlock extends Block {
	public EndBlock() {
		super(net.minecraft.block.material.Material.rock);
		setBlockName("EndBlock");
		setHardness(1.0F);
		setCreativeTab(Calculator.Calculator);
		setBlockTextureName("Calculator:end_diamond_block");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		for (int l = 0; l < 3; ++l) {
			double d6 = x + rand.nextFloat();
			double d1 = y + rand.nextFloat();
			d6 = z + rand.nextFloat();
			double d3 = 0.0D;
			double d4 = 0.0D;
			double d5 = 0.0D;
			int i1 = rand.nextInt(2) * 2 - 1;
			int j1 = rand.nextInt(2) * 2 - 1;
			d3 = (rand.nextFloat() - 0.5D) * 0.125D;
			d4 = (rand.nextFloat() - 0.5D) * 0.125D;
			d5 = (rand.nextFloat() - 0.5D) * 0.125D;
			double d2 = z + 0.5D + 0.25D * j1;
			d5 = rand.nextFloat() * 1.0F * j1;
			double d0 = x + 0.5D + 0.25D * i1;
			d3 = rand.nextFloat() * 1.0F * i1;
			world.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
		}
	}
}
