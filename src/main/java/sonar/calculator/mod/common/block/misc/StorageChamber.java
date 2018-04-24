package sonar.calculator.mod.common.block.misc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.IGuiTile;

import javax.annotation.Nonnull;
import java.util.List;

public class StorageChamber extends SonarMachineBlock {

	public StorageChamber() {
		super(SonarMaterials.machine, true, true);
	}

	public boolean hasAnimatedFront() {
		return false;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (player != null && !world.isRemote) {
			player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World world, int i) {
		return new TileEntityStorageChamber();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		world.removeTileEntity(pos);
	}

    @Override
    public void addSpecialToolTip(ItemStack stack, World world, List<String> list, NBTTagCompound tag) {
    	if(tag==null){
    		return;
    	}
        switch (tag.getInteger("type")) {
            case 1:
                list.add(FontHelper.translate("circuit.type") + ": " + FontHelper.translate("circuit.analysed"));
                break;
            case 2:
                list.add(FontHelper.translate("circuit.type") + ": " + FontHelper.translate("circuit.stable"));
                break;

            case 3:
                list.add(FontHelper.translate("circuit.type") + ": " + FontHelper.translate("circuit.damaged"));
                break;

            case 4:
                list.add(FontHelper.translate("circuit.type") + ": " + FontHelper.translate("circuit.dirty"));
                break;
        }

        int[] stored = tag.getIntArray("stored");
        int total = 0;
        for (int aStored : stored) {
            total += aStored;
        }
        if (total != 0) {
            list.add(FontHelper.translate("circuit.stored") + ": " + total);
        }
    }

    @Override
	public boolean hasSpecialRenderer() {
		return true;
	}
}
