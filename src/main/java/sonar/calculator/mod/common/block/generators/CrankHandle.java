package sonar.calculator.mod.common.block.generators;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankHandle;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.BlockInteraction;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CrankHandle extends SonarMachineBlock {
	@SideOnly(Side.CLIENT)
	private IIcon icon;

	public CrankHandle() {
		super(SonarMaterials.machine);
		setBlockBounds((float)(0.0625*3), 0.0F, (float)(0.0625*3), (float)(1-(0.0625*3)), 0.625F, (float)(1-(0.0625*3)));
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, BlockInteraction interact) {
		TileEntityCrankHandle crank = (TileEntityCrankHandle) world.getTileEntity(x, y, z);
		int rand1 = 0 + (int) (Math.random() * 100.0D);

		if (!crank.cranked) {
			crank.cranked = true;
			if (rand1 < 1) {
				dropBlockAsItem(world, x, y, z, new ItemStack(Items.stick, 2));
				world.setBlockToAir(x, y, z);
			}
			return true;
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		this.icon = register.registerIcon("Calculator:crank");
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		super.canPlaceBlockAt(world, x, y, z);
		if ((y >= 0) && (y < 256)) {
			Block block = world.getBlock(x, y - 1, z);
			if (block == Calculator.handcrankedGenerator) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block id) {
		super.canPlaceBlockAt(world, x, y, z);
		Block block = world.getBlock(x, y - 1, z);
		if (block != Calculator.handcrankedGenerator) {
			world.func_147480_a(x, y, z, true);
			world.markBlockForUpdate(x, y, z);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return this.icon;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityCrankHandle();
	}

	@Override
	public boolean dropStandard(World world, int x, int y, int z) {
		return true;
	}

}
