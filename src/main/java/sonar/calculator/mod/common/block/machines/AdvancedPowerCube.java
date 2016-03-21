package sonar.calculator.mod.common.block.machines;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedPowerCube;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.BlockInteractionType;
import sonar.core.utils.MachineSide;

public class AdvancedPowerCube extends SonarMachineBlock {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyEnum<MachineSide> DOWN = PropertyEnum.create("down", MachineSide.class);
	public static final PropertyEnum<MachineSide> UP = PropertyEnum.create("up", MachineSide.class);
	public static final PropertyEnum<MachineSide> NORTH = PropertyEnum.create("north", MachineSide.class);
	public static final PropertyEnum<MachineSide> EAST = PropertyEnum.create("east", MachineSide.class);
	public static final PropertyEnum<MachineSide> SOUTH = PropertyEnum.create("south", MachineSide.class);
	public static final PropertyEnum<MachineSide> WEST = PropertyEnum.create("west", MachineSide.class);

	public AdvancedPowerCube() {
		super(SonarMaterials.machine, true, true);
	}

	/*@Override
	 * 
	 * @SideOnly(Side.CLIENT) public void registerBlockIcons(IIconRegister iconRegister) { this.side1 = iconRegister.registerIcon(Calculator.modid + ":" + "advancedPowerCube_side_slot1"); this.side2 = iconRegister.registerIcon(Calculator.modid + ":" + "advancedPowerCube_side_slot2"); this.front1 = iconRegister.registerIcon(Calculator.modid + ":" + "advancedPowerCube_front_slot1"); this.front2 = iconRegister.registerIcon(Calculator.modid + ":" + "advancedPowerCube_front_slot2"); } */
	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (player != null) {
			if (interact.type == BlockInteractionType.SHIFT_RIGHT) {
				if (world.getTileEntity(pos) instanceof TileEntityAdvancedPowerCube) {
					TileEntityAdvancedPowerCube cube = (TileEntityAdvancedPowerCube) world.getTileEntity(pos);
					cube.incrementEnergy(interact.side);
					world.markBlockRangeForRenderUpdate(pos, pos);
				}
			} else {
				player.openGui(Calculator.instance, CalculatorGui.advancedCube, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityAdvancedPowerCube();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);
	}
}
