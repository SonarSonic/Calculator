package sonar.calculator.mod;

import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sonar.calculator.mod.common.block.CalculatorSaplings;
import sonar.calculator.mod.research.ResearchWorldData;

public class CalculatorEvents {

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		if (event.getWorld().provider.getDimension() == Calculator.saveDimension) {
			ResearchWorldData data = (ResearchWorldData) event.getWorld().getPerWorldStorage().getOrLoadData(ResearchWorldData.class, ResearchWorldData.tag);
		}
	}

	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event) {
		if (event.getWorld().provider.getDimension() == Calculator.saveDimension) {
			MapStorage storage = event.getWorld().getPerWorldStorage();
			ResearchWorldData data = (ResearchWorldData) storage.getOrLoadData(ResearchWorldData.class, ResearchWorldData.tag);
			if (data == null) {
				storage.setData(ResearchWorldData.tag, new ResearchWorldData(ResearchWorldData.tag));
			}
		}
	}

	@SubscribeEvent
	public void Amethyst(BonemealEvent event) {
		if (event.getBlock() != null) {
			if (event.getBlock() == Calculator.amethystSapling) {
				if (!event.getWorld().isRemote) {
					((CalculatorSaplings) Calculator.amethystSapling).generateTree(event.getWorld(), event.getPos(), event.getBlock(), event.getWorld().rand);
					event.setResult(Result.ALLOW);
				}
			}
		}
	}

	@SubscribeEvent
	public void Tanzanite(BonemealEvent event) {
		if (event.getBlock() != null) {
			if (event.getBlock() == Calculator.tanzaniteSapling) {
				if (!event.getWorld().isRemote) {
					((CalculatorSaplings) Calculator.tanzaniteSapling).generateTree(event.getWorld(), event.getPos(), event.getBlock(), event.getWorld().rand);
					event.setResult(Result.ALLOW);
				}
			}
		}
	}

	@SubscribeEvent
	public void pear(BonemealEvent event) {
		if (event.getBlock() != null) {
			if (event.getBlock() == Calculator.pearSapling) {
				if (!event.getWorld().isRemote) {
					((CalculatorSaplings) Calculator.pearSapling).generateTree(event.getWorld(), event.getPos(), event.getBlock(), event.getWorld().rand);
					event.setResult(Result.ALLOW);
				}
			}
		}
	}

	@SubscribeEvent
	public void diamond(BonemealEvent event) {
		if (event.getBlock() != null) {
			if (event.getBlock() == Calculator.diamondSapling) {
				if (!event.getWorld().isRemote) {
					((CalculatorSaplings) Calculator.diamondSapling).generateTree(event.getWorld(), event.getPos(), event.getBlock(), event.getWorld().rand);
					event.setResult(Result.ALLOW);
				}
			}
		}
	}
}
