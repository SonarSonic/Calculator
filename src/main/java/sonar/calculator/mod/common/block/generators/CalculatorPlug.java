package sonar.calculator.mod.common.block.generators;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, Boolean.valueOf(true)));
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (!world.isRemote) {
			ItemStack held = player.getHeldItemMainhand();
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

	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState().withProperty(ACTIVE, true);
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVE, meta==1 ? true : false);

	}

	public int getMetaFromState(IBlockState state) {
		return state.getValue(ACTIVE) ? 1 : 0;
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { ACTIVE });
	}
}
