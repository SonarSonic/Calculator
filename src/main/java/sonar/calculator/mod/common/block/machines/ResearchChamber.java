package sonar.calculator.mod.common.block.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.core.common.block.SonarBlockContainer;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.network.FlexibleGuiHandler;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ResearchChamber extends SonarBlockContainer {

	public ResearchChamber() {
		super(SonarMaterials.machine, true);
		this.hasSpecialRenderer = true;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - 0.0625F * 8, 1.0F);
	}

	@Nonnull
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player != null && !world.isRemote) {
			TileEntity target = world.getTileEntity(pos);
			if (target instanceof TileEntityResearchChamber) {
				TileEntityResearchChamber chamber = (TileEntityResearchChamber) target;
				UUID playerUUID = chamber.playerUUID.getUUID();
				if (playerUUID.equals(player.getName())) {
					FlexibleGuiHandler.instance().openBasicTile(player, world, pos, 0);
				} else {
					FontHelper.sendMessage("Access Denied: " + playerUUID, world, player);
				}
			}
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack itemstack) {
		super.onBlockPlacedBy(world, pos, state, player, itemstack);
		TileEntity target = world.getTileEntity(pos);
		if (target instanceof TileEntityResearchChamber) {
			TileEntityResearchChamber chamber = (TileEntityResearchChamber) target;
			if (player instanceof EntityPlayer) {
				chamber.playerUUID.setObject(((EntityPlayer) player).getGameProfile().getId());
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World var1, int var2) {
		return new TileEntityResearchChamber();
	}
}