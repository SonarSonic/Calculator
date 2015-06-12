package sonar.calculator.mod.common.tileentity.misc;

import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.core.utils.helpers.SonarHelper;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityStablePort extends TileEntity implements IEnergyConnection {

	public int x, y, z;

	public void updateEntity() {
		TileEntityCalculatorLocator locator = getLocator();
		if (locator != null && locator.storage.getEnergyStored() != 0) {
			addEnergy(locator);
		}
	}

	public void addEnergy(TileEntityCalculatorLocator locator) {
		for (int i = 0; i < 6; i++) {
			if (SonarHelper.isAdjacentEnergyHandlerFromSide(this, i)) {
				TileEntity entity = SonarHelper.getAdjacentTileEntity(this, ForgeDirection.getOrientation(i));
				
				if (SonarHelper.isEnergyHandlerFromSide(entity, ForgeDirection.getOrientation(i))) {
					locator.storage.modifyEnergyStored(-SonarHelper.pushEnergy(entity, ForgeDirection.getOrientation(i).getOpposite(), locator.storage.extractEnergy(64000, true), false));

				}
				return;
			}
		}
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("Locator_X", x);
		tag.setInteger("Locator_Y", y);
		tag.setInteger("Locator_Z", z);
	}

	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.x = tag.getInteger("Locator_X");
		this.y = tag.getInteger("Locator_Y");
		this.z = tag.getInteger("Locator_Z");
	}

	public void findLocator() {
		double distance = 100;
		boolean found = false;
		for (int X = -11; X <= 11; X++) {
			for (int Z = -11; Z <= 11; Z++) {
				for (int Y = -1; Y <= 1; Y++) {
					if (this.worldObj.getTileEntity(xCoord + X, yCoord + Y, zCoord + Z) != null && this.worldObj.getTileEntity(xCoord + X, yCoord + Y, zCoord + Z) instanceof TileEntityCalculatorLocator) {
						double mastDist = this.worldObj.getTileEntity(xCoord + X, yCoord + Y, zCoord + Z).getDistanceFrom(xCoord, yCoord, zCoord);
						if (distance > mastDist) {
							distance = mastDist;
							this.x = xCoord + X;
							this.y = yCoord + Y;
							this.z = zCoord + Z;
							found = true;
						}
					}
				}
			}
		}
		if (!found) {
			this.x = -1000;
			this.y = -1000;
			this.z = -1000;
		}

	}

	public boolean hasLocator() {
		if (x == -1000 && y == -1000 && z == -1000) {
			return false;
		}
		TileEntity target = this.worldObj.getTileEntity(x, y, z);
		if (target != null && target instanceof TileEntityCalculatorLocator) {
			return true;
		}
		return false;
	}

	public TileEntityCalculatorLocator getLocator() {
		if (hasLocator()) {
			TileEntity target = this.worldObj.getTileEntity(x, y, z);
			if (target != null && target instanceof TileEntityCalculatorLocator) {
				return (TileEntityCalculatorLocator) target;
			}
		}

		return null;
	}
}
