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
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;

public class HUDCalculatorPlug implements IWailaDataProvider {

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

		if (accessor.getTileEntity() instanceof TileEntityCalculatorPlug) {
			TileEntityCalculatorPlug tile = (TileEntityCalculatorPlug) handler;
			NBTTagCompound tag = new NBTTagCompound();
			tile.writeToNBT(tag);
			int stable = tag.getInteger("Stable");
			currenttip.add(getString(stable));

		}
		return currenttip;
	}

	private String getString(int stable) {
		if (stable == 0) {
			return StatCollector.translateToLocal("circuit.stable")+": " + StatCollector.translateToLocal("circuit.noStability");
		}
		if (stable == 1){
			return StatCollector.translateToLocal("circuit.stable")+": " + StatCollector.translateToLocal("locator.false");
		}
		if (stable == 2) {
			return StatCollector.translateToLocal("circuit.stable")+": " + StatCollector.translateToLocal("locator.true");
		}

		return StatCollector.translateToLocal("circuit.stable")+": " + StatCollector.translateToLocal("locator.unknown");
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
