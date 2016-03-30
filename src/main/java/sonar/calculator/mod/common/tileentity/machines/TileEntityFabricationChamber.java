package sonar.calculator.mod.common.tileentity.machines;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import sonar.calculator.mod.client.gui.misc.GuiFabricationChamber;
import sonar.calculator.mod.common.containers.ContainerFabricationChamber;
import sonar.calculator.mod.common.recipes.machines.FabricationChamberRecipes;
import sonar.calculator.mod.common.recipes.machines.FabricationChamberRecipes.CircuitStack;
import sonar.calculator.mod.common.tileentity.TileEntityProcess;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber.CircuitType;
import sonar.core.api.SonarAPI;
import sonar.core.common.tileentity.TileEntityEnergySidedInventory.EnergyMode;
import sonar.core.helpers.SonarHelper;
import sonar.core.inventory.SonarTileInventory;
import sonar.core.utils.IGuiTile;
import sonar.core.utils.MachineSideConfig;

public class TileEntityFabricationChamber extends TileEntityProcess implements IGuiTile {

	public ArrayList<TileEntityStorageChamber> chambers = new ArrayList();
	public ArrayList<CircuitStack> stored = new ArrayList();
	public ItemStack selected = null;

	public TileEntityFabricationChamber() {
		super.input = new int[] { 0 };
		super.output = new int[] {};
		super.inv = new SonarTileInventory(this, 1);
		setEnergyMode(EnergyMode.RECIEVE);
	}

	public void update() {
		super.update();
		chambers = getChambers();
		stored = getAvailableCircuits();
		markDirty();
	}

	public ArrayList<TileEntityStorageChamber> getChambers() {
		ArrayList<TileEntityStorageChamber> chambers = new ArrayList<TileEntityStorageChamber>();
		ArrayList<EnumFacing> outputs = sides.getSidesWithConfig(MachineSideConfig.INPUT);
		for (EnumFacing side : outputs) {
			TileEntity tile = SonarHelper.getAdjacentTileEntity(this, side);
			if (tile != null && tile instanceof TileEntityStorageChamber) {
				chambers.add((TileEntityStorageChamber) tile);
			}
		}

		return chambers;

	}

	public ArrayList<CircuitStack> getAvailableCircuits() {
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

	@Override
	public boolean canProcess() {
		if (selected != null) {
			CircuitStack[] stack = FabricationChamberRecipes.instance.getRequirements(selected);
			if (stack != null) {
				return FabricationChamberRecipes.canPerformRecipe(stack, stored);
			}
		}
		return false;
	}

	@Override
	public void finishProcess() {
		
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
