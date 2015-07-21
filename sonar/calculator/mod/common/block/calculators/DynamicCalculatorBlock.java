package sonar.calculator.mod.common.block.calculators;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator.Dynamic;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.block.SonarBlock;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.SonarMaterials;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DynamicCalculatorBlock extends SonarMachineBlock {
	@SideOnly(Side.CLIENT)
	private IIcon iconFront, iconTop;

	public DynamicCalculatorBlock() {
		super(SonarMaterials.machine, true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon("Calculator:atomiccalculatoranimate1");
		this.iconFront = iconRegister.registerIcon("Calculator:dynamiccalculatoranimate");
		this.iconTop = iconRegister.registerIcon("Calculator:atomiccalculatoranimate2");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == metadata ? this.iconFront : side == 0 ? this.iconTop : side == 1 ? this.iconTop : (metadata == 0) && (side == 3) ? this.iconFront : this.blockIcon;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack) {
		super.onBlockPlacedBy(world, x, y, z, entityplayer, itemstack);
		TileEntity target = world.getTileEntity(x, y, z);
		if (target != null && target instanceof TileEntityCalculator.Dynamic) {
			TileEntityCalculator.Dynamic calc = (TileEntityCalculator.Dynamic) target;
			calc.getResearch();
		}
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			TileEntity target = world.getTileEntity(x, y, z);
			if (target != null && target instanceof TileEntityCalculator.Dynamic) {
				TileEntityCalculator.Dynamic calc = (Dynamic) target;
				player.openGui(Calculator.instance, CalculatorGui.DynamicCalculator, world, x, y, z);
				/*
				 * FailedCoords coords = calc.checkStructure(); if (coords.getBoolean()) { player.openGui(Calculator.instance, CalculatorGui.DynamicCalculator, world, x, y, z); } else { if
				 * (!world.isRemote) { FontHelper.sendMessage("No Multi-block", world, player); FontHelper.sendMessage("X: " + coords.getX() + " Y: " + coords.getY() + " Z: " + coords.getZ() + " - " +
				 * FontHelper.translate("greenhouse.equal") + " " + coords.getBlock(), world, player); } }
				 */
			}
		}

		return true;

	}

	@SideOnly(Side.CLIENT)
	public String localBlockName(String string) {
		if (string.equals("stable")) {
			return FontHelper.translate("tile.StableStone.name");
		}
		if (string.equals("glass")) {
			return FontHelper.translate("tile.StableGlass.name");
		} else {
			return FontHelper.translate("tile.Air.name");
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityCalculator.Dynamic();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block oldblock, int oldMetadata) {
		TileEntity entity = world.getTileEntity(x, y, z);

		if (entity != null && entity instanceof IInventory) {
			IInventory tileentity = (IInventory) world.getTileEntity(x, y, z);
			for (int i = 0; i < tileentity.getSizeInventory(); i++) {
				ItemStack itemstack = tileentity.getStackInSlot(i);

				if (itemstack != null && i != 0 && i != 3 && i != 6) {
					float f = this.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

					while (itemstack.stackSize > 0) {
						int j = this.rand.nextInt(21) + 10;

						if (j > itemstack.stackSize) {
							j = itemstack.stackSize;
						}

						itemstack.stackSize -= j;

						EntityItem item = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

						if (itemstack.hasTagCompound()) {
							item.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}

						world.spawnEntityInWorld(item);
					}
				}
			}

			world.func_147453_f(x, y, z, oldblock);
		}
		world.removeTileEntity(x, y, z);

	}
}
