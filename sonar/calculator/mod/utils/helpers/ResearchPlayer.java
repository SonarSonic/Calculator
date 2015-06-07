package sonar.calculator.mod.utils.helpers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import java.util.Map;

import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipe;
import sonar.calculator.mod.common.recipes.crafting.CalculatorRecipes;
import sonar.calculator.mod.integration.nei.handlers.CalculatorRecipeHandler.SmeltingPair;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ResearchPlayer implements IExtendedEntityProperties {

	private final EntityPlayer player;

	public final static String tag = "CalculatorResearchPlayer";

	public final static int unlockedString = CalculatorConfig.unlockedRecipes;
	public final static int newString = CalculatorConfig.newRecipes;

	public ResearchPlayer(EntityPlayer player) {
		this.player = player;
		this.player.getDataWatcher().addObject(unlockedString, "");
		this.player.getDataWatcher().addObject(newString, "");
	}

	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(ResearchPlayer.tag, new ResearchPlayer(player));
	}

	public static final ResearchPlayer get(EntityPlayer player) {
		return (ResearchPlayer) player.getExtendedProperties(tag);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();

		properties.setString("unblocked", this.player.getDataWatcher().getWatchableObjectString(this.unlockedString));

		properties.setString("new", this.player.getDataWatcher().getWatchableObjectString(this.newString));

		compound.setTag(tag, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(tag);
		this.player.getDataWatcher().updateObject(unlockedString, properties.getString("unblocked"));
		this.player.getDataWatcher().updateObject(newString, properties.getString("new"));

	}

	@Override
	public void init(Entity entity, World world) {
	}

	private static String getSaveKey(EntityPlayer player) {
		return player.getGameProfile().getName() + ":" + tag;
	}

	public static void saveProxyData(EntityPlayer player) {
		ResearchPlayer playerData = ResearchPlayer.get(player);
		NBTTagCompound savedData = new NBTTagCompound();

		playerData.saveNBTData(savedData);
		Calculator.calculatorProxy.storeEntityData(getSaveKey(player), savedData);
	}

	public static void loadProxyData(EntityPlayer player) {
		ResearchPlayer playerData = ResearchPlayer.get(player);
		NBTTagCompound savedData = Calculator.calculatorProxy.getEntityData(getSaveKey(player));

		if (savedData != null) {
			playerData.loadNBTData(savedData);
		}

	}

	public void sync(EntityPlayer player, int item, int binary) {
		int[] unblocked = unblocked(player);
		int[] newRecipes = newRecipes(player);
		String blockString = "";
		String newlist = "";
		for (int i = 0; i < unblocked.length; i++) {
			if (i != item) {
				blockString = blockString + unblocked[i];
				newlist = newlist + newRecipes[i];
			} else {
				blockString = blockString + binary;
				newlist = newlist + binary;
			}
		}
		this.player.getDataWatcher().updateObject(unlockedString, blockString);
		this.player.getDataWatcher().updateObject(newString, newlist);

	}

	public void sync(EntityPlayer player) {
		int[] unblocked = unblocked(player);
		int[] newRecipes = newRecipes(player);
		String blockString = "";
		String newlist = "";
		for (int i = 0; i < unblocked.length; i++) {
			blockString = blockString + unblocked[i];
			newlist = newlist + newRecipes[i];
		}
		this.player.getDataWatcher().updateObject(unlockedString, blockString);
		this.player.getDataWatcher().updateObject(newString, newlist);

	}

	public void block(EntityPlayer player, ItemStack stack) {
		if (CalculatorRecipes.recipes().getID(stack) != 0)
			sync(player, CalculatorRecipes.recipes().getID(stack), 0);

	}

	private void block(EntityPlayer player, int stack) {
		if (stack != 0)
			sync(player, stack, 0);
	}

	public void unblock(EntityPlayer player, ItemStack stack) {
		this.player.getDataWatcher().updateObject(newString, "");
		if (stack.getItem() == Item.getItemFromBlock(Blocks.log) || stack.getItem() == Item.getItemFromBlock(Blocks.log2) || stack.getItem() == Item.getItemFromBlock(Blocks.carpet) || stack.getItem() == Item.getItemFromBlock(Blocks.wool)
				|| stack.getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay) || stack.getItem() == Item.getItemFromBlock(Blocks.sapling) || stack.getItem() == Item.getItemFromBlock(Blocks.planks) || stack.getItem() == Item.getItemFromBlock(Blocks.leaves)
				|| stack.getItem() == Item.getItemFromBlock(Blocks.leaves2)) {
			unlock(player, stack);
		} else if (CalculatorRecipes.recipes().getID(stack) != 0)
			sync(player, CalculatorRecipes.recipes().getID(stack), 1);

	}

	private void unblock(EntityPlayer player, int stack) {
		if (stack != 0)
			sync(player, stack, 1);

	}

	private void unlock(EntityPlayer player, ItemStack stack) {
		for (int i = 0; i < 16; i++) {
			ItemStack unlock = new ItemStack(stack.getItem(), 1, i);
			if (unlock != null) {
				unblock(player, CalculatorRecipes.recipes().getID(unlock));
			}
		}
	}

	public boolean isBlocked(EntityPlayer player, ItemStack stack) {
		if (stack == null) {
			return false;
		}
		if (CalculatorRecipes.recipes().getID(stack) == 0) {
			return true;
		}
		return unblocked(player)[CalculatorRecipes.recipes().getID(stack)] == 0;

	}

	public boolean isBlocked(EntityPlayer player, int stack) {
		CalculatorRecipes.recipes().getStack(stack);
		if (stack == 0) {
			return true;
		}
		return unblocked(player)[stack] == 0;

	}

	public static int[] unblocked(EntityPlayer player) {
		String list = player.getDataWatcher().getWatchableObjectString(unlockedString);
		int[] unblocked = new int[CalculatorRecipes.recipes().getIDList().size() + 1];

		for (int i = 0; i < list.length(); i++) {
			unblocked[i] = Integer.parseInt(String.valueOf(list.charAt(i)));
		}
		return unblocked;

	}

	public static int[] newRecipes(EntityPlayer player) {
		String list = player.getDataWatcher().getWatchableObjectString(newString);
		int[] unblocked = new int[CalculatorRecipes.recipes().getIDList().size() + 1];

		for (int i = 0; i < list.length(); i++) {
			unblocked[i] = Integer.parseInt(String.valueOf(list.charAt(i)));
		}
		return unblocked;

	}
}