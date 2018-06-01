package sonar.calculator.mod.common.block.machines;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.core.common.block.SonarBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.network.FlexibleGuiHandler;
import sonar.core.utils.ISpecialTooltip;

import javax.annotation.Nonnull;
import java.util.List;

public class Assimilator extends SonarBlock implements ITileEntityProvider, ISpecialTooltip {

	public int type;

	public Assimilator(int type) {
		super(SonarMaterials.machine, true);
		this.type = type;
		this.hasSpecialRenderer = true;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - 0.0625F * 3, 1.0F);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player != null && !world.isRemote) {
			FlexibleGuiHandler.instance().openBasicTile(player, world, pos, 0);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World var1, int var2) {
		if (this.type == 0) {
			return new TileEntityAssimilator.Stone();
		} else {
			return new TileEntityAssimilator.Algorithm();
		}
	}

    @Override
    public void addSpecialToolTip(ItemStack stack, World world, List<String> list, NBTTagCompound tag) {
        if (type == 0) {
            if (stack.hasTagCompound()) {
                int hunger = stack.getTagCompound().getInteger("hunger");
                if (hunger != 0) {
                    list.add(FontHelper.translate("points.hunger") + ": " + hunger);
                }
                int health = stack.getTagCompound().getInteger("health");
                if (health != 0) {
                    list.add(FontHelper.translate("points.health") + ": " + health);
                }
            }
        }
    }
}
