package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import sonar.calculator.mod.common.tileentity.TileEntityFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPoint;
import sonar.core.inventory.ContainerEmpty;
import sonar.core.inventory.ContainerSync;

public class ContainerFlux extends ContainerSync {

	public int state;
	
	public ContainerFlux(InventoryPlayer player, TileEntity entity, boolean network) {
		super(entity);
		addSlots(player,entity);
	}

	public void addSlots(InventoryPlayer player, TileEntity entity){
		
		if (entity instanceof TileEntityFluxPoint) {
			for (int i = 0; i < 3; i++) {
				for (int k = 0; k < 9; k++) {
					addSlotToContainer(new Slot(player, k + i * 9 + 9, 8 + k * 18, (state==1 ? 84 : 60) + i * 18));
				}
			}
			for (int i = 0; i < 9; i++) {
				addSlotToContainer(new Slot(player, i, 8 + i * 18,(state==1 ? 142 : 118) ));
			}
		} else {
			for (int i = 0; i < 3; i++) {
				for (int k = 0; k < 9; k++) {
					addSlotToContainer(new Slot(player, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
				}
			}
			for (int i = 0; i < 9; i++) {
				addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
			}
		}
	}
	
	public void switchState(InventoryPlayer player, TileEntity entity){
		if(state==0){
			state=1;
		}else{
			state=0;
		}
		this.inventorySlots.clear();
		this.inventoryItemStacks.clear();
		this.addSlots(player, entity);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}