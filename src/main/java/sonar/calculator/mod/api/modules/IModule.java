package sonar.calculator.mod.api.modules;

import sonar.core.api.IRegistryObject;
import sonar.core.utils.BlockInteraction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**all modules must implement this*/
public interface IModule extends IRegistryObject {
	
	public String getClientName();
	
}
