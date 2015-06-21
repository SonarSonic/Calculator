package sonar.calculator.mod.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;

public class FluxControllerData extends WorldSavedData {
	
	TileEntityFluxController controller;
	public boolean removed;
	
	
	public FluxControllerData(TileEntityFluxController controller, int freq) {
		super(TileEntityFluxController.chunkData+freq);
		this.controller=controller;
		this.removed=true;
	}
	public FluxControllerData(TileEntityFluxController controller) {
		super(TileEntityFluxController.chunkData+controller.freq);
		this.controller=controller;
		this.removed=false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		controller.recieveMode = tag.getInteger("recieveMode");
		controller.sendMode = tag.getInteger("sendMode");
		controller.freq = tag.getInteger("freq");
		controller.x = tag.getInteger("XX");
		controller.y = tag.getInteger("YY");
		controller.z = tag.getInteger("ZZ");
		controller.dimension = tag.getInteger("DIMENSION");
		controller.playerName = tag.getString("playerName");
		controller.allowDimensions = tag.getInteger("allowDimensions");
		controller.playerProtect = tag.getInteger("playerProtect");
		removed = tag.getBoolean("removed");
		
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setInteger("recieveMode", controller.recieveMode);
		tag.setInteger("sendMode", controller.sendMode);
		tag.setInteger("freq", controller.freq);
		tag.setInteger("XX", controller.x);
		tag.setInteger("YY", controller.y);
		tag.setInteger("ZZ", controller.z);
		tag.setInteger("DIMENSION", controller.dimension);
		tag.setString("playerName", controller.playerName);
		tag.setInteger("allowDimensions", controller.allowDimensions);
		tag.setInteger("playerProtect", controller.playerProtect);
		tag.setBoolean("removed", removed);
	}
	public TileEntityFluxController getController(){
		return controller;
		
	}

}
