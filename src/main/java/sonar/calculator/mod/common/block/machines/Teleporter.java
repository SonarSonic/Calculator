package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityTeleporter;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.network.packets.PacketTeleportLinks;
import sonar.calculator.mod.utils.TeleporterRegistry;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.network.PacketTileSync;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.helpers.NBTHelper.SyncType;

public class Teleporter extends SonarMachineBlock {

	public Teleporter() {
		super(SonarMaterials.machine);
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ, BlockInteraction interact) {
		if (player != null) {
			if (!world.isRemote) {
				NBTTagCompound tag = new NBTTagCompound();
				TileEntity target = world.getTileEntity(x, y, z);
				if (target != null && target instanceof TileEntityTeleporter) {
					TileEntityTeleporter tele = (TileEntityTeleporter) target;
					tele.writeData(tag, SyncType.SYNC);
					Calculator.network.sendTo(new PacketTileSync(x, y, z, tag), (EntityPlayerMP) player);
					Calculator.network.sendTo(new PacketTeleportLinks(x, y, z, TeleporterRegistry.getTeleportLinks(tele.teleporterID)), (EntityPlayerMP) player);
					player.openGui(Calculator.instance, CalculatorGui.Teleporter, world, x, y, z);
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
	public void breakBlock(World world, int x, int y, int z, Block oldblock, int oldMetadata) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileEntityTeleporter) {
			TileEntityTeleporter teleporter = (TileEntityTeleporter) tile;
			teleporter.removeFromFrequency();
		}
		super.breakBlock(world, x, y, z, oldblock, oldMetadata);
	}
}
