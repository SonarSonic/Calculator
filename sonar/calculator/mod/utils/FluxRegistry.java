package sonar.calculator.mod.utils;

import gnu.trove.map.hash.THashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sonar.calculator.mod.api.IFlux;
import sonar.calculator.mod.api.IFluxPlug;
import sonar.calculator.mod.api.IFluxPoint;

public class FluxRegistry {

	private static Map<Integer, List<IFlux>> points = new THashMap<Integer, List<IFlux>>();
	private static Map<Integer, List<IFlux>> plugs = new THashMap<Integer, List<IFlux>>();
	private static Map<Integer, IFlux> masters = new THashMap<Integer, IFlux>();
	private static Map<Integer, Integer> dimensions = new THashMap<Integer, Integer>();
	public static void removeAll() {
		points.clear();
		plugs.clear();
		masters.clear();
		dimensions.clear();
	}

	public static List<IFlux> getPoints(int freq) {
		if (freq <= 0) {
			return null;
		}
		return points.get(freq);
	}

	public static List<IFlux> getPlugs(int freq) {
		if (freq <= 0) {
			return null;
		}
		return plugs.get(freq);
	}

	public static IFlux getMaster(int freq) {
		if (freq <= 0) {
			return null;
		}

		return masters.get(freq);
	}

	public static int getDimension(int freq) {
		if (freq <= 0) {
			return 0;
		}
		if(dimensions.get(freq)==null){
			return 0;
		}
		return dimensions.get(freq);
	}
	public static void addFlux(IFlux flux) {
		if (flux != null) {
			if (flux instanceof IFluxPlug) {
				if (plugs.get(flux.freq()) == null) {
					plugs.put(flux.freq(), new ArrayList());
				}
				if (!plugs.get(flux.freq()).contains(flux)) {
					plugs.get(flux.freq()).add(flux);
				} else {
					plugs.get(flux.freq()).remove(flux);
					plugs.get(flux.freq()).add(flux);
				}
			} else if (flux instanceof IFluxPoint) {
				if (points.get(flux.freq()) == null) {
					points.put(flux.freq(), new ArrayList());
				}
				if (!points.get(flux.freq()).contains(flux)) {
					points.get(flux.freq()).add(flux);
				} else {
					points.get(flux.freq()).remove(flux);
					points.get(flux.freq()).add(flux);
				}
			} 
		}
	}

	public static void addMaster(IFlux plug) {
		if (plug == null) {
			return;
		}
		masters.put(plug.freq(), plug);
	}
	public static void addDimension(int freq, int dimension) {
		if (freq <= 0) {
			return ;
		}
		dimensions.put(freq, dimension);
	}
	public static void removeFlux(IFlux flux) {
		if (flux != null) {
			if (flux instanceof IFluxPlug) {
				if (plugs.get(flux.freq()) == null) {
					return;
				}
				plugs.get(flux.freq()).remove(flux);

			} else if (flux instanceof IFluxPoint) {
				if (points.get(flux.freq()) == null) {
					return;
				}
				points.get(flux.freq()).remove(flux);

			}
		}
	}

	public static void removeMaster(IFlux point) {
		masters.remove(point.freq());
	}

	public static void removeDimension(int freq, int dimension) {
		dimensions.remove(freq, dimension);
	}
	public static int plugCount(int freq) {
		List<IFlux> plugs = getPlugs(freq);
		if (plugs != null) {
			return plugs.size();
		} else {
			return 0;
		}
	}

	public static int pointCount(int freq) {
		List<IFlux> points = getPoints(freq);
		if (points != null) {
			return points.size();
		} else {
			return 0;
		}
	}
}
