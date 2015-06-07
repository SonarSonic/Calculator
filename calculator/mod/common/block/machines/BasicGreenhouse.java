package sonar.calculator.mod.common.block.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IWrench;
import sonar.calculator.mod.common.tileentity.machines.TileEntityBasicGreenhouse;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.IDropTile;
import sonar.core.utils.SonarMaterials;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BasicGreenhouse extends SonarMachineBlock implements IWrench {
	private static boolean keepInventory;

	private Random rand = new Random();
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BasicGreenhouse() {
		super(SonarMaterials.machine);
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityBasicGreenhouse) {
			TileEntityBasicGreenhouse house = (TileEntityBasicGreenhouse) world
					.getTileEntity(x, y, z);
			if (player.isSneaking()) {
				if (house.hasRequiredStacks()
						&& house.storage.getEnergyStored() >= house.requiredBuildEnergy) {
					if (house.isIncomplete() && !house.wasBuilt()
							&& !house.isBeingBuilt()) {
						FailedCoords coords = house.createBlock();
						if (!coords.getBoolean()) {
							FontHelper.sendMessage(StatCollector.translateToLocal("greenhouse.block") + " " 
									+ "X: " + coords.getX()
									+ " Y: " + coords.getY()
									+ " Z: " + coords.getZ()
									+ " - " + StatCollector.translateToLocal("greenhouse.blocking"), world, player);
						} else {
							FontHelper.sendMessage(StatCollector.translateToLocal("greenhouse.construction"), world, player);
						}
					}
				}
				if (house.isIncomplete() && !house.wasBuilt()
						&& !house.isBeingBuilt()) {
					if (!house.hasRequiredStacks()) {

						FontHelper.sendMessage(house.getRequiredStacks(), world, player);

					} else if (!(house.storage.getEnergyStored() >= house.requiredBuildEnergy)) {
						if (!world.isRemote) {
							FontHelper.sendMessage(StatCollector.translateToLocal("energy.notEnough"), world, player);
						}

					}
				}
				if (!house.isBeingBuilt() && house.isIncomplete()
						&& house.wasBuilt()) {
					FailedCoords coords = house.isComplete();
					if (!coords.getBoolean()) {
						FontHelper.sendMessage("X: " + coords.getX() + " Y: "
										+ coords.getY() + " Z: "
										+ coords.getZ()
										+ " - " + StatCollector.translateToLocal("greenhouse.equal") + " "
										+ coords.getBlock(), world, player);
					}
				} else if (house.isCompleted()) {
					FontHelper.sendMessage(StatCollector.translateToLocal("greenhouse.complete"), world, player);

				}

			} else {
				if (player != null) {
				if (!world.isRemote) {
					player.openGui(Calculator.instance,
							CalculatorGui.BasicGreenhouse, world, x, y, z);
				}
				}
			}
		}
		return true;

	}

	

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityBasicGreenhouse();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister
				.registerIcon("Calculator:greenhouse_side");
		this.iconFront = iconRegister
				.registerIcon("Calculator:basic_greenhouse_front");
		this.iconTop = iconRegister.registerIcon("Calculator:greenhouse_side");

	}


	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess w, int x, int y, int z, int s) {
		Block log = w.getBlock(x, y + 1, z);
		if (log != null) {
			if (checkLog(log)) {
				return log.getIcon(w, x, y + 1, z, s);
			}
		}
		return getIcon(s, w.getBlockMetadata(x, y, z));

	}

	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == metadata ? this.iconFront : side == 0 ? this.iconTop
				: side == 1 ? this.iconTop
						: (metadata == 0) && (side == 3) ? this.iconFront
								: this.blockIcon;
	}
	
	public boolean checkLog(Block block) {

		for (int i = 0; i < OreDictionary.getOres("logWood").size(); i++) {
			if (OreDictionary.getOres("logWood").get(i).getItem() == Item
					.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("treeWood").size(); i++) {
			if (OreDictionary.getOres("treeWood").get(i).getItem() == Item
					.getItemFromBlock(block)) {
				return true;
			}
		}
		if (block instanceof BlockLog) {
			return true;
		}
		return false;
	}

	@Override
	public boolean dropStandard(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player,
			List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);
		CalculatorHelper.addGasToolTip(stack, player, list);
	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
		
	}
}
