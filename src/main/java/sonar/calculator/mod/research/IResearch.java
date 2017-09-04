package sonar.calculator.mod.research;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sonar.core.api.IRegistryObject;
import sonar.core.api.nbt.INBTSyncable;

import java.util.ArrayList;

public interface IResearch extends INBTSyncable, IRegistryObject {

    /**
     * the registered research name
     */
    @Override
    String getName();

    /**
     * the research name as it appears
     */
    String getClientName();

    /**
     * the research hint
     */
    String getHint();

    /**
     * the item to use as the logo for the research
     */
    Item getItemLogo();

    /**
     * gets a list of item rewards when this research is completed
     */
    ArrayList<ItemStack> getItemRewards();

    /**
     * gets a list of item rewards when this research is completed
     */
    ArrayList<RecipeReward> getUnlockedRecipes();

    /**
     * only relevant to items, to stop them being awarded twice, Recipes awards are always available
     */
    boolean wasAwarded();

    /**
     * sets if the items have been awarded or not
     */
    void setAwarded(boolean bool);

    /**
     * the players progress with this research is from 0 to 100
     */
    byte getProgress();

    /**
     * the research type
     */
    ResearchCategory getResearchType();

    /**
     * gets a new instance of this research type
     */
    IResearch getInstance();
}
