package sonar.calculator.mod;

import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sonar.calculator.mod.common.block.CalculatorSaplings;

public class CalculatorEvents {
/*
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer && PlayerResearch.get((EntityPlayer) event.entity) == null)
			PlayerResearch.register((EntityPlayer) event.entity);

		if (event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(PlayerResearch.tagName) == null)
			event.entity.registerExtendedProperties(PlayerResearch.tagName, new PlayerResearch((EntityPlayer) event.entity));
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		PlayerResearch.get(event.entityPlayer).copy(PlayerResearch.get(event.original));
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayer && !event.entity.worldObj.isRemote) {
			// sync data packet
		}
	}
	@SubscribeEvent
	public void CalculatorCraftingCraft(ItemCraftedEvent event) {
		EntityPlayer player = event.player;
		if (player != null) {
			ItemStack item = player.getHeldItem();

			if ((item != null) && (item.getItem().equals(Calculator.itemCraftingCalculator)) && ((player.openContainer instanceof ICalculatorCrafter))) {
					((ICalculatorCrafter)player.openContainer).removeEnergy();
			
			}
		}
		
	}
	*/
	@SubscribeEvent
	public void Amethyst(BonemealEvent event) {
		if (event.block != null) {
			if (event.block == Calculator.AmethystSapling) {				
				if (!event.world.isRemote) {
					((CalculatorSaplings) Calculator.AmethystSapling).growTree(event.world, event.pos, event.world.rand);
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
					((CalculatorSaplings) Calculator.tanzaniteSapling).growTree(event.world, event.pos, event.world.rand);
					event.setResult(Result.ALLOW);
				}
			}
		}
	}

	@SubscribeEvent
	public void pear(BonemealEvent event) {
		if (event.block != null) {
			if (event.block == Calculator.PearSapling) {
				if (!event.world.isRemote) {
					((CalculatorSaplings) Calculator.PearSapling).growTree(event.world, event.pos, event.world.rand);
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
					((CalculatorSaplings) Calculator.diamondSapling).growTree(event.world, event.pos, event.world.rand);
					event.setResult(Result.ALLOW);
				}
			}
		}
	}
}
