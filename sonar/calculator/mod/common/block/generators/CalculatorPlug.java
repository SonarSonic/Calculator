package sonar.calculator.mod.common.block.generators;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarMaterials;

public class CalculatorPlug extends SonarMachineBlock {
	private Random rand = new Random();
	private static boolean keepInventory;

	public CalculatorPlug() {
		super(SonarMaterials.machine);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
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
	public boolean operateBlock(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			player.openGui(Calculator.instance, CalculatorGui.CalculatorPlug,world, x, y, z);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityCalculatorPlug();
	}

	@Override
	public boolean dropStandard(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player,
			List list) {
	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {

	}

}
