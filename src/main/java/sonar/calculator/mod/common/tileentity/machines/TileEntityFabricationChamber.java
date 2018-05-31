package sonar.calculator.mod.common.tileentity.machines;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.items.IItemHandler;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.misc.GuiFabricationChamber;
import sonar.calculator.mod.common.containers.ContainerFabricationChamber;
import sonar.calculator.mod.common.recipes.FabricationChamberRecipes;
import sonar.core.SonarCore;
import sonar.core.api.IFlexibleGui;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.api.utils.BlockCoords;
import sonar.core.common.block.SonarBlock;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.helpers.ItemStackHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.SonarHelper;
import sonar.core.inventory.SonarLargeInventory;
import sonar.core.inventory.handling.EnumFilterType;
import sonar.core.inventory.handling.ItemTransferHelper;
import sonar.core.inventory.handling.filters.IInsertFilter;
import sonar.core.network.sync.SyncTagType;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.recipes.ISonarRecipe;
import sonar.core.recipes.ISonarRecipeObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileEntityFabricationChamber extends TileEntityInventory implements IFlexibleGui, IByteBufTile {

	public ItemStack selected = ItemStack.EMPTY;
	public final int fabricateTime = 200;
	public final int moveTime = 100;
	public SyncTagType.BOOLEAN canMove = new SyncTagType.BOOLEAN(0);
	public SyncTagType.BOOLEAN moved = new SyncTagType.BOOLEAN(1);
	public SyncTagType.INT currentFabricateTime = new SyncTagType.INT(2);
	public SyncTagType.INT currentMoveTime = new SyncTagType.INT(3);

	{
		syncList.addParts(canMove, moved, currentFabricateTime, currentMoveTime);
	}

	public TileEntityFabricationChamber() {
		super.inv.setSize(1);
		super.inv.getInsertFilters().put(IInsertFilter.BLOCK_INSERT, EnumFilterType.EXTERNAL_INTERNAL);
	}

    @Override
	public void update() {
		super.update();
		if (canMove.getObject()) {
			if (currentMoveTime.getObject() != 50 && currentMoveTime.getObject() != 0) {
				if (!moved.getObject())
					currentMoveTime.increaseBy(1);
				else
					currentMoveTime.decreaseBy(1);
				if (currentMoveTime.getObject() == 0) {
					canMove.setObject(false);
				}
			} else if (currentMoveTime.getObject() == 50) {
				// so now it's in position
				if (currentFabricateTime.getObject() != fabricateTime) {
					currentFabricateTime.increaseBy(1);
					if (this.isClient()) {
                        if ((currentFabricateTime.getObject() & 1) == 0 && (currentFabricateTime.getObject() / 2 & 1) == 0) {
							EnumFacing face = world.getBlockState(getPos()).getValue(SonarBlock.FACING);
							int fX = face.getFrontOffsetX();
							int fZ = face.getFrontOffsetZ();
							// TODO
							// world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + fX == -1 ? 0.62 : 0.38, pos.getY() + 0.6F, pos.getZ() + (fZ == 0 ? 0.38 : 0.62), 0.0D, 0.0D, 0.0D);
							// world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.38 + (0.25 *face.getFrontOffsetZ()), pos.getY() + 0.6F, pos.getZ() + 0.38 + (0.25 *face.getFrontOffsetZ()), 0.0D, 0.0D, 0.0D);
							// world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + (0.38 * fX), pos.getY() + 0.6F, pos.getZ() + 0.38 * fZ, 0.0D, 0.0D, 0.0D);
							// world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + (0.38 * fX), pos.getY() + 0.6F, pos.getZ() + 0.38 + 0.25 * fZ, 0.0D, 0.0D, 0.0D);
							// world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + (0.38 * fX), pos.getY() + 0.6F, pos.getZ() + 0.38 * fZ, 0.0D, 0.0D, 0.0D);

						}
					}
				} else {
					currentFabricateTime.setObject(0);
					currentMoveTime.decreaseBy(1);
					moved.setObject(true);
					fabricate();
				}
			} else if (currentMoveTime.getObject() == 0) {
				moved.setObject(false);
				currentMoveTime.increaseBy(1);
			}
		}
	}

	public ArrayList<TileEntityStorageChamber> getChambers() {
        ArrayList<TileEntityStorageChamber> chambers = new ArrayList<>();

		ArrayList<BlockCoords> connected = SonarHelper.getConnectedBlocks(Calculator.storageChamber, Arrays.asList(EnumFacing.VALUES), world, pos, 256);
		for (BlockCoords chamber : connected) {
			TileEntity tile = chamber.getTileEntity(world);
			if (tile instanceof TileEntityStorageChamber) {
				chambers.add((TileEntityStorageChamber) tile);
			}
		}
		return chambers;
	}

	public ArrayList<StoredItemStack> getAvailableCircuits(ArrayList<TileEntityStorageChamber> chambers) {
        ArrayList<StoredItemStack> circuits = new ArrayList<>();
		for (TileEntityStorageChamber chamber : chambers) {
			for (SonarLargeInventory.InventoryLargeSlot slot : chamber.inv().slots) {
				StoredItemStack largeStack = slot.getLargeStack();
				if (largeStack.getItemStack().getItem() == Calculator.circuitBoard) {
					ItemTransferHelper.addStackToList(circuits, largeStack.copy());
				}
			}
		}
		return circuits;
	}

	public void fabricate() {
		if (selected.isEmpty() || this.isClient()) {
			return;
		}
		ItemStack selected = this.selected.copy();
		if (selected.getCount() == 0)
			selected.grow(1);
		ArrayList<TileEntityStorageChamber> chambers = getChambers();
		ArrayList<StoredItemStack> available = getAvailableCircuits(chambers);
		ISonarRecipe recipe = FabricationChamberRecipes.instance().getRecipeFromOutputs(null, new Object[] { selected });
		if (recipe != null && recipe.matchingInputs(available.toArray())) {
			ItemStack current = slots().get(0);
			boolean fabricated = false;
			if (current.isEmpty()) {
				slots().set(0, selected.copy());
				fabricated = true;
			} else if (ItemStackHelper.equalStacksRegular(current, selected) && current.getCount() + selected.getCount() <= getInventoryStackLimit() && current.getCount() + selected.getCount() <= selected.getMaxStackSize()) {
				current.grow(selected.getCount());
				fabricated = true;
			}
			if (fabricated) {
				final List<ISonarRecipeObject> inputs = recipe.inputs();
				INPUTS: for(ISonarRecipeObject input : inputs){
					ItemStack stack = ((ItemStack) input.getValue()).copy();
					for (TileEntityStorageChamber chamber : chambers) {
						IItemHandler handler = ItemTransferHelper.getItemHandler(chamber, EnumFacing.UP);
						if(!ItemTransferHelper.isInvalidItemHandler(handler)){
							for(int i = 0; i < handler.getSlots(); i++){
								ItemStack extract = handler.extractItem(i, stack.getCount(), true);
								if(!extract.isEmpty() && ItemStack.areItemStacksEqual(stack, extract)){
									stack = handler.extractItem(i, stack.getCount(), false);
									if(stack.isEmpty()){
										continue INPUTS;
									}
								}
							}
						}
					}
				}
				markDirty();
			}
		}
	}

    @Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			if (nbt.hasKey("selected")) {
				selected = new ItemStack((NBTTagCompound) nbt.getTag("selected"));
			} else {
				selected = ItemStack.EMPTY;
			}
		}
	}

    @Override
	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			if (!selected.isEmpty()) {
				NBTTagCompound tag = new NBTTagCompound();
				selected.writeToNBT(tag);
				nbt.setTag("selected", tag);
			}
		}
		return nbt;
	}

	@Override
	public void writePacket(ByteBuf buf, int id) {
		switch (id) {
		case 0:
			ByteBufUtils.writeItemStack(buf, selected);
			break;
		}
	}

	@Override
	public void readPacket(ByteBuf buf, int id) {
		switch (id) {
		case 0:
			selected = ByteBufUtils.readItemStack(buf);
			break;
		case 1:
			if (selected != null) {
				ArrayList<TileEntityStorageChamber> chambers = getChambers();
				ArrayList<StoredItemStack> available = getAvailableCircuits(chambers);
				ISonarRecipe recipe = FabricationChamberRecipes.instance().getRecipeFromOutputs(null, new Object[] { selected.copy() });
				if (recipe != null && recipe.matchingInputs(available.toArray())) {
					if (!canMove.getObject()) {
						canMove.setObject(true);
						SonarCore.sendFullSyncAround(this, 64);
						markDirty();
					}
				}
			}
			break;
		}
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerFabricationChamber(player.inventory, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiFabricationChamber(player.inventory, this);
	}
}
