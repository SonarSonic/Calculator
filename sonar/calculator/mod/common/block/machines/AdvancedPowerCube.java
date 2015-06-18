package sonar.calculator.mod.common.block.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IWrench;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedPowerCube;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.IDropTile;
import sonar.core.utils.SonarMaterials;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AdvancedPowerCube extends SonarMachineBlock{
	@SideOnly(Side.CLIENT)
	private IIcon front1, front2, side1, side2;
	private static boolean keepInventory;
	private Random rand = new Random();

	public AdvancedPowerCube() {
		super(SonarMaterials.machine);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.side1 = iconRegister.registerIcon(Calculator.modid + ":"
				+ "advancedPowerCube_side_slot1");
		this.side2 = iconRegister.registerIcon(Calculator.modid + ":"
				+ "advancedPowerCube_side_slot2");
		this.front1 = iconRegister.registerIcon(Calculator.modid + ":"
				+ "advancedPowerCube_front_slot1");
		this.front2 = iconRegister.registerIcon(Calculator.modid + ":"
				+ "advancedPowerCube_front_slot2");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
		TileEntity entity = access.getTileEntity(x, y, z);
		if (entity != null) {
			if (entity instanceof TileEntityAdvancedPowerCube) {
				TileEntityAdvancedPowerCube t = (TileEntityAdvancedPowerCube) access
						.getTileEntity(x, y, z);
				int s = t.side(side);
				if (side == access.getBlockMetadata(x, y, z)) {
					if(side==s){
						return front2;
					}
					else{
						return front1;
					}
				} else {
					if(side==s){
						return side2;
					}
					else{
						return side1;
					}
				}
			}
		}
		return side1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == metadata ? this.front1 : side == 0 ? this.side1
				: side == 1 ? this.side1
						: (metadata == 0) && (side == 3) ? this.front1
								: this.side1;
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(Calculator.advancedPowerCube);
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			if (player.isSneaking()) {
				if (world.getTileEntity(x, y, z) instanceof TileEntityAdvancedPowerCube) {
					TileEntityAdvancedPowerCube cube = (TileEntityAdvancedPowerCube) world
							.getTileEntity(x, y, z);
					cube.incrementEnergy(side);
					world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
				}
			}
			else {
				player.openGui(Calculator.instance,
						CalculatorGui.advancedCube, world, x, y, z);
			}
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityAdvancedPowerCube();
	}

	@Override
	public boolean dropStandard(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player,
			List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);
		
	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
		
	}
}
