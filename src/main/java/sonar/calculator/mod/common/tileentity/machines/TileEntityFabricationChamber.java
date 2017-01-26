package sonar.calculator.mod.common.tileentity.machines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.misc.GuiFabricationChamber;
import sonar.calculator.mod.common.containers.ContainerFabricationChamber;
import sonar.calculator.mod.common.recipes.FabricationChamberRecipes;
import sonar.core.SonarCore;
import sonar.core.api.SonarAPI;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.api.utils.ActionType;
import sonar.core.api.utils.BlockCoords;
import sonar.core.common.block.SonarBlock;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.helpers.ItemStackHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.helpers.SonarHelper;
import sonar.core.inventory.SonarInventory;
import sonar.core.network.sync.SyncTagType;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.recipes.ISonarRecipe;
import sonar.core.recipes.ISonarRecipeObject;
import sonar.core.utils.IGuiTile;

public class TileEntityFabricationChamber extends TileEntityInventory implements IGuiTile, IByteBufTile {

	public ItemStack selected = null;
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
		super.inv = new SonarInventory(this, 1);
		syncList.addPart(inv);
	}

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
						if ((currentFabricateTime.getObject() & 1) == 0 && ((currentFabricateTime.getObject() / 2) & 1) == 0) {
							EnumFacing face = worldObj.getBlockState(getPos()).getValue(SonarBlock.FACING);
							int fX = face.getFrontOffsetX();
							int fZ = face.getFrontOffsetZ();
							// TODO
							// worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + fX == -1 ? 0.62 : 0.38, pos.getY() + 0.6F, pos.getZ() + (fZ == 0 ? 0.38 : 0.62), 0.0D, 0.0D, 0.0D);
							// worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.38 + (0.25 *face.getFrontOffsetZ()), pos.getY() + 0.6F, pos.getZ() + 0.38 + (0.25 *face.getFrontOffsetZ()), 0.0D, 0.0D, 0.0D);
							// worldObj.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + (0.38 * fX), pos.getY() + 0.6F, pos.getZ() + 0.38 * fZ, 0.0D, 0.0D, 0.0D);
							// worldObj.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + (0.38 * fX), pos.getY() + 0.6F, pos.getZ() + 0.38 + 0.25 * fZ, 0.0D, 0.0D, 0.0D);
							// worldObj.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + (0.38 * fX), pos.getY() + 0.6F, pos.getZ() + 0.38 * fZ, 0.0D, 0.0D, 0.0D);

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
		ArrayList<TileEntityStorageChamber> chambers = new ArrayList<TileEntityStorageChamber>();

		ArrayList<BlockCoords> connected = SonarHelper.getConnectedBlocks(Calculator.storageChamber, Arrays.asList(EnumFacing.VALUES), worldObj, pos, 256);
		for (BlockCoords chamber : connected) {
			TileEntity tile = chamber.getTileEntity(worldObj);
			if (tile != null && tile instanceof TileEntityStorageChamber) {
				chambers.add((TileEntityStorageChamber) tile);
			}
		}
		return chambers;

	}

	public ArrayList<StoredItemStack> getAvailableCircuits(ArrayList<TileEntityStorageChamber> chambers) {
		ArrayList<StoredItemStack> circuits = new ArrayList<StoredItemStack>();
		for (TileEntityStorageChamber chamber : chambers) {
			for (StoredItemStack storedstack : chamber.getTileInv().slots) {
				if (storedstack != null && storedstack.getItemStack().getItem() == Calculator.circuitBoard) {
					SonarAPI.getItemHelper().addStackToList(circuits, storedstack);
				}
			}
		}
		return circuits;
	}

	public void fabricate() {
		if (selected == null || this.isClient()) {
			return;
		}
		ItemStack selected = this.selected.copy();
		if (selected.stackSize == 0)
			selected.stackSize++;
		ArrayList<TileEntityStorageChamber> chambers = getChambers();
		ArrayList<StoredItemStack> available = getAvailableCircuits(chambers);
		ISonarRecipe recipe = FabricationChamberRecipes.instance().getRecipeFromOutputs(null, new Object[] { selected });
		if (recipe != null && recipe.matchingInputs(new Object[] { available })) {
			ItemStack current = slots()[0];
			boolean fabricated = false;
			if (current == null) {
				slots()[0] = selected.copy();
				fabricated = true;
			} else if (ItemStackHelper.equalStacksRegular(current, selected) && current.stackSize + selected.stackSize <= getInventoryStackLimit() && current.stackSize + selected.stackSize <= selected.getMaxStackSize()) {
				slots()[0].stackSize += selected.copy().stackSize;
				fabricated = true;
			}
			if (fabricated) {
				final List<ISonarRecipeObject> inputs = recipe.inputs();
				List<StoredItemStack> stacks = new ArrayList();
				inputs.forEach(input -> stacks.add(new StoredItemStack((ItemStack) input.getValue())));
				for (TileEntityStorageChamber chamber : chambers) {
					if (stacks.isEmpty()) {
						break;
					}
					for (StoredItemStack stack : stacks) {
						if (stack.stored != 0) {
							StoredItemStack remove = SonarAPI.getItemHelper().removeItems(chamber, stack.copy(), EnumFacing.DOWN, ActionType.PERFORM, null);
							stack.stored -= remove.stored;
						}
					}
				}
				markDirty();
			}
		}
	}

	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			if (nbt.hasKey("selected")) {
				selected = ItemStack.loadItemStackFromNBT((NBTTagCompound) nbt.getTag("selected"));
			} else {
				selected = null;
			}
		}
	}

	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			if (selected != null) {
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
				if (recipe != null && recipe.matchingInputs(new Object[] { available })) {
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
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerFabricationChamber(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiFabricationChamber(player.inventory, this);
	}

}
