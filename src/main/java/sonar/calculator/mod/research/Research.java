package sonar.calculator.mod.research;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.research.types.ResearchTypes;
import sonar.core.helpers.NBTHelper.SyncType;

public abstract class Research implements IResearch {

	public boolean wasAwarded = false;
	public final ResearchTypes type;
	public final String clientName;
	public final Item logo;

	public Research(ResearchTypes type, String clientName, Item logo) {
		this.type = type;
		this.clientName = clientName;
		this.logo = logo;
	}

	public String getName() {
		return type.name();
	}

	public String getClientName() {
		return clientName;
	}

	public Item getItemLogo() {
		return logo;
	}

	@Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		wasAwarded = nbt.getBoolean("wasAwarded");
	}

	@Override
	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		nbt.setBoolean("wasAwarded", wasAwarded);
		return nbt;
	}

	public ArrayList<ItemStack> getItemRewards() {
		return new ArrayList();
	}

	public ArrayList<RecipeReward> getUnlockedRecipes() {
		return new ArrayList();
	}

	@Override
	public boolean wasAwarded() {
		return wasAwarded;
	}

	@Override
	public void setAwarded(boolean bool) {
		wasAwarded = bool;
	}

	@Override
	public boolean isLoadable() {
		return true;
	}
}
