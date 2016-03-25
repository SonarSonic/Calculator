package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.item.misc.UpgradeCircuit;
import sonar.calculator.mod.common.tileentity.machines.TileEntityDockingStation;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.BlockInteractionType;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.helpers.FontHelper;

public class DockingStation extends SonarMachineBlock {

	public DockingStation() {
		super(SonarMaterials.machine, true, true);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.95F, 1.0F);
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (player != null) {
			if (interact.type == BlockInteractionType.RIGHT) {
				if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof UpgradeCircuit) {
					return false;
				}
				if (!insertCalculator(player, world, x, y, z)) {
					if (!world.isRemote) {
						if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityDockingStation) {
							TileEntityDockingStation station = (TileEntityDockingStation) world.getTileEntity(x, y, z);
							if (station.isCalculator(station.calcStack) != 0) {
								player.openGui(Calculator.instance, IGuiTile.ID, world, x, y, z);
							} else {
								FontHelper.sendMessage(FontHelper.translate("docking.noCalculator"), world, player);
							}
						}
					}
				}
			}

		}
		return true;
	}

	public boolean insertCalculator(EntityPlayer player, World world, int x, int y, int z) {
		if (player.getHeldItem() != null && TileEntityDockingStation.isCalculator(player.getHeldItem()) > 0) {
			if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityDockingStation) {
				TileEntityDockingStation station = (TileEntityDockingStation) world.getTileEntity(x, y, z);
				if (station.getStackInSlot(0) == null) {
					station.calcStack = player.getHeldItem().copy();
					player.getHeldItem().stackSize--;
					return true;
				}
			}

		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityDockingStation();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block oldblock, int oldMetadata) {

		TileEntityDockingStation entity = (TileEntityDockingStation) world.getTileEntity(x, y, z);

		ItemStack itemstack = entity.calcStack;

		if (itemstack != null) {
			float f = this.rand.nextFloat() * 0.8F + 0.1F;
			float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
			float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

			while (itemstack.stackSize > 0) {
				int j = this.rand.nextInt(21) + 10;

				if (j > itemstack.stackSize) {
					j = itemstack.stackSize;
				}

				itemstack.stackSize -= j;

				EntityItem item = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

				if (itemstack.hasTagCompound()) {
					item.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
				}

				world.spawnEntityInWorld(item);
			}
		}

		super.breakBlock(world, x, y, z, oldblock, oldMetadata);

	}
}
