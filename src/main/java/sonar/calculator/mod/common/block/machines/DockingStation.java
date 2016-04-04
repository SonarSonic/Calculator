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
import sonar.calculator.mod.common.tileentity.machines.TileEntityDockingStation;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.BlockInteractionType;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.upgrades.MachineUpgrade;

public class DockingStation extends SonarMachineBlock {

	public DockingStation() {
		super(SonarMaterials.machine, true, true);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.95F, 1.0F);
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (player != null) {
			if (interact.type == BlockInteractionType.RIGHT) {
				if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof MachineUpgrade) {
					return false;
				}
				if (!insertCalculator(player, world, pos)) {
					if (!world.isRemote) {
						TileEntity target = world.getTileEntity(pos);
						if (target != null && target instanceof TileEntityDockingStation) {
							TileEntityDockingStation station = (TileEntityDockingStation) target;
							if (station.isCalculator(station.calcStack) != 0) {
								player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
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

	public boolean insertCalculator(EntityPlayer player, World world, BlockPos pos) {
		if (player.getHeldItem() != null && TileEntityDockingStation.isCalculator(player.getHeldItem()) > 0) {
			TileEntity target = world.getTileEntity(pos);
			if (target != null && target instanceof TileEntityDockingStation) {
				TileEntityDockingStation station = (TileEntityDockingStation) target;
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
}
