package sonar.calculator.mod.integration.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.core.utils.helpers.FontHelper;

public class HUDCO2Generator implements IWailaDataProvider {

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

		if (accessor.getTileEntity() instanceof TileEntityCO2Generator) {
			TileEntityCO2Generator tile = (TileEntityCO2Generator) handler;
			NBTTagCompound tag = new NBTTagCompound();
			tile.writeToNBT(tag);
			int burnTime = tag.getInteger("burnTime");
			int maxBurn = tag.getInteger("maxBurnTime");

			if (burnTime > 0 && maxBurn != 0 && tile.gasAdd == 0) {
				String burn = FontHelper.translate("co2.control");
				currenttip.add(burn);
			}

			else if (burnTime > 0 && maxBurn != 0) {
				String burn = FontHelper.translate("co2.burnt")+": " + burnTime * 100 / maxBurn;
				currenttip.add(burn);
			} else {
				String burn = FontHelper.translate("co2.burning");
				currenttip.add(burn);
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
