package sonar.calculator.mod.common.block.generators;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankedGenerator;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarMaterials;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CrankedGenerator extends SonarMachineBlock {
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	private static boolean keepInventory;
	private Random rand = new Random();

	public CrankedGenerator() {
		super(SonarMaterials.machine);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon("Calculator:crank_side");
		this.iconFront = iconRegister.registerIcon("Calculator:crank_front");
		this.iconTop = iconRegister.registerIcon("Calculator:crank_top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == metadata ? this.iconFront : side == 0 ? this.iconTop : side == 1 ? this.iconTop : (metadata == 0) && (side == 3) ? this.iconFront : this.blockIcon;
	}

	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityCrankedGenerator) {
			TileEntityCrankedGenerator generator = (TileEntityCrankedGenerator) world.getTileEntity(x, y, z);
			generator.updateAdjacentHandlers();
		}

	}


	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		setDefaultDirection(world, x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityCrankedGenerator) {
			TileEntityCrankedGenerator generator = (TileEntityCrankedGenerator) world.getTileEntity(x, y, z);
			generator.updateAdjacentHandlers();
		}

	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			if (!world.isRemote) {
				player.openGui(Calculator.instance, CalculatorGui.CrankedGenerator, world, x, y, z);
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityCrankedGenerator();
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
		list.add(FontHelper.translate("energy.generate") + ": " + 18 + " RF/t");

	}
}
