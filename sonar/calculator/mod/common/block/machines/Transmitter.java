package sonar.calculator.mod.common.block.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityTransmitter;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarMaterials;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Transmitter extends SonarMachineBlock {

	private Random rand = new Random();

	public Transmitter() {
		super(SonarMaterials.machine);
		this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
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
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityTransmitter();
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
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if (!world.getBlock(x, y + 1, z).isReplaceable(world, x, y + 1, z)) {
			return false;
		}
		return true;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		float x1 = x + random.nextFloat();
		float y1 = y + 0.5F;
		float z1 = z + random.nextFloat();

		world.spawnParticle("smoke", x1, y1, z1, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("smoke", x1, y1, z1, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("smoke", x1, y1 + 1.0F, z1, 0.0D, 0.0D, 0.0D);
		world.spawnParticle("smoke", x1, y1 + 1.0F, z1, 0.0D, 0.0D, 0.0D);

	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		setDefaultDirection(world, x, y, z);
		setBlocks(world, x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block oldblock, int oldMetadata) {
		super.breakBlock(world, x, y, z, oldblock, oldMetadata);
		this.removeBlocks(world, x, y, z);
	}

	private void setBlocks(World world, int x, int y, int z) {
		world.setBlock(x, y + 1, z, Calculator.transmitterBlock);
	}

	private void removeBlocks(World world, int x, int y, int z) {
		world.setBlockToAir(x, y + 1, z);
	}
}
