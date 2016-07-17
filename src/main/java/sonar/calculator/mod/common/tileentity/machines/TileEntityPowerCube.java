package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.entity.player.EntityPlayer;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.client.gui.machines.GuiPowerCube;
import sonar.calculator.mod.common.containers.ContainerPowerCube;
import sonar.core.api.energy.EnergyMode;
import sonar.core.common.tileentity.TileEntityEnergyInventory;
import sonar.core.inventory.SonarInventory;
import sonar.core.utils.IGuiTile;

public class TileEntityPowerCube extends TileEntityEnergyInventory implements IGuiTile {

	public TileEntityPowerCube() {
		super.storage.setCapacity(CalculatorConfig.getInteger("Standard Machine")).setMaxTransfer(200);
		super.inv = new SonarInventory(this, 2);
		super.energyMode = EnergyMode.RECIEVE;
		super.maxTransfer = 4;
	}

	@Override
	public void update() {
		super.update();
		if (this.isClient()) {
			return;
		}
		charge(0);
		discharge(1);		
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new ContainerPowerCube(player.inventory, this);
	}

	@Override
	public Object getGuiScreen(EntityPlayer player) {
		return new GuiPowerCube(player.inventory, this);
	}

}
