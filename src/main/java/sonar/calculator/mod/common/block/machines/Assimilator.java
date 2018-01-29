package sonar.calculator.mod.common.block.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.IGuiTile;

import java.util.List;

public class Assimilator extends SonarMachineBlock {

	public int type;

	public Assimilator(int type) {
		super(SonarMaterials.machine, true, true);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - 0.0625F * 3, 1.0F);
		this.type = type;
	}

    @Override
	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (player != null && !world.isRemote) {
			if (type == 0) {
				player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
			} else {
				player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		if (this.type == 0) {
			return new TileEntityAssimilator.Stone();
		} else {
			return new TileEntityAssimilator.Algorithm();
		}
	}

    @Override
    public void addSpecialToolTip(ItemStack stack, World world, List<String> list, NBTTagCompound tag) {
        super.addSpecialToolTip(stack, world, list, tag);
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
