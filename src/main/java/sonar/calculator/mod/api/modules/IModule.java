package sonar.calculator.mod.api.modules;

import sonar.core.utils.BlockInteraction;
import sonar.core.utils.IRegistryObject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**all modules must implement this*/
public interface IModule extends IRegistryObject {
	
	public String getClientName();
	
}
