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
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;

public class HUDCalculatorLocator implements IWailaDataProvider {

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

		if (accessor.getTileEntity() instanceof TileEntityCalculatorLocator) {
			TileEntityCalculatorLocator tile = (TileEntityCalculatorLocator) handler;
			NBTTagCompound tag = new NBTTagCompound();
			tile.writeToNBT(tag);
			int active = tag.getInteger("active");
			if (active == 0) {
				currenttip.add(StatCollector.translateToLocal("locator.active")+": " + StatCollector.translateToLocal("locator.false"));
			}
			if (active == 1) {
				currenttip.add(StatCollector.translateToLocal("locator.active")+": " + StatCollector.translateToLocal("locator.true"));
			}
			String user = tile.ownerUsername();
			if (user != "None") {
				currenttip.add(StatCollector.translateToLocal("locator.owner")+": " + user);
			}
			if (user == "None") {
				currenttip.add(StatCollector.translateToLocal("locator.owner")+": " + StatCollector.translateToLocal("locator.none"));

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
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te,
			NBTTagCompound tag, World world, int x, int y, int z) {
		return tag;
	}
}
