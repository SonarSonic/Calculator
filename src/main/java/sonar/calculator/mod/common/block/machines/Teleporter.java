package sonar.calculator.mod.common.block.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityTeleporter;
import sonar.calculator.mod.network.packets.PacketTeleportLinks;
import sonar.calculator.mod.utils.TeleporterRegistry;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.IGuiTile;

import javax.annotation.Nonnull;
import java.util.List;

public class Teleporter extends SonarMachineBlock {

	public Teleporter() {
		super(SonarMaterials.machine, false, true);
	}

    @Override
	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (player != null) {
			if (!world.isRemote) {
				NBTTagCompound tag = new NBTTagCompound();
				TileEntity target = world.getTileEntity(pos);
				if (target instanceof TileEntityTeleporter) {
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
	public TileEntity createNewTileEntity(@Nonnull World var1, int var2) {
		return new TileEntityTeleporter();
	}

    @Override
    public void addSpecialToolTip(ItemStack stack, World world, List<String> list, NBTTagCompound tag) {
		list.add("A simple teleporter");
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityTeleporter) {
			TileEntityTeleporter teleporter = (TileEntityTeleporter) tile;
			teleporter.removeFromFrequency();
		}
		super.breakBlock(world, pos, state);
	}
}
