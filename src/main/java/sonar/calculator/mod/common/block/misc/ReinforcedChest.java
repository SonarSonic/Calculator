package sonar.calculator.mod.common.block.misc;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityReinforcedChest;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.ISpecialTooltip;

public class ReinforcedChest extends BlockChest implements ISpecialTooltip {
	public ReinforcedChest() {
		super(BlockChest.Type.BASIC);
	}
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			if (!world.isRemote) {
				FMLNetworkHandler.openGui(player, Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityReinforcedChest();
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return true;
	}

	private boolean isDoubleChest(World worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
		list.add(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + "New Feature!");
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
	}
}
