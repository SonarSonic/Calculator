package sonar.calculator.mod.research;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sonar.core.api.IRegistryObject;
import sonar.core.api.nbt.INBTSyncable;

public interface IResearch extends INBTSyncable, IRegistryObject {

	/** the registered research name */
	public String getName();

	/** the research name as it appears */
	public String getClientName();

	/** the research hint */
	public String getHint();

	/** the item to use as the logo for the research */
	public Item getItemLogo();

	/** gets a list of item rewards when this research is completed */
	public ArrayList<ItemStack> getItemRewards();

	/** gets a list of item rewards when this research is completed */
	public ArrayList<RecipeReward> getUnlockedRecipes();

	/** only relevant to items, to stop them being awarded twice, Recipes awards are always available */
	public boolean wasAwarded();

	/** sets if the items have been awarded or not */
	public void setAwarded(boolean bool);

	/** the players progress with this research is from 0 to 100 */
	public byte getProgress();

	/** the research type */
	public ResearchCategory getResearchType();

	/** gets a new instance of this research type */
	public IResearch getInstance();
}
