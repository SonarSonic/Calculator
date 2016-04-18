package sonar.calculator.mod.common.tileentity.machines;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import sonar.calculator.mod.client.gui.misc.GuiFabricationChamber;
import sonar.calculator.mod.common.containers.ContainerFabricationChamber;
import sonar.calculator.mod.common.recipes.machines.FabricationChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.FabricationChamberRecipes.CircuitStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber.CircuitType;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.helpers.ItemStackHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.inventory.SonarInventory;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.utils.IGuiTile;

public class TileEntityFabricationChamber extends TileEntityInventory implements IGuiTile, IByteBufTile {

	public ItemStack selected = null;
	public int fabricateTime = 200;
	public int moveTime = 100;
	public boolean moved;
	public boolean canMove;

	public int currentFabricateTime = 0;
	public int currentMoveTime = 0;

	public TileEntityFabricationChamber() {
		super.inv = new SonarInventory(this, 1);
	}

	public void update() {
		super.update();
		if (canMove) {
			if (currentMoveTime != 50 && currentMoveTime != 0) {
				if (!moved)
					currentMoveTime++;
				else
					currentMoveTime--;
				if (currentMoveTime == 0) {
					canMove = false;
				}
			} else if (currentMoveTime == 50) {
				// so now it's in position
				if (currentFabricateTime != fabricateTime) {
					currentFabricateTime++;
					if (this.isClient()) {
						if ((currentFabricateTime & 1) == 0 && ((currentFabricateTime / 2) & 1) == 0) {
							worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.38, pos.getY() + 0.6F, pos.getZ() + 0.38, 0.0D, 0.0D, 0.0D);
							worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.38, pos.getY() + 0.6F, pos.getZ() + 0.38 + 0.25, 0.0D, 0.0D, 0.0D);
							worldObj.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.38, pos.getY() + 0.6F, pos.getZ() + 0.38, 0.0D, 0.0D, 0.0D);
							worldObj.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.38, pos.getY() + 0.6F, pos.getZ() + 0.38 + 0.25, 0.0D, 0.0D, 0.0D);
						}
					}
				} else {
					currentFabricateTime = 0;
					currentMoveTime--;
					moved = true;
					fabricate();
				}
			} else if (currentMoveTime == 0) {
				moved = false;
				currentMoveTime++;
			}
		}
	}

	public ArrayList<TileEntityStorageChamber> getChambers() {
		ArrayList<TileEntityStorageChamber> chambers = new ArrayList<TileEntityStorageChamber>();
		for (EnumFacing side : EnumFacing.VALUES) {
			int offset = 1;
			while (offset != 64) {
				TileEntity tile = worldObj.getTileEntity(getPos().offset(side, offset));
				if (tile != null && tile instanceof TileEntityStorageChamber) {
					chambers.add((TileEntityStorageChamber) tile);
				} else {
					break;
				}
				offset++;
			}
		}
		return chambers;

	}

	public ArrayList<CircuitStack> getAvailableCircuits(ArrayList<TileEntityStorageChamber> chambers) {
		ArrayList<CircuitStack> circuits = new ArrayList<CircuitStack>();
		for (TileEntityStorageChamber chamber : chambers) {
			int pos = 0;
			CircuitType type = TileEntityStorageChamber.getCircuitType(chamber.getStorage().getSavedStack());
			if (type != null && type.isProcessed()) {
				for (Integer stored : chamber.getStorage().stored) {
					if (stored != 0) {
						CircuitStack storedStack = new CircuitStack(pos, stored, type.isStable());
						addCircuitToStack(circuits, storedStack);
					}
					pos++;
				}
			}
		}
		return circuits;
	}

	public void addCircuitToStack(ArrayList<CircuitStack> circuits, CircuitStack stack) {
		for (CircuitStack stored : circuits) {
			if (stored.stable == stack.stable && stored.meta == stack.meta) {
				stored.required += stack.required;
				return;
			}
		}
		circuits.add(stack);
	}

	public void fabricate() {
		if (selected == null || this.isClient()) {
			return;
		}
		ItemStack selected = this.selected.copy();
		if (selected.stackSize == 0)
			selected.stackSize++;
		ArrayList<TileEntityStorageChamber> chambers = getChambers();
		ArrayList<CircuitStack> available = getAvailableCircuits(chambers);
		CircuitStack[] requirements = FabricationChamberRecipes.getInstance().getRequirements(selected).clone();
		if (requirements != null && FabricationChamberRecipes.canPerformRecipe(requirements, available)) {
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
				final List<CircuitStack> used = (List<CircuitStack>) Arrays.asList(requirements.clone());
				for (TileEntityStorageChamber chamber : chambers) {
					CircuitType type = TileEntityStorageChamber.getCircuitType(chamber.getStorage().getSavedStack());
					if (type != null && type.isProcessed()) {
						int pos = 0;
						for (CircuitStack circuit : used) {
							if (circuit.required > 0 && (circuit.stable && type.isStable()) || (!circuit.stable && !type.isStable())) {
								int stored = chamber.getStorage().stored[circuit.meta];
								if (stored > 0) {
									int remove = (int) Math.min(stored, circuit.required);
									chamber.getStorage().decreaseStored(circuit.meta, remove);
									// used.get(pos).required -= remove;
								}
							}
							pos++;
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

	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		if (type.isType(SyncType.DEFAULT_SYNC, SyncType.SAVE)) {
			if (selected != null) {
				NBTTagCompound tag = new NBTTagCompound();
				selected.writeToNBT(tag);
				nbt.setTag("selected", tag);
			}
		}
	}

	@Override
	public void writePacket(ByteBuf buf, int id) {
		switch (id) {
		case 0:
			ByteBufUtils.writeItemStack(buf, selected);
			break;
		case 1:
			if (!canMove)
				canMove = true;
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
			if (!canMove)
				canMove = true;
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
