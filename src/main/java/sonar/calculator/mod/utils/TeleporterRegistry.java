package sonar.calculator.mod.utils;

import gnu.trove.map.hash.THashMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import sonar.calculator.mod.api.machines.ITeleport;
import sonar.calculator.mod.api.machines.TeleportLink;
import sonar.calculator.mod.common.tileentity.misc.TileEntityTeleporter;

public class TeleporterRegistry {

	private static Map<Integer, ITeleport> teleporters = new THashMap<Integer, ITeleport>();

	public static void removeAll() {
		teleporters.clear();
	}

	public static List<ITeleport> getTeleporters() {
		List<ITeleport> list = new ArrayList();
		for (Map.Entry<Integer, ITeleport> teleport : teleporters.entrySet()) {
			list.add(teleport.getValue());
		}
		return list;
	}

	public static void removeTeleporter(ITeleport teleport) {
		teleporters.remove(teleport);
	}

	public static void addTeleporter(ITeleport teleport) {
		teleporters.put(teleport.teleporterID(), teleport);
	}

	public static int nextID() {
		int nextID = 0;
		boolean flag = false;
		while (!flag) {
			nextID++;
			if (teleporters.get(nextID) == null) {
				flag = true;
			}
		}
		return nextID;

	}

	public static TileEntityTeleporter getTile(ITeleport teleport) {
		if (teleport == null) {
			return null;
		}
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		World world = server.worldServerForDimension(teleport.teleporterID());
		if (world != null) {
			TileEntity target = world.getTileEntity(teleport.getCoords().getBlockPos());
			return (TileEntityTeleporter) (target instanceof TileEntityTeleporter ? target : null);
		}
		return null;
	}

	public static ITeleport getPoint(ITeleport flux) {
		TileEntity target = getTile(flux);
		return (ITeleport) target;
	}

	public static List<TeleportLink> getTeleportLinks(int currentID) {
		List<ITeleport> teleports = getTeleporters();
		List<TeleportLink> list = new ArrayList();
		for (ITeleport teleport : teleports) {
			if (teleport != null && teleport.teleporterID() != currentID) {
				TileEntity tile = getTile(teleport);
				if (tile != null) {
					list.add(new TeleportLink(teleport.teleporterID(), teleport.name(), teleport.getCoords().getDimension()));
				} else {
					teleporters.remove(teleport.teleporterID());
				}
			}
		}
		Collections.sort(list, new Comparator<TeleportLink>() {
			public int compare(TeleportLink str1, TeleportLink str2) {
				int res = String.CASE_INSENSITIVE_ORDER.compare(str1.networkName, str2.networkName);
				if (res == 0) {
					res = str1.networkName.compareTo(str2.networkName);
				}
				return res;
			}
		});
		return list;

	}

}
