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
import sonar.calculator.mod.api.IUpgradeCircuits;
import sonar.core.utils.helpers.FontHelper;

public class HUDCircuitUpgrades implements IWailaDataProvider {

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

		if (accessor.getTileEntity() instanceof IUpgradeCircuits) {
			
			IUpgradeCircuits tile = (IUpgradeCircuits) handler;
			NBTTagCompound tag = new NBTTagCompound();
			accessor.getTileEntity().writeToNBT(tag);
			int speedint = tag.getInteger("sUpgrade");
			int energyint = tag.getInteger("eUpgrade");
			int voidint = tag.getShort("vUpgrade");
			if(speedint!=0){
			String speed = FontHelper.translate("circuit.speed")+": " + speedint;
			
			currenttip.add(speed);
			}

			if(energyint!=0){
			String energy = FontHelper.translate("circuit.energy")+": " + energyint;
			currenttip.add(energy);
			}
			
			if(voidint!=0){
			String voidString = FontHelper.translate("circuit.void")+": " + FontHelper.translate("circuit.installed");
			currenttip.add(voidString);
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
