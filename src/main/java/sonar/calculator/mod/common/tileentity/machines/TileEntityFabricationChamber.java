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
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.items.IStability;
import sonar.calculator.mod.client.gui.misc.GuiFabricationChamber;
import sonar.calculator.mod.common.containers.ContainerFabricationChamber;
import sonar.calculator.mod.common.item.misc.CircuitBoard;
import sonar.calculator.mod.common.recipes.machines.FabricationChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.FabricationChamberRecipes.CircuitStack;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber.CircuitType;
import sonar.core.api.SonarAPI;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.api.utils.ActionType;
import sonar.core.api.utils.BlockCoords;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.helpers.ItemStackHelper;
import sonar.core.helpers.SonarHelper;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.inventory.SonarInventory;
import sonar.core.network.utils.IByteBufTile;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.MachineSideConfig;

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

		ArrayList<BlockCoords> connected = SonarHelper.getConnectedBlocks(Calculator.storageChamber, Arrays.asList(EnumFacing.VALUES), worldObj, pos, 256);
		for (BlockCoords chamber : connected) {
			TileEntity tile = chamber.getTileEntity(worldObj);
			if (tile != null && tile instanceof TileEntityStorageChamber) {
				chambers.add((TileEntityStorageChamber) tile);
			}
		}
		return chambers;

	}

	public ArrayList<CircuitStack> getAvailableCircuits(ArrayList<TileEntityStorageChamber> chambers) {
		ArrayList<CircuitStack> circuits = new ArrayList<CircuitStack>();
		for (TileEntityStorageChamber chamber : chambers) {
			for (ArrayList<ItemStack> stack : chamber.stacks()) {
				StoredItemStack storedstack = chamber.getTileInv().buildItemStack(stack);
				if (storedstack != null && storedstack.getItemStack().getItem() == Calculator.circuitBoard) {
					CircuitStack storedStack = new CircuitStack(storedstack.getItemStack().getItemDamage(), storedstack.stored, ((IStability) (storedstack.getItemStack().getItem())).getStability(storedstack.getItemStack()));
					addCircuitToStack(circuits, storedStack);
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
				ArrayList<StoredItemStack> stacks = new ArrayList();
				for (CircuitStack stack : used) {
					ItemStack item = new ItemStack(Calculator.circuitBoard, 1, stack.meta);
					NBTTagCompound tag = new NBTTagCompound();
					if (((CircuitBoard) item.getItem()).getStability(item)) {
						tag.setInteger("Stable", 1);
					} else {
						tag.setInteger("Stable", 0);
					}
					tag.setBoolean("Analysed", true);
					item.setTagCompound(tag);
					SonarAPI.getItemHelper().addStackToList(stacks, new StoredItemStack(item).setStackSize(stack.required));
				}
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
