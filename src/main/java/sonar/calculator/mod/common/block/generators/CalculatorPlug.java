package sonar.calculator.mod.common.block.generators;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.items.IStability;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.core.api.SonarAPI;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.api.utils.ActionType;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.IGuiTile;

public class CalculatorPlug extends SonarMachineBlock {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public CalculatorPlug() {
		super(SonarMaterials.machine, false, true);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, Boolean.valueOf(true)));
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (!world.isRemote) {
			ItemStack held = player.getHeldItem();
			if (held != null && held.getItem() instanceof IStability) {
				TileEntity tile = world.getTileEntity(pos);
				StoredItemStack item = new StoredItemStack(held).setStackSize(1);
				StoredItemStack stack = SonarAPI.getItemHelper().getStackToAdd(1, item, SonarAPI.getItemHelper().addItems(tile, item.copy(), EnumFacing.UP, ActionType.PERFORM, null));
				if (stack == null || stack.getStackSize() == 0)
					held.stackSize -= 1;
				return true;
			}
			player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCalculatorPlug();
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity target = world.getTileEntity(pos);
		if (target != null && target instanceof TileEntityCalculatorPlug) {
			TileEntityCalculatorPlug plug = (TileEntityCalculatorPlug) target;
			return state.withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, plug.stable.getObject() == 2);
		}
		return state;
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING, ACTIVE });
	}
}
