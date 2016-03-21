package sonar.calculator.mod.common.block.generators;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.helpers.SonarHelper;

public class InvisibleBlock extends Block implements IDismantleable {

	public int type;

	public InvisibleBlock(int type) {
		super(SonarMaterials.machine);
		this.type = type;
		if (type == 2) {
			this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
		}
		if (type == 0) {
			this.setBlockBounds(0.20F, 0.0F, 0.20F, 0.80F, 1.0F, 0.80F);
		}
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			if (!world.isRemote) {
				if (type == 0) {
					if (world.getBlock(x, y - 1, z) == Calculator.conductorMast) {

						player.openGui(Calculator.instance, CalculatorGui.ConductorMast, world, x, y - 1, z);
					} else if (world.getBlock(x, y - 2, z) == Calculator.conductorMast) {

						player.openGui(Calculator.instance, CalculatorGui.ConductorMast, world, x, y - 2, z);
					} else if (world.getBlock(x, y - 3, z) == Calculator.conductorMast) {

						player.openGui(Calculator.instance, CalculatorGui.ConductorMast, world, x, y - 3, z);
					}
				}
			}
		}
		return true;
	}

	@Override
	public final boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		if (this.type == 0) {
			if (world.getBlock(x, y - 1, z) == Calculator.conductorMast) {
				TileEntityConductorMast mast = (TileEntityConductorMast) world.getTileEntity(x, y - 1, z);
				SonarHelper.dropTile(player, world.getBlock(x, y - 1, z), world, x, y - 1, z);
			} else if (world.getBlock(x, y - 2, z) == Calculator.conductorMast) {
				TileEntityConductorMast mast = (TileEntityConductorMast) world.getTileEntity(x, y - 2, z);
				SonarHelper.dropTile(player, world.getBlock(x, y - 2, z), world, x, y - 2, z);
			} else if (world.getBlock(x, y - 3, z) == Calculator.conductorMast) {
				TileEntityConductorMast mast = (TileEntityConductorMast) world.getTileEntity(x, y - 3, z);
				SonarHelper.dropTile(player, world.getBlock(x, y - 3, z), world, x, y - 3, z);
			}
		} else if (this.type == 1) {
			for (int X = -1; X < 2; X++) {
				for (int Z = -1; Z < 2; Z++) {
					if (world.getBlock(x + X, y - 1, z + Z) == Calculator.weatherStation) {
						TileEntity i = world.getTileEntity(x + X, y - 1, z + Z);
						Block bi = world.getBlock(x + X, y - 1, z + Z);
						bi.dropBlockAsItem(world, x + X, y - 1, z + Z, world.getBlockMetadata(x + X, y - 1, z + Z), 0);
						world.setBlockToAir(x + X, y - 1, z + Z);
					}
				}
			}
		} else if (this.type == 2) {
			if (world.getBlock(x, y - 1, z) == Calculator.transmitter) {
				Block bi = world.getBlock(x, y - 1, z);
				bi.dropBlockAsItem(world, x, y - 1, z, world.getBlockMetadata(x, y - 1, z), 0);
				world.setBlockToAir(x, y - 1, z);

			}
		}
		return super.removedByPlayer(world, player, x, y, z, willHarvest);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block oldblock, int oldMetadata) {
		if (type == 0) {
			TileEntityConductorMast.setWeatherStationAngles(true, world, x, y, z);
		}
	}

	@Override
	public int quantityDropped(Random p_149745_1_) {
		return 0;
	}

	@Override
	public Item getItem(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public ArrayList<ItemStack> dismantleBlock(EntityPlayer player, World world, int x, int y, int z, boolean returnDrops) {

		SonarHelper.dropTile(player, world.getBlock(x, y, z), world, x, y, z);
		return null;
	}

	@Override
	public boolean canDismantle(EntityPlayer player, World world, int x, int y, int z) {
		return true;
	}
}
