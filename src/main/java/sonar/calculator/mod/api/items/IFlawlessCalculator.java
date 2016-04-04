package sonar.calculator.mod.api.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.api.modules.IModule;

public interface IFlawlessCalculator extends IModuleProvider {

	/** performs the removal of a {@link IModule}
	 * @param stack the Flawless Calculator
	 * @param slot the slot you wish to remove from
	 * @return the item associated with the removed module with NBT set */
	public ItemStack removeModule(ItemStack stack, int slot);

	/** performs the addition of a given {@link IModule}
	 * @param stack the Flawless Calculator
	 * @param moduleTag the current module tag compound
	 * @param module the {@link IModule} to add
	 * @param slot the slot you wish to add to */
	public void addModule(ItemStack stack, NBTTagCompound moduleTag, IModule module, int slot);

	/** checks if a {@link IModule} can be removed from the calculator
	 * @param stack the Flawless Calculator
	 * @param slot the slot you wish to remove to
	 * @return if the module can be removed */
	public boolean canRemoveModule(ItemStack stack, int slot);

	/** checks if a {@link IModule} can be added to the calculator
	 * @param stack the Flawless Calculator
	 * @param module the {@link IModule} to add
	 * @param slot the slot you wish to add to
	 * @return if the module can be added */
	public boolean canAddModule(ItemStack stack, IModule module, int slot);

	/** returns the NBTTag associated with a module in a given slot
	 * @param stack the Flawless Calculator
	 * @param slot the slot you wish to retrieve from
	 * @return */
	public NBTTagCompound getModuleTag(ItemStack stack, int slot);

	/** returns the current {@link IModule}
	 * @param stack the Flawless Calculator
	 * @return */
	public IModule getCurrentModule(ItemStack stack);

	/** returns the current position of the current {@link IModule} 
	 * @param stack the Flawless Calculator
	 * @return */
	public int getCurrentSlot(ItemStack stack);
}
