package sonar.calculator.mod.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class RecipeData extends WorldSavedData {
	public static final String recipe =  "Recipe-Data";	
	
	public int hi;
	
	public RecipeData(int hi){
		super(recipe);
	}
	
	public RecipeData() {
		super(recipe);
	}
	@Override
	public void readFromNBT(NBTTagCompound compound) {		
		hi = compound.getInteger("hi");
	}
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		compound.setInteger("hi", hi);
	}

}
