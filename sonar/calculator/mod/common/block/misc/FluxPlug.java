package sonar.calculator.mod.common.block.misc;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.TileEntityFluxHandler;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPlug;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.network.packets.PacketFluxNetworkList;
import sonar.calculator.mod.network.packets.PacketTileSync;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarMaterials;
import sonar.core.utils.helpers.NBTHelper.SyncType;

public class FluxPlug extends SonarMachineBlock {

	private Random rand = new Random();

	public FluxPlug() {
		super(SonarMaterials.machine, false);
		this.setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
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
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			if (!world.isRemote) {

				TileEntity target = world.getTileEntity(x, y, z);
				if (target != null && target instanceof TileEntityFlux) {
					TileEntityFlux plug = (TileEntityFlux) target;
					NBTTagCompound tag = new NBTTagCompound();
					plug.writeToNBT(tag);
					Calculator.network.sendTo(new PacketTileSync(x, y, z, tag),(EntityPlayerMP) player);
					Calculator.network.sendTo(new PacketFluxNetworkList(x, y, z, FluxRegistry.getAvailableNetworks(player.getGameProfile().getName(), null)), (EntityPlayerMP) player);
					player.openGui(Calculator.instance, CalculatorGui.FluxPlug, world, x, y, z);

				}
			}
		}

		return true;
	}

	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityFluxHandler) {
			TileEntityFluxHandler flux = (TileEntityFluxHandler) world.getTileEntity(x, y, z);
			flux.updateAdjacentHandlers();
		}

	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemstack) {
		super.onBlockPlacedBy(world, x, y, z, player, itemstack);
		TileEntity target = world.getTileEntity(x, y, z);
		if (target != null && target instanceof TileEntityFluxHandler) {
			TileEntityFluxHandler flux = (TileEntityFluxHandler) target;
			flux.updateAdjacentHandlers();
			if (player != null && player instanceof EntityPlayer) {
				flux.setPlayer((EntityPlayer) player);
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityFluxPlug();
	}

	@Override
	public boolean dropStandard(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
		list.add("Sends Energy");
	}
}
