package sonar.calculator.mod.utils;

import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotBanned extends Slot {

	public SlotBanned(IInventory inv,int slot, int x,int y) {
		super(inv, slot, x, y);
	}
	  public boolean isItemValid(ItemStack stack)
	    {
		  if(!TileEntityAtomicMultiplier.isAllowed(stack)){
			  return false;
		  }
		  else if(stack.getMaxStackSize()<4){
			  return false;
		  }
	        return true;
	    }
}
