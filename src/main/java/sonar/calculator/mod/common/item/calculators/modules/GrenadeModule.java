package sonar.calculator.mod.common.item.calculators.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.api.modules.IModuleClickable;
import sonar.calculator.mod.common.entities.EntityGrenade;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.helpers.FontHelper;

public class GrenadeModule extends ModuleBase implements IModuleClickable {

	@Override
	public boolean isLoadable() {
		return true;
	}

	@Override
	public String getName() {
		return "Grenade";
	}

	@Override
	public String getClientName() {
		return FontHelper.translate("flawless.mode4");
	}

	@Override
	public void onModuleActivated(ItemStack stack, NBTTagCompound tag, World world, EntityPlayer player) {
		if (isEnergyAvailable(stack, player, world, 10000)) {
			if (CalculatorConfig.enableGrenades) {
				world.playSoundAtEntity(player, "random.fizz", 0.7F, 0.8F);
				if (!world.isRemote) {
					world.spawnEntityInWorld(new EntityGrenade(world, player));
					this.extractEnergy(stack, player, 10000, false);
				}
			} else {
				FontHelper.sendMessage(FontHelper.translate("calc.ban"), world, player);
			}
		}
	}

	@Override
	public boolean onBlockClicked(ItemStack stack, NBTTagCompound tag, EntityPlayer player, World world, BlockPos pos, BlockInteraction interaction) {
		return false;
	}
}
