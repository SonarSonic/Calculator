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
		if (event.world.provider.getDimensionId() == Calculator.saveDimension) {
			ResearchWorldData data = (ResearchWorldData) event.world.getPerWorldStorage().loadData(ResearchWorldData.class, ResearchWorldData.tag);
		}
	}

	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event) {
		if (event.world.provider.getDimensionId() == Calculator.saveDimension) {
			MapStorage storage = event.world.getPerWorldStorage();
			ResearchWorldData data = (ResearchWorldData) storage.loadData(ResearchWorldData.class, ResearchWorldData.tag);
			if (data == null) {
				storage.setData(ResearchWorldData.tag, new ResearchWorldData(ResearchWorldData.tag));
			}
		}
	}

	@SubscribeEvent
	public void Amethyst(BonemealEvent event) {
		if (event.block != null) {
			if (event.block == Calculator.amethystSapling) {
				if (!event.world.isRemote) {
					((CalculatorSaplings) Calculator.amethystSapling).generateTree(event.world, event.pos, event.block, event.world.rand);
					event.setResult(Result.ALLOW);
				}
			}
		}
	}

	@SubscribeEvent
	public void Tanzanite(BonemealEvent event) {
		if (event.block != null) {
			if (event.block == Calculator.tanzaniteSapling) {
				if (!event.world.isRemote) {
					((CalculatorSaplings) Calculator.tanzaniteSapling).generateTree(event.world, event.pos, event.block, event.world.rand);
					event.setResult(Result.ALLOW);
				}
			}
		}
	}

	@SubscribeEvent
	public void pear(BonemealEvent event) {
		if (event.block != null) {
			if (event.block == Calculator.pearSapling) {
				if (!event.world.isRemote) {
					((CalculatorSaplings) Calculator.pearSapling).generateTree(event.world, event.pos, event.block, event.world.rand);
					event.setResult(Result.ALLOW);
				}
			}
		}
	}

	@SubscribeEvent
	public void diamond(BonemealEvent event) {
		if (event.block != null) {
			if (event.block == Calculator.diamondSapling) {
				if (!event.world.isRemote) {
					((CalculatorSaplings) Calculator.diamondSapling).generateTree(event.world, event.pos, event.block, event.world.rand);
					event.setResult(Result.ALLOW);
				}
			}
		}
	}
}
