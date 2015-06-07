package sonar.calculator.mod.common.block.misc;

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
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.utils.IDropTile;
import sonar.core.utils.SonarMaterials;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CO2Generator extends SonarMachineBlock implements IWrench {
	private static boolean keepInventory;

	private Random rand = new Random();
	@SideOnly(Side.CLIENT)
	private IIcon iconFront, iconTop;

	public CO2Generator() {
		super(SonarMaterials.machine);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		this.blockIcon = register.registerIcon("Calculator:greenhouse_side");
		this.iconFront = register.registerIcon("Calculator:co2_front");
		this.iconTop = register.registerIcon("Calculator:greenhouse_side");

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
		return side == metadata ? this.iconFront : side == 0 ? this.iconTop
				: side == 1 ? this.iconTop
						: (metadata == 0) && (side == 3) ? this.iconFront
								: this.blockIcon;
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
		if (!world.isRemote) {
			player.openGui(Calculator.instance,
					CalculatorGui.CO2Generator, world, x, y, z);
		}}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TileEntityCO2Generator();
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
