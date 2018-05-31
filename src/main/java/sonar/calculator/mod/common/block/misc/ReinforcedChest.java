package sonar.calculator.mod.common.block.misc;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.common.tileentity.misc.TileEntityReinforcedChest;
import sonar.core.api.inventories.ISonarInventoryTile;
import sonar.core.network.FlexibleGuiHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class ReinforcedChest extends BlockChest {
	public ReinforcedChest() {
		super(BlockChest.Type.BASIC);
	}

	@Override
	public boolean onBlockActivated(World world, @Nonnull BlockPos pos, IBlockState state, @Nonnull EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			FlexibleGuiHandler.instance().openBasicTile(player, world, pos, 0);
		}
		return true;
	}

	@Override
	public void breakBlock(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof ISonarInventoryTile){
			List<ItemStack> stacks = ((ISonarInventoryTile) tile).inv().getDrops();
			for (ItemStack itemstack : stacks){
				if (!itemstack.isEmpty()){
					InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
				}
			}
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityReinforcedChest();
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return true;
	}
}
