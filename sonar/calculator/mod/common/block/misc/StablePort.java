package sonar.calculator.mod.common.block.misc;

import java.util.Random;

import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.ILocatorBlock;
import sonar.calculator.mod.api.IWrench;
import sonar.calculator.mod.api.IWrenchable;
import sonar.calculator.mod.common.tileentity.misc.TileEntityStablePort;
import sonar.core.utils.SonarMaterials;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class StablePort extends BlockContainer implements IWrenchable, ILocatorBlock {

	public StablePort() {
		super(SonarMaterials.machine);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityStablePort();
	}

	public Item getItemDropped(int m, Random rand, int fortune) {
		return Item.getItemFromBlock(Calculator.stablestoneBlock);
	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		if (!world.isRemote) {
			TileEntity target = world.getTileEntity(x, y, z);
			if (target != null && target instanceof TileEntityStablePort) {
				TileEntityStablePort port = (TileEntityStablePort) target;
				port.findLocator();
			}
		}
	}

	@Override
	public boolean canWrench() {
		return false;
	}

	@Override
	public void onWrench(World world, int x, int y, int z) {
		world.setBlock(x, y, z, Calculator.stablestoneBlock);
		world.removeTileEntity(x, y, z);
	}
}
