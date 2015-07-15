package sonar.calculator.mod.common.block.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.SonarMaterials;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FlawlessGreenhouse extends SonarMachineBlock {
	private static boolean keepInventory;

	private Random rand = new Random();
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public FlawlessGreenhouse() {
		super(SonarMaterials.machine);
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityFlawlessGreenhouse) {
			TileEntityFlawlessGreenhouse house = (TileEntityFlawlessGreenhouse) world.getTileEntity(x, y, z);
			if (player.isSneaking()) {
				if (!house.isBeingBuilt() && house.isIncomplete()) {
					FailedCoords coords = house.isComplete();
					if (!coords.getBoolean()) {
						FontHelper.sendMessage("X: " + coords.getX() + " Y: " + coords.getY() + " Z: " + coords.getZ() + " - " + FontHelper.translate("greenhouse.equal") + " " + coords.getBlock(),
								world, player);
					}
				} else if (house.isCompleted()) {
					FontHelper.sendMessage(FontHelper.translate("greenhouse.complete"), world, player);

				}

			} else {
				if (player != null) {
					if (!world.isRemote) {
						player.openGui(Calculator.instance, CalculatorGui.FlawlessGreenhouse, world, x, y, z);
					}
				}
			}
		}
		return true;

	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityFlawlessGreenhouse();
	}

	@Override
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(Calculator.flawlessGreenhouse);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon("Calculator:greenhouse_side");
		this.iconFront = iconRegister.registerIcon("Calculator:flawless_greenhouse_front");
		this.iconTop = iconRegister.registerIcon("Calculator:greenhouse_side");

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess w, int x, int y, int z, int s) {
		Block stone = w.getBlock(x, y + 1, z);
		if (stone != null) {
			if (stone == Calculator.stablestoneBlock) {
				return stone.getIcon(w, x, y, z, s);
			}
		}
		return getIcon(s, w.getBlockMetadata(x, y, z));

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == metadata ? this.iconFront : side == 0 ? this.iconTop : side == 1 ? this.iconTop : (metadata == 0) && (side == 3) ? this.iconFront : this.blockIcon;
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
}
