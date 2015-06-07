package sonar.calculator.mod.integration.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse;

public class HUDGreenhouseState implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {

		return currenttip;
	}
	@Override
	public List<String> getWailaBody(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		TileEntity handler = accessor.getTileEntity();
		if (handler == null)
			return currenttip;

		if (accessor.getTileEntity() instanceof TileEntityGreenhouse) {
			TileEntityGreenhouse tile = (TileEntityGreenhouse) handler;
			NBTTagCompound tag = new NBTTagCompound();
			tile.writeToNBT(tag);
			int multi = tag.getInteger("Multi");
			switch(multi){
			case -1:currenttip.add(StatCollector.translateToLocal("locator.state") + ": " + StatCollector.translateToLocal("greenhouse.building"));	break;
			case 0:currenttip.add(StatCollector.translateToLocal("locator.state") + ": " + StatCollector.translateToLocal("greenhouse.incomplete"));	break;
			case 1:currenttip.add(StatCollector.translateToLocal("locator.state") + ": " + StatCollector.translateToLocal("greenhouse.complete"));	break;
			case 2:currenttip.add(StatCollector.translateToLocal("locator.state") + ": " + StatCollector.translateToLocal("greenhouse.complete"));	break;
			
			}
		
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return currenttip;
	}


	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
		if (te != null)
			te.writeToNBT(tag);
		return tag;
	}	
}
