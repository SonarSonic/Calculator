package sonar.calculator.mod.common.item.calculators.modules;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.api.modules.IModuleClickable;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.helpers.FontHelper;

public class EnderModule extends ModuleBase implements IModuleClickable {

	@Override
	public String getName() {
		return "Ender";
	}

	@Override
	public String getClientName() {
		return FontHelper.translate("flawless.mode5");
	}

	@Override
	public void onModuleActivated(ItemStack stack, NBTTagCompound tag, World world, EntityPlayer player) {
		if (this.isEnergyAvailable(stack, player, world, 1000)) {
			world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.NEUTRAL, 0.5F,0.8F);
			if (!world.isRemote)
				world.spawnEntityInWorld(new EntityEnderPearl(world, player));
		}
	}

	@Override
	public boolean onBlockClicked(ItemStack stack, NBTTagCompound tag, EntityPlayer player, World world, BlockPos pos, BlockInteraction interaction) {
		return false;
	}

}
