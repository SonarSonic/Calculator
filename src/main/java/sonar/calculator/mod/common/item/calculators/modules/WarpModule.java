package sonar.calculator.mod.common.item.calculators.modules;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.api.modules.IModuleClickable;
import sonar.core.api.blocks.IStableBlock;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.helpers.FontHelper;

public class WarpModule extends ModuleBase implements IModuleClickable {

	@Override
	public boolean isLoadable() {
		return true;
	}

	@Override
	public String getName() {
		return "Teleport";
	}

	@Override
	public void onModuleActivated(ItemStack stack, NBTTagCompound tag, World world, EntityPlayer player) {
		if (!world.isRemote) {
			if (!tag.hasKey("click") || !tag.getBoolean("click")) {
                String message;
				if (player.dimension == tag.getInteger("Dimension")) {
					if (tag.getBoolean("Tele")) {
                        BlockPos target = new BlockPos(tag.getInteger("TeleX"), tag.getInteger("TeleY"), tag.getInteger("TeleZ"));
						if (world.isAirBlock(target.offset(EnumFacing.UP)) && world.isAirBlock(target.offset(EnumFacing.UP, 2))) {
							IBlockState state = world.getBlockState(target);
							if (state.getBlock() instanceof IStableBlock) {
								if (isEnergyAvailable(stack, player, world, 1000)) {
									player.setPositionAndUpdate(tag.getInteger("TeleX") + 0.5, tag.getInteger("TeleY") + 1, tag.getInteger("TeleZ") + 0.5);
									message = "Teleported!";
								} else {
									message = "hey";
								}
							} else {
								message = "calc.stableStone";
							}
						} else {
							message = "Destination is blocked";
						}
					} else {
						message = "calc.noPosition";
					}
				} else {
					message = "calc.dimension";
				}
                FontHelper.sendMessage(FontHelper.translate(message), world, player);
			} else {
				tag.setBoolean("click", false);
			}
		}
	}

	@Override
	public boolean onBlockClicked(ItemStack stack, NBTTagCompound tag, EntityPlayer player, World world, BlockPos pos, BlockInteraction interaction) {
		if (!world.isRemote) {
			if (player.isSneaking()) {
				Block target = world.getBlockState(pos).getBlock();
				if (target instanceof IStableBlock) {
					tag.setInteger("TeleX", pos.getX());
					tag.setInteger("TeleY", pos.getY());
					tag.setInteger("TeleZ", pos.getZ());
					tag.setInteger("Dimension", player.dimension);
					tag.setBoolean("Tele", true);
					FontHelper.sendMessage(FontHelper.translate("calc.position"), world, player);
				} else {
					FontHelper.sendMessage(FontHelper.translate("not stable stone"), world, player);
				}
			}
		}
		return true;
	}
}
