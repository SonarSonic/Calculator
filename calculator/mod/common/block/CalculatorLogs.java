package sonar.calculator.mod.common.block;

import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CalculatorLogs extends BlockLog {
	int type;
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public CalculatorLogs(int type) {
		this.type = type;
		setHarvestLevel("axe", 0);
		setHardness(0.7F);
	}

	@Override
	public boolean isWood(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		switch (type) {
		case 0:
			this.blockIcon = iconRegister.registerIcon("Calculator:wood/log_amethyst_side");
			this.iconTop = iconRegister.registerIcon("Calculator:wood/log_amethyst_top");
			break;
		case 1:
			this.blockIcon = iconRegister.registerIcon("Calculator:wood/log_tanzanite_side");
			this.iconTop = iconRegister.registerIcon("Calculator:wood/log_tanzanite_top");
			break;
		case 2:
			this.blockIcon = iconRegister.registerIcon("Calculator:wood/log_pear_side");
			this.iconTop = iconRegister.registerIcon("Calculator:wood/log_pear_top");
			break;
		case 3:
			this.blockIcon = iconRegister.registerIcon("Calculator:wood/log_diamond_side");
			this.iconTop = iconRegister.registerIcon("Calculator:wood/log_diamond_top");
			break;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getSideIcon(int side) {
		return blockIcon;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getTopIcon(int side) {
		return iconTop;
	}
}
