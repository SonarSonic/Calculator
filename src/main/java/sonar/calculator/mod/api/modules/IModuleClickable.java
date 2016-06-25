package sonar.calculator.mod.api.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.core.api.utils.BlockInteraction;

/**for modules which can do something on left/right click*/
public interface IModuleClickable extends IModule {

	public void onModuleActivated(ItemStack stack, NBTTagCompound modeTag, World world, EntityPlayer player);

	public boolean onBlockClicked(ItemStack stack, NBTTagCompound modeTag, EntityPlayer player, World world, BlockPos pos, BlockInteraction interaction);

}
