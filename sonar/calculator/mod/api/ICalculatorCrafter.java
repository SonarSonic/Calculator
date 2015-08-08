package sonar.calculator.mod.api;

import net.minecraft.inventory.IInventory;

public interface ICalculatorCrafter {

	public void removeEnergy();	

	public int maxCraft();

    public void onCraftMatrixChanged(IInventory inv);
}
