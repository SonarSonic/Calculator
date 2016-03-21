package sonar.calculator.mod.common.block.generators;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankedGenerator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.helpers.FontHelper;

public class CrankedGenerator extends SonarMachineBlock {
	public CrankedGenerator() {
		super(SonarMaterials.machine, true, true);
	}

	/*
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon("Calculator:crank_side");
		this.iconFront = iconRegister.registerIcon("Calculator:crank_front");
		this.iconTop = iconRegister.registerIcon("Calculator:crank_top");
	}
	*/

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity != null && tileentity instanceof TileEntityCrankedGenerator) {
			TileEntityCrankedGenerator generator = (TileEntityCrankedGenerator) world.getTileEntity(pos);
			generator.updateAdjacentHandlers();
		}
	}
	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbour) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity != null && tileentity instanceof TileEntityCrankedGenerator) {
			TileEntityCrankedGenerator generator = (TileEntityCrankedGenerator) world.getTileEntity(pos);
			generator.updateAdjacentHandlers();
		}

	}
	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (player != null) {
			if (!world.isRemote) {
				player.openGui(Calculator.instance, CalculatorGui.CrankedGenerator, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityCrankedGenerator();
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
