package sonar.calculator.mod.network;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.client.gui.misc.GuiModuleSelector;
import sonar.calculator.mod.common.containers.ContainerModuleSelector;
import sonar.calculator.mod.network.packets.PacketCalculatorScreen;
import sonar.calculator.mod.network.packets.PacketJumpModule;
import sonar.calculator.mod.network.packets.PacketModuleSelection;
import sonar.calculator.mod.network.packets.PacketPlayerResearch;
import sonar.calculator.mod.network.packets.PacketTeleportLinks;
import sonar.core.common.tileentity.TileEntitySonar;
import sonar.core.utils.IGuiItem;
import sonar.core.utils.IGuiTile;

public class CalculatorCommon implements IGuiHandler {

    private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<>();

	public static void registerPackets() {
		Calculator.network.registerMessage(PacketCalculatorScreen.Handler.class, PacketCalculatorScreen.class, 0, Side.CLIENT);
		Calculator.network.registerMessage(PacketTeleportLinks.Handler.class, PacketTeleportLinks.class, 1, Side.CLIENT);
		Calculator.network.registerMessage(PacketJumpModule.Handler.class, PacketJumpModule.class, 2, Side.SERVER);
		Calculator.network.registerMessage(PacketJumpModule.Handler.class, PacketJumpModule.class, 3, Side.CLIENT);
		Calculator.network.registerMessage(PacketModuleSelection.Handler.class, PacketModuleSelection.class, 4, Side.SERVER);
		Calculator.network.registerMessage(PacketPlayerResearch.Handler.class, PacketPlayerResearch.class, 5, Side.CLIENT);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
		if (entity != null) {
			if(entity instanceof TileEntitySonar){
				((TileEntitySonar) entity).forceNextSync();
			}
			switch (ID) {
			case IGuiTile.ID:
				return ((IGuiTile) entity).getGuiContainer(player);
			}
		} else {
			ItemStack equipped = player.getHeldItemMainhand();
			if (!equipped.isEmpty()) {
				switch (ID) {
				case IGuiItem.ID:
					return ((IGuiItem) equipped.getItem()).getGuiContainer(player, equipped);
					// case CalculatorGui.SmeltingModule:
					// return new ContainerSmeltingModule(player, player.inventory, new WIPSmeltingModule.SmeltingInventory(equipped), equipped);
				case CalculatorGui.RecipeInfo:
					break;
				case CalculatorGui.ModuleSelect:
					return new ContainerModuleSelector(player, equipped);
				}
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
		if (entity != null) {
			switch (ID) {
			case IGuiTile.ID:
				return ((IGuiTile) entity).getGuiScreen(player);
			}
		} else {
			ItemStack equipped = player.getHeldItemMainhand();
			if (equipped != null) {
				switch (ID) {
				case IGuiItem.ID:
					return ((IGuiItem) equipped.getItem()).getGuiScreen(player, equipped);
					// case CalculatorGui.SmeltingModule:
					// return new GuiSmeltingModule(player, player.inventory, new WIPSmeltingModule.SmeltingInventory(equipped), equipped);
				case CalculatorGui.ModuleSelect:
					return new GuiModuleSelector(player, equipped);
				}
			}
		}

		return null;
	}

	public void registerRenderThings() {
	}

	public static void storeEntityData(String name, NBTTagCompound compound) {
		extendedEntityData.put(name, compound);
	}

	public static NBTTagCompound getEntityData(String name) {
		return extendedEntityData.remove(name);
	}
}
