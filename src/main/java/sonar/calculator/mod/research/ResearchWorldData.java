package sonar.calculator.mod.research;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;
import sonar.core.helpers.NBTHelper.SyncType;

import javax.annotation.Nonnull;

public class ResearchWorldData extends WorldSavedData {

	public static final String tag = "sonar.calculator.research";

	public ResearchWorldData(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(@Nonnull NBTTagCompound nbt) {
		PlayerResearchRegistry.readData(nbt, SyncType.SAVE);
	}

	@Nonnull
    @Override
	public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound nbt) {
		PlayerResearchRegistry.writeData(nbt, SyncType.SAVE);
		return nbt;
	}

    @Override
	public boolean isDirty() {
		return true;
	}
}
