package sonar.calculator.mod.common.item.calculators.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.modules.IModuleClickable;
import sonar.calculator.mod.network.packets.PacketJumpModule;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.helpers.FontHelper;

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
		if (current < last || (current > last + 50)) {
			if (world.isRemote) {
				RayTraceResult position = player.rayTrace(500, 1);
				BlockPos pos = position.getBlockPos();
				if (world.isAirBlock(pos.offset(EnumFacing.UP)) && world.isAirBlock(pos.offset(EnumFacing.UP, 2))) {
					Calculator.network.sendToServer(new PacketJumpModule(pos));
				} else {
					pos = pos.offset(position.sideHit, 1);
					if (world.isAirBlock(pos.offset(EnumFacing.UP)) && world.isAirBlock(pos.offset(EnumFacing.UP, 2))) {
						Calculator.network.sendToServer(new PacketJumpModule(pos));
					} else
						player.sendMessage(new TextComponentTranslation("Target is blocked"));
				}
			} else {
				modeTag.setLong("last", world.getWorldTime());
			}
		} else if (!world.isRemote) {
			FontHelper.sendMessage("" + "Cool Down: " + (current - last)*2 + "%", world, player);
		}
	}

	@Override
	public boolean onBlockClicked(ItemStack stack, NBTTagCompound modeTag, EntityPlayer player, World world, BlockPos pos, BlockInteraction interaction) {
		return false;
	}

}
