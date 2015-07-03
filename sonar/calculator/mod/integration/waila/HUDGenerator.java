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
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.core.utils.helpers.FontHelper;

public class HUDGenerator implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
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

		if (accessor.getTileEntity() instanceof TileEntityGenerator.StarchExtractor) {
			TileEntityGenerator tile = (TileEntityGenerator) handler;
			NBTTagCompound tag = new NBTTagCompound();
			tile.writeToNBT(tag);
			int level = tag.getInteger("ItemLevel") * 100 / 5000;
			if (tile instanceof TileEntityGenerator.RedstoneExtractor) {
				String points = FontHelper.translate("generator.starch") + ": " + level + "%";
				currenttip.add(points);
			} else if (tile instanceof TileEntityGenerator.GlowstoneExtractor) {
				String points = FontHelper.translate("generator.glowstone") + ": " + level + "%";
				currenttip.add(points);
			} else {
				String points = FontHelper.translate("generator.redstone") + ": " + level + "%";
				currenttip.add(points);
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
