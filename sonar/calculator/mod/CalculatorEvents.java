package sonar.calculator.mod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import sonar.calculator.mod.common.block.CalculatorSaplings;
import sonar.calculator.mod.common.containers.ContainerCalculator;
import sonar.calculator.mod.common.containers.ContainerCraftingCalculator;
import sonar.calculator.mod.common.containers.ContainerScientificCalculator;
import sonar.calculator.mod.utils.FluxRegistry;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CalculatorEvents {


	@SubscribeEvent	
	public void CalculatorCraftingCraft(ItemCraftedEvent event) {
		EntityPlayer player = event.player;
		if (player != null) {
			if (!player.capabilities.isCreativeMode) {
				ItemStack item = player.getHeldItem();

				if ((item != null) && (item.getItem().equals(Calculator.itemCraftingCalculator)) && ((player.openContainer instanceof ContainerCraftingCalculator))) {
					int energy = item.stackTagCompound.getInteger("Energy");

					if (energy > 0) {
						item.stackTagCompound.setInteger("Energy", energy - 1);
					}

				}
			}
		}
	}

	@SubscribeEvent
	public void CalculatorCraft(ItemCraftedEvent event) {
		EntityPlayer player = event.player;
		if (player != null) {
			if (!player.capabilities.isCreativeMode) {
				ItemStack item = player.getHeldItem();

				if ((item != null) && (item.getItem().equals(Calculator.itemCalculator)) && ((player.openContainer instanceof ContainerCalculator))) {
					int energy = item.stackTagCompound.getInteger("Energy");

					if (energy > 0) {
						item.stackTagCompound.setInteger("Energy", energy - 1);
					}

				}
			}
		}
	}

	@SubscribeEvent
	public void ScientificCalculatorCraft(ItemCraftedEvent event) {
		EntityPlayer player = event.player;
		if (player != null) {
			if (!player.capabilities.isCreativeMode) {
				ItemStack item = player.getHeldItem();

				if ((item != null) && (item.getItem().equals(Calculator.itemScientificCalculator)) && ((player.openContainer instanceof ContainerScientificCalculator))) {
					int energy = item.stackTagCompound.getInteger("Energy");

					if (energy > 0) {
						item.stackTagCompound.setInteger("Energy", energy - 1);
					}

				}
			}
		}
	}

	@SubscribeEvent
	public void Amethyst(BonemealEvent event) {
		if (event.block != null) {
			if (event.block == Calculator.AmethystSapling) {
				if (!event.world.isRemote) {
					((CalculatorSaplings) Calculator.AmethystSapling).growTree(event.world, event.x, event.y, event.z, event.world.rand);
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
					((CalculatorSaplings) Calculator.tanzaniteSapling).growTree(event.world, event.x, event.y, event.z, event.world.rand);
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
					((CalculatorSaplings) Calculator.PearSapling).growTree(event.world, event.x, event.y, event.z, event.world.rand);
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
					((CalculatorSaplings) Calculator.diamondSapling).growTree(event.world, event.x, event.y, event.z, event.world.rand);
					event.setResult(Result.ALLOW);
				}
			}
		}
	}
}
