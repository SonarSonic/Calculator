package sonar.calculator.mod.common.block.generators;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.IWrench;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.IDropTile;
import sonar.core.utils.SonarMaterials;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class StarchExtractor extends SonarMachineBlock implements IWrench {
	private Random rand = new Random();
	private static boolean keepInventory;

	public StarchExtractor() {
		super(SonarMaterials.machine);
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
				player.openGui(Calculator.instance, CalculatorGui.StarchExtractor, world, x, y, z);
			}
		}
		return true;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityGenerator.StarchExtractor) {
			TileEntityGenerator.StarchExtractor generator = (TileEntityGenerator.StarchExtractor) world.getTileEntity(x, y, z);
			generator.updateHandlers();
		}
	}

	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityGenerator.StarchExtractor) {
			TileEntityGenerator.StarchExtractor generator = (TileEntityGenerator.StarchExtractor) world.getTileEntity(x, y, z);
			generator.updateHandlers();
		}

	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityGenerator.StarchExtractor) {
			TileEntityGenerator.StarchExtractor generator = (TileEntityGenerator.StarchExtractor) world.getTileEntity(x, y, z);
			generator.updateHandlers();
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityGenerator.StarchExtractor();
	}

	@Override
	public boolean dropStandard(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);
		CalculatorHelper.addItemLevelToolTip(stack, player, list);

	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
		list.add(FontHelper.translate("energy.generate") + ": " + CalculatorConfig.starchRF + " RF/t");

	}
}
