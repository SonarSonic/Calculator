package sonar.calculator.mod.common.tileentity.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.machines.GuiResearchChamber;
import sonar.calculator.mod.common.containers.ContainerResearchChamber;
import sonar.calculator.mod.common.recipes.ResearchRecipeType;
import sonar.calculator.mod.network.packets.PacketPlayerResearch;
import sonar.calculator.mod.research.IResearch;
import sonar.calculator.mod.research.PlayerResearchRegistry;
import sonar.calculator.mod.research.types.RecipeResearch;
import sonar.calculator.mod.research.types.ResearchTypes;
import sonar.core.api.IFlexibleGui;
import sonar.core.common.tileentity.TileEntityInventory;
import sonar.core.network.sync.SyncTagType;
import sonar.core.network.sync.SyncUUID;

import java.util.ArrayList;

public class TileEntityResearchChamber extends TileEntityInventory implements IFlexibleGui {

	public static final int researchSpeed = 100;
	public SyncTagType.INT ticks = new SyncTagType.INT(0);
	public SyncUUID playerUUID = new SyncUUID(1);

	public TileEntityResearchChamber() {
		super.inv.setSize(1);
		syncList.addParts(ticks, playerUUID);
	}

    @Override
	public void update() {
		super.update();
		if (slots().get(0) == null) {
			ticks.setObject(0);
            //return;
		} else {
			if (ticks.getObject() == 0)
				ticks.setObject(1);
			if (ticks.getObject() > 0) {
				if (ticks.getObject() != researchSpeed)
					ticks.increaseBy(1);
				else {
					addRecipes();
					ticks.setObject(-1);
				}
			}
		}
	}

	public void addRecipes() {
		if (isServer()) {
			ArrayList<ResearchRecipeType> types = ResearchRecipeType.getUnlocked(slots().get(0));
			if (!types.isEmpty()) {
				IResearch research = PlayerResearchRegistry.getSpecificResearch(playerUUID.getUUID(), ResearchTypes.RECIPES);
				if (research instanceof RecipeResearch) {
					RecipeResearch recipes = (RecipeResearch) research;
					recipes.addRecipes(types);
				}
			}
		}
	}

    @Override
	public boolean receiveClientEvent(int action, int param) {
		if (action == 1)
			markBlockForUpdate();
		return true;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		super.setInventorySlotContents(i, itemstack);
		markBlockForUpdate();
		this.world.addBlockEvent(pos, blockType, 1, 0);
	}

    @Override
	public void openInventory(EntityPlayer player) {
		super.openInventory(player);
		if (isServer())
			Calculator.network.sendTo(new PacketPlayerResearch(player), (EntityPlayerMP) player);
	}

	@Override
	public Object getServerElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new ContainerResearchChamber(player, this);
	}

	@Override
	public Object getClientElement(Object obj, int id, World world, EntityPlayer player, NBTTagCompound tag) {
		return new GuiResearchChamber(player, this);
	}
}
