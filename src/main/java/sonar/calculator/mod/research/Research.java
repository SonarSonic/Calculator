package sonar.calculator.mod.research;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.research.types.ResearchTypes;
import sonar.core.helpers.NBTHelper.SyncType;

import java.util.ArrayList;

public abstract class Research implements IResearch {

    public boolean wasAwarded;
	public final ResearchTypes type;
	public final String clientName;
	public final Item logo;

	public Research(ResearchTypes type, String clientName, Item logo) {
		this.type = type;
		this.clientName = clientName;
		this.logo = logo;
	}

    @Override
	public String getName() {
		return type.name();
	}

    @Override
	public String getClientName() {
		return clientName;
	}

    @Override
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

    @Override
	public ArrayList<ItemStack> getItemRewards() {
        return new ArrayList<>();
	}

    @Override
	public ArrayList<RecipeReward> getUnlockedRecipes() {
        return new ArrayList<>();
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
