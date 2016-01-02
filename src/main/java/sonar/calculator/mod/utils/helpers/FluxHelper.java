package sonar.calculator.mod.utils.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.api.IFlux;
import sonar.calculator.mod.api.IFluxPlug;
import sonar.calculator.mod.api.IFluxPoint;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.TileEntityFlux.TransferList;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPlug;
import sonar.calculator.mod.utils.FluxRegistry;

public class FluxHelper {

	public static TileEntity getTile(IFlux flux) {
		if (flux == null) {
			return null;
		}
		MinecraftServer server = MinecraftServer.getServer();
		World world = server.worldServerForDimension(flux.dimension());
		if (world != null) {
			TileEntity target = world.getTileEntity(flux.xCoord(), flux.yCoord(), flux.zCoord());
			return target;
		}
		return null;
	}

	public static IFluxPoint getPoint(IFlux flux) {
		TileEntity target = getTile(flux);
		if (target != null && target instanceof IFluxPoint) {
			return (IFluxPoint) target;
		}
		return null;
	}

	public static IFluxPlug getPlug(IFlux flux) {
		TileEntity target = getTile(flux);
		if (target != null && target instanceof IFluxPlug) {
			return (IFluxPlug) target;
		}
		return null;
	}

	public static TileEntityFluxController getController(int networkID) {
		TileEntity target = getTile(FluxRegistry.getController(networkID));

		if (target != null && target instanceof TileEntityFluxController) {
			return (TileEntityFluxController) target;
		}
		return null;
	}

	public static List<IFluxPoint> getPoints(int networkID) {
		List<IFlux> fluxList = FluxRegistry.getPoints(networkID);

		if (fluxList == null) {
			return null;
		}

		List<IFluxPoint> points = new ArrayList();
		for (int i = 0; i < fluxList.size(); i++) {
			IFluxPoint point = FluxHelper.getPoint(fluxList.get(i));
			if (point != null && point.networkID() == networkID) {
				points.add(point);
			}
		}
		Collections.sort(points, new Comparator<IFluxPoint>() {
			public int compare(IFluxPoint o1, IFluxPoint o2) {
				return o2.priority() - o1.priority();
			}
		});

		return points;
	}

	public static List<IFluxPlug> getPlugs(int networkID) {

		List<IFlux> fluxList = FluxRegistry.getPlugs(networkID);
		if (fluxList == null || fluxList.size() == 0) {
			return null;
		}
		List<IFluxPlug> plugs = new ArrayList();
		for (int i = 0; i < fluxList.size(); i++) {
			IFluxPlug plug = FluxHelper.getPlug(fluxList.get(i));
			if (plug != null && plug.networkID() == networkID) {
				plugs.add(plug);
			}
		}
		TileEntityFluxController controller = FluxHelper.getController(networkID);
		if (controller == null || controller != null && controller.sendMode == 0) {
			return plugs;

		} else if (controller.sendMode == 2) {
			Collections.sort(plugs, new Comparator<IFluxPlug>() {
				public int compare(IFluxPlug o1, IFluxPlug o2) {
					return o2.pullEnergy(TileEntityFluxPlug.maxTransfer, true, true) - o1.pullEnergy(TileEntityFluxPlug.maxTransfer, true, true);
				}
			});
			return plugs;

		} else {
			Collections.sort(plugs, new Comparator<IFluxPlug>() {
				public int compare(IFluxPlug o1, IFluxPlug o2) {
					return o1.pullEnergy(TileEntityFluxPlug.maxTransfer, true, true) - o2.pullEnergy(TileEntityFluxPlug.maxTransfer, true, true);
				}
			});
			return plugs;
		}
	}

	public static int getMaxOutput(int networkID) {
		List<IFlux> points = FluxRegistry.getPoints(networkID);
		int output = 0;
		if (points == null || points.size() == 0) {
			return 0;
		}
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i) != null) {
				TileEntity target = FluxHelper.getTile(points.get(i));
				if (target != null && target instanceof IFluxPoint) {
					IFluxPoint point = (IFluxPoint) target;
					int transfer = Math.min(TileEntityFluxPlug.maxTransfer, point.maxTransfer());
					output += transfer - point.pushEnergy(transfer, true);
				}

			}
		}

		return output;
	}

	public static TransferList getMaxInput(int networkID) {
		List<IFluxPlug> plugs = getPlugs(networkID);
		if (plugs == null || plugs.size() == 0) {
			return new TransferList(new int[0], 0);
		}
		int input = 0;
		int[] inputList = new int[plugs.size()];
		for (int i = 0; i < plugs.size(); i++) {
			input += TileEntityFluxPlug.maxTransfer - plugs.get(i).pullEnergy(TileEntityFluxPlug.maxTransfer, true, false);
			inputList[i] = TileEntityFluxPlug.maxTransfer - plugs.get(i).pullEnergy(TileEntityFluxPlug.maxTransfer, true, true);

		}
		return new TransferList(inputList, input);
	}
	public static long getBuffer(int networkID) {
		List<IFluxPlug> plugs = getPlugs(networkID);
		if (plugs == null || plugs.size() == 0) {
			return 0;
		}
		long buffer = 0;
		int[] inputList = new int[plugs.size()];
		for (int i = 0; i < plugs.size(); i++) {
			buffer+= TileEntityFluxPlug.maxTransfer-plugs.get(i).getBuffer(TileEntityFluxPlug.maxTransfer, true);

		}
		return buffer;
	}

	public static boolean checkPlayerName(String player, int networkID) {
		TileEntityFluxController controller = FluxHelper.getController(networkID);
		if (controller == null) {
			return true;
		} else {
			return controller.validPlayer(player);
		}
	}

	public static boolean checkPlayerName(TileEntity flux, int networkName) {
		if (flux == null || !(flux instanceof TileEntityFlux)) {
			return true;
		}
		TileEntityFluxController controller = FluxHelper.getController(networkName);
		if (controller == null) {
			return true;
		} else {
			return controller.validPlayer(((TileEntityFlux) flux).playerName);
		}
	}

}
