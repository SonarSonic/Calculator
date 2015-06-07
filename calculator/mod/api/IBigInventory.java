package sonar.calculator.mod.api;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**only used in Storage Chambers at the moment*/
public interface IBigInventory extends IInventory {

	/** for use in rendering the stack
	 * @param slot id of the slot
	 * @return ItemStack with the amount stored set as the stack size
	 */
	public ItemStack getFullStack(int slot);	
	/**
	 * @param slot id of the slot
	 * @return ItemStack with the usable amount which respects max stacksize as the stack size
	 */
	public ItemStack getSlotStack(int slot);

	/** called when an entire stack has been removed updates the saved stack in the inventory 
	 * @param removed id of the slot which was just removed
	 */
	public void resetSavedStack(int removed);	

	/**
	 * @return a copy of the ItemStack which is being stored
	 */
	public ItemStack getSavedStack();	

	/** set the current stored stack, overriding the previous save
	 * @param stack the ItemStack you wish to set the saved stack to.
	 */
	public void setSavedStack(ItemStack stack);	

	/**checking if a item matches the stored stack, the correct place to implement ORE DICT
	 * @param slot id of the slot
	 * @param stack item you wish to check
	 * @return if it valid or not
	 */
	public boolean isItemValid(int slot, ItemStack stack);
}
