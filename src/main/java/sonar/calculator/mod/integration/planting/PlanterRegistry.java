package sonar.calculator.mod.integration.planting;

import gnu.trove.map.hash.THashMap;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PlanterRegistry {

	public static final IPlanter defaultPlanter = new DefaultPlanter();

	private static Map<String, IPlanter> planters = new THashMap<String, IPlanter>();

	public static void registerPlanters() {
		addPlanter("Natura", new NaturaPlanter());
	}

	public static void addPlanter(String modid, IPlanter planter) {
		planters.put(modid, planter);
	}

	public static IPlanter getPlanter(ItemStack stack) {
		if (stack.getItem() == null || GameRegistry.findUniqueIdentifierFor(stack.getItem()) == null) {
			return defaultPlanter;
		} else {
			IPlanter planter = planters.get(GameRegistry.findUniqueIdentifierFor(stack.getItem()).modId);
			return planter != null ? planter : defaultPlanter;
		}

	}

	public static class DefaultPlanter implements IPlanter {

		@Override
		public Block getCropFromStack(ItemStack stack) {
			if (stack.getItem() instanceof IPlantable) {
				return ((IPlantable) stack.getItem()).getPlant(null, 0, 0, 0);
			}
			return null;
		}

		@Override
		public int getMetaFromStack(ItemStack stack) {
			if (stack.getItem() instanceof IPlantable) {
				return ((IPlantable) stack.getItem()).getPlantMetadata(null, 0, 0, 0);
			}
			return 0;
		}

	}

}
