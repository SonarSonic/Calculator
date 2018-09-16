package sonar.calculator.mod.common.block.misc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.core.common.block.SonarBlockContainer;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.network.FlexibleGuiHandler;
import sonar.core.utils.ISpecialTooltip;

import javax.annotation.Nonnull;
import java.util.List;

public class StorageChamber extends SonarBlockContainer implements ISpecialTooltip {

	public StorageChamber() {
		super(SonarMaterials.machine, true);
	}

	public boolean hasAnimatedFront() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player != null && !world.isRemote) {
			FlexibleGuiHandler.instance().openBasicTile(player, world, pos, 0);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull World world, int i) {
		return new TileEntityStorageChamber();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
	    super.breakBlock(world, pos, state);
		//world.removeTileEntity(pos);
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
}
