package sonar.calculator.mod.common.tileentity.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cofh.api.energy.EnergyStorage;
import sonar.core.common.tileentity.TileEntityInventory;

public class TileEntityCalculator extends TileEntityInventory {
	
	public static class Dynamic extends TileEntityCalculator {
		public Dynamic(){
		super.slots = new ItemStack[10];
		}
	}
	public static class Atomic extends TileEntityCalculator {
		public Atomic(){
		super.slots = new ItemStack[4];
		}
	}
}
