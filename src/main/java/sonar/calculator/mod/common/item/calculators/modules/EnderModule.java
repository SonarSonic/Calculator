package sonar.calculator.mod.common.item.calculators.modules;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.api.modules.IModuleClickable;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.BlockInteraction;

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
			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F);
			if (!world.isRemote)
				world.spawnEntityInWorld(new EntityEnderPearl(world, player));
		}
	}

	@Override
	public boolean onBlockClicked(ItemStack stack, NBTTagCompound tag, EntityPlayer player, World world, BlockPos pos, BlockInteraction interaction) {
		return false;
	}

}
