package sonar.calculator.mod.common.block.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.IGuiTile;

import java.util.UUID;

public class ResearchChamber extends SonarMachineBlock {

	public ResearchChamber() {
		super(SonarMaterials.machine, true, true);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - 0.0625F * 8, 1.0F);
	}

    @Override
	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (player != null && !world.isRemote) {
			TileEntity target = world.getTileEntity(pos);
			if (target != null && target instanceof TileEntityResearchChamber) {
				TileEntityResearchChamber chamber = (TileEntityResearchChamber) target;
				UUID playerUUID = chamber.playerUUID.getUUID();
				if (playerUUID.equals(player.getName())) {
					FMLNetworkHandler.openGui(player, Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
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
		if (target != null && target instanceof TileEntityResearchChamber) {
			TileEntityResearchChamber chamber = (TileEntityResearchChamber) target;
			if (player != null && player instanceof EntityPlayer) {
				chamber.playerUUID.setObject(((EntityPlayer) player).getGameProfile().getId());
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityResearchChamber();
	}
}