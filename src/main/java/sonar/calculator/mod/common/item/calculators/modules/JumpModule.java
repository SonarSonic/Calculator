package sonar.calculator.mod.common.item.calculators.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.modules.IModuleClickable;
import sonar.calculator.mod.network.packets.PacketJumpModule;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.helpers.FontHelper;

public class JumpModule extends ModuleBase implements IModuleClickable {

	@Override
	public String getClientName() {
		return "Jump Module";
	}

	@Override
	public String getName() {
		return "Jump";
	}

	@Override
	public void onModuleActivated(ItemStack stack, NBTTagCompound modeTag, World world, EntityPlayer player) {
		long last = modeTag.getLong("last");
		long current = world.getWorldTime();
		if (current < last || (current > last + 100)) {
			if (world.isRemote) {
				MovingObjectPosition position = player.rayTrace(500, 1);
				BlockPos pos = position.getBlockPos();
				if (world.isAirBlock(pos.offset(EnumFacing.UP)) && world.isAirBlock(pos.offset(EnumFacing.UP, 2))) {
					Calculator.network.sendToServer(new PacketJumpModule(pos));
				} else {
					FontHelper.sendMessage("Target is blocked", world, player);
				}
			} else {
				modeTag.setLong("last", world.getWorldTime());
			}
		} else if (!world.isRemote) {
			FontHelper.sendMessage("" + "Cool Down: " + (current-last) + "%", world, player);
		}
	}

	@Override
	public boolean onBlockClicked(ItemStack stack, NBTTagCompound modeTag, EntityPlayer player, World world, BlockPos pos, BlockInteraction interaction) {
		return false;
	}

}
