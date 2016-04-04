package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityTeleporter;
import sonar.calculator.mod.network.packets.PacketTeleportLinks;
import sonar.calculator.mod.utils.TeleporterRegistry;
import sonar.core.SonarCore;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.network.PacketTileSync;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.IGuiTile;

public class Teleporter extends SonarMachineBlock {

	public Teleporter() {
		super(SonarMaterials.machine, false, true);
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (player != null) {
			if (!world.isRemote) {
				NBTTagCompound tag = new NBTTagCompound();
				TileEntity target = world.getTileEntity(pos);
				if (target != null && target instanceof TileEntityTeleporter) {
					TileEntityTeleporter tele = (TileEntityTeleporter) target;
					tele.sendSyncPacket(player);
					Calculator.network.sendTo(new PacketTeleportLinks(pos, TeleporterRegistry.getTeleportLinks(tele.teleporterID)), (EntityPlayerMP) player);
					player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
				}
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityTeleporter();
	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
		list.add("A simple teleporter");
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile != null && tile instanceof TileEntityTeleporter) {
			TileEntityTeleporter teleporter = (TileEntityTeleporter) tile;
			teleporter.removeFromFrequency();
		}
		super.breakBlock(world, pos, state);
	}
}
