package sonar.calculator.mod.common.block.misc;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.network.packets.PacketFluxNetworkList;
import sonar.calculator.mod.utils.FluxRegistry;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.network.PacketTileSync;
import sonar.core.utils.SonarMaterials;

public class FluxController extends SonarMachineBlock {

	private Random rand = new Random();	

	public FluxController() {
		super(SonarMaterials.machine, false);
		this.setBlockBounds(0.0625F, 0.0625F, 0.0625F, 1 - 0.0625F, 1 - 0.0625F, 1 - 0.0625F);
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
				if (target != null && target instanceof TileEntityFluxController) {
					TileEntityFluxController controller = (TileEntityFluxController) target;
					NBTTagCompound tag = new NBTTagCompound();
					controller.writeToNBT(tag);
					Calculator.network.sendTo(new PacketTileSync(x, y, z, tag),(EntityPlayerMP) player);
					Calculator.network.sendTo(new PacketFluxNetworkList(x, y, z, FluxRegistry.getAvailableNetworks(player.getGameProfile().getName(), controller)), (EntityPlayerMP) player);
					player.openGui(Calculator.instance, CalculatorGui.FluxController, world, x, y, z);
				}
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityFluxController();
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
		list.add("Manages Energy");
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemstack) {
		super.onBlockPlacedBy(world, x, y, z, player, itemstack);
		TileEntity target = world.getTileEntity(x, y, z);
		if (target != null && target instanceof TileEntityFluxController) {
			TileEntityFluxController control = (TileEntityFluxController) target;
			if (player != null && player instanceof EntityPlayer) {
				control.setPlayer((EntityPlayer) player);
			}
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block oldblock, int oldMetadata) {
		TileEntity target = world.getTileEntity(x, y, z);
		if (target != null && target instanceof TileEntityFluxController) {
			TileEntityFluxController control = (TileEntityFluxController) target;
			control.removeChunks();
		}
		super.breakBlock(world, x, y, z, oldblock, oldMetadata);
	}
}
