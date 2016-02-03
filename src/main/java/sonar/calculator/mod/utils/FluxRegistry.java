package sonar.calculator.mod.utils;

import gnu.trove.map.hash.THashMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.api.flux.IFlux;
import sonar.calculator.mod.api.flux.IFluxPlug;
import sonar.calculator.mod.api.flux.IFluxPoint;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.utils.helpers.FluxHelper;

public class FluxRegistry {

	private static Map<Integer, List<IFlux>> points = new THashMap<Integer, List<IFlux>>();
	private static Map<Integer, List<IFlux>> plugs = new THashMap<Integer, List<IFlux>>();
	private static Map<Integer, IFlux> masters = new THashMap<Integer, IFlux>();
	private static Map<Integer, IFlux> controllers = new THashMap<Integer, IFlux>();
	private static Map<Integer, String> networksNAME = new THashMap<Integer, String>();
	private static Map<String, Integer> networksID = new THashMap<String, Integer>();

	public static void removeAll() {
		points.clear();
		plugs.clear();
		masters.clear();
		controllers.clear();
		networksNAME.clear();
		networksID.clear();
	}

	public static List<IFlux> getPoints(int networkID) {
		if (networkID <= 0) {
			return null;
		}
		return points.get(networkID);
	}

	public static List<IFlux> getPlugs(int networkID) {
		if (networkID <= 0) {
			return null;
		}
		return plugs.get(networkID);
	}

	public static IFlux getMaster(int networkID) {
		if (networkID <= 0) {
			return null;
		}

		return masters.get(networkID);
	}

	public static IFlux getController(int networkID) {
		if (networkID <= 0) {
			return null;
		}

		return controllers.get(networkID);
	}

	public static String getNetwork(int networkID) {
		if (networkID <= 0) {
			return "NETWORK";
		}
		if (networksNAME.get(networkID) == null || networksNAME.get(networkID).isEmpty()) {
			return "NETWORK";
		}
		return networksNAME.get(networkID);
	}

	public static int getNetwork(String networkName, String masterName) {
		if (networkName == null || networkName.isEmpty() || networkName == "NETWORK" || masterName == null || masterName.isEmpty()) {
			return 0;
		}
		if (networksID.get(networkName) == null) {
			return 0;
		}
		return validPlayer(networksID.get(networkName), masterName) ? networksID.get(networkName) : 0;
	}

	public static boolean validPlayer(int networkID, String masterName) {
		TileEntityFluxController controller = FluxHelper.getController(networkID);
		if (controller != null && controller.validPlayer(masterName)) {
			return true;
		} else if (controller != null) {
			return false;
		}
		TileEntity master = FluxHelper.getTile(FluxRegistry.getMaster(networkID));
		if (master != null && master instanceof TileEntityFlux) {
			TileEntityFlux flux = (TileEntityFlux) master;
			return flux.playerName.equals(masterName);
		} else if (master == null) {
			return true;
		}
		return false;
	}

	public static boolean renameNetwork(String masterName, String networkName, String newName) {
		int networkID = getNetwork(networkName, masterName);
		networksNAME.put(networkID, newName);
		networksID.remove(networkName, networkID);
		networksID.put(newName, networkID);
		return false;
	}

	public static int createNetwork(String networkName) {
		if (networkName.isEmpty() || networkName.equals("NETWORK")) {
			return 0;
		}
		int networkID = networksNAME.size() + 1;
		networksNAME.put(networkID, networkName);
		networksID.put(networkName, networkID);
		return networkID;
	}

	public static void addFlux(IFlux flux) {
		if (flux != null && flux.networkID() != 0) {
			if (flux instanceof IFluxPlug) {
				if (plugs.get(flux.networkID()) == null) {
					plugs.put(flux.networkID(), new ArrayList());
				}
				if (!plugs.get(flux.networkID()).contains(flux)) {
					plugs.get(flux.networkID()).add(flux);
				} else {
					plugs.get(flux.networkID()).remove(flux);
					plugs.get(flux.networkID()).add(flux);
				}
			} else if (flux instanceof IFluxPoint) {
				if (points.get(flux.networkID()) == null) {
					points.put(flux.networkID(), new ArrayList());
				}
				if (!points.get(flux.networkID()).contains(flux)) {
					points.get(flux.networkID()).add(flux);
				} else {
					points.get(flux.networkID()).remove(flux);
					points.get(flux.networkID()).add(flux);
				}
			}
		}
	}

	public static void addMaster(IFlux flux) {
		if (flux != null && flux.networkID() != 0) {
			masters.put(flux.networkID(), flux);
		}
	}

	public static void addController(IFlux flux) {
		if (flux != null && flux.networkID() != 0) {
			controllers.put(flux.networkID(), flux);
		}
	}

	public static void removeFlux(IFlux flux) {
		if (flux != null) {
			if (flux instanceof IFluxPlug) {
				if (plugs.get(flux.networkID()) == null) {
					return;
				}
				plugs.get(flux.networkID()).remove(flux);

			} else if (flux instanceof IFluxPoint) {
				if (points.get(flux.networkID()) == null) {
					return;
				}
				points.get(flux.networkID()).remove(flux);
			}
		}
	}

	public static void removeMaster(IFlux flux) {
		masters.remove(flux.networkID());
	}

	public static void removeController(IFlux flux) {
		controllers.remove(flux.networkID());
	}

	public static int plugCount(int networkID) {
		List<IFlux> plugs = getPlugs(networkID);
		if (plugs != null) {
			return plugs.size();
		} else {
			return 0;
		}
	}

	public static int pointCount(int networkID) {
		List<IFlux> points = getPoints(networkID);
		if (points != null) {
			return points.size();
		} else {
			return 0;
		}
	}

	public static List<FluxNetwork> getAvailableNetworks(String player, IFlux flux) {
		List<FluxNetwork> available = new ArrayList();
		for (int i = 0; i <= networksNAME.size(); i++) {
			if (getNetwork(i) != null && getNetwork(i) != "NETWORK") {
				if (validPlayer(i, player)) {
					TileEntityFluxController controller = FluxHelper.getController(i);
					if (controller != null) {
						if (flux == null || flux.equals(controller)) {
							available.add(new FluxNetwork(i, getNetwork(i), controller.playerProtect));
						}
					} else {
						available.add(new FluxNetwork(i, getNetwork(i), 1));
					}
				}
			}
		}

		Collections.sort(available, new Comparator<FluxNetwork>() {
			public int compare(FluxNetwork str1, FluxNetwork str2) {
				int res = String.CASE_INSENSITIVE_ORDER.compare(str1.networkName, str2.networkName);
				if (res == 0) {
					res = str1.networkName.compareTo(str2.networkName);
				}
				return res;
			}
		});
		return available;
	}
}
