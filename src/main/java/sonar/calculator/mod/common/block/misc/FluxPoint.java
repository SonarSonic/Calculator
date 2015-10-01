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
import sonar.calculator.mod.common.tileentity.TileEntityFluxHandler;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPoint;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.network.packets.PacketFluxNetworkList;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.network.PacketTileSync;
import sonar.core.utils.SonarMaterials;
import sonar.core.utils.helpers.NBTHelper.SyncType;

public class FluxPoint extends SonarMachineBlock {

	private Random rand = new Random();

	public FluxPoint() {
		super(SonarMaterials.machine, false);
		this.setBlockBounds(0.375F, 0.375F, 0.375F, 0.625F, 0.625F, 0.625F);
	}


	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			if (!world.isRemote) {
				TileEntity target = world.getTileEntity(x, y, z);
				if (target != null && target instanceof TileEntityFluxPoint) {
					TileEntityFluxPoint point = (TileEntityFluxPoint) target;
					NBTTagCompound tag = new NBTTagCompound();
					point.writeToNBT(tag);
					Calculator.network.sendTo(new PacketTileSync(x, y, z, tag),(EntityPlayerMP) player);		
					Calculator.network.sendTo(new PacketFluxNetworkList(x, y, z, FluxRegistry.getAvailableNetworks(player.getGameProfile().getName(), null)), (EntityPlayerMP) player);
					player.openGui(Calculator.instance, CalculatorGui.FluxPoint, world, x, y, z);
				}
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityFluxPoint();
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
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityFluxHandler) {
			TileEntityFluxHandler flux = (TileEntityFluxHandler) world.getTileEntity(x, y, z);
			flux.updateAdjacentHandlers();
		}

	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
		list.add("Receives Energy");
	}	
	
	public boolean hasSpecialRenderer() {
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemstack) {
		super.onBlockPlacedBy(world, x, y, z, player, itemstack);
		TileEntity target = world.getTileEntity(x, y, z);
		if (target != null && target instanceof TileEntityFluxHandler) {
			TileEntityFluxHandler flux = (TileEntityFluxHandler) target;
			if (player != null && player instanceof EntityPlayer) {
				flux.setPlayer((EntityPlayer) player);
			}
			flux.updateAdjacentHandlers();
		}
	}

}
