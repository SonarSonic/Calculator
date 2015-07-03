package sonar.calculator.mod.integration.waila;

import java.text.DecimalFormat;
import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse;
import sonar.core.utils.helpers.FontHelper;

public class HUDGreenhouse implements IWailaDataProvider {


	DecimalFormat dec = new DecimalFormat("##.##");
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
			int oxygen = tile.getOxygen();
			int carbon = tag.getInteger("Carbon");
			if (carbon != 0) {
				String carbonString = FontHelper.translate("greenhouse.carbon") + ": "
						+ dec.format(carbon * 100 / 100000) + "%";
				currenttip.add(carbonString);
			}
			if (oxygen != 0) {
				String oxygenString = FontHelper.translate("greenhouse.oxygen") + ": "
						+ dec.format(oxygen * 100 / 100000) + "%";
				currenttip.add(oxygenString);
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
