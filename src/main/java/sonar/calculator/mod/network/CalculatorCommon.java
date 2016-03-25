package sonar.calculator.mod.network;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.flux.IFlux;
import sonar.calculator.mod.client.gui.calculators.GuiAtomicCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiCraftingCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiDynamicCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiFlawlessCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiInfoCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiDynamicModule;
import sonar.calculator.mod.client.gui.calculators.GuiScientificCalculator;
import sonar.calculator.mod.client.gui.generators.GuiCalculatorLocator;
import sonar.calculator.mod.client.gui.generators.GuiCalculatorPlug;
import sonar.calculator.mod.client.gui.generators.GuiConductorMast;
import sonar.calculator.mod.client.gui.generators.GuiCrankedGenerator;
import sonar.calculator.mod.client.gui.generators.GuiExtractor;
import sonar.calculator.mod.client.gui.machines.GuiAdvancedGreenhouse;
import sonar.calculator.mod.client.gui.machines.GuiAdvancedPowerCube;
import sonar.calculator.mod.client.gui.machines.GuiAnalysingChamber;
import sonar.calculator.mod.client.gui.machines.GuiAtomicMultiplier;
import sonar.calculator.mod.client.gui.machines.GuiBasicGreenhouse;
import sonar.calculator.mod.client.gui.machines.GuiDockingStation;
import sonar.calculator.mod.client.gui.machines.GuiDualOutputSmelting;
import sonar.calculator.mod.client.gui.machines.GuiFlawlessFurnace;
import sonar.calculator.mod.client.gui.machines.GuiFlawlessGreenhouse;
import sonar.calculator.mod.client.gui.machines.GuiHealthProcessor;
import sonar.calculator.mod.client.gui.machines.GuiHungerProcessor;
import sonar.calculator.mod.client.gui.machines.GuiPowerCube;
import sonar.calculator.mod.client.gui.machines.GuiResearchChamber;
import sonar.calculator.mod.client.gui.machines.GuiSmeltingBlock;
import sonar.calculator.mod.client.gui.machines.GuiStorageChamber;
import sonar.calculator.mod.client.gui.misc.GuiAlgorithmAssimilator;
import sonar.calculator.mod.client.gui.misc.GuiCO2Generator;
import sonar.calculator.mod.client.gui.misc.GuiFluxController;
import sonar.calculator.mod.client.gui.misc.GuiFluxPlug;
import sonar.calculator.mod.client.gui.misc.GuiFluxPoint;
import sonar.calculator.mod.client.gui.misc.GuiGasLantern;
import sonar.calculator.mod.client.gui.misc.GuiMagneticFlux;
import sonar.calculator.mod.client.gui.misc.GuiStoneAssimilator;
import sonar.calculator.mod.client.gui.misc.GuiTeleporter;
import sonar.calculator.mod.client.gui.misc.GuiWeatherController;
import sonar.calculator.mod.client.gui.modules.GuiRecipeInfo;
import sonar.calculator.mod.client.gui.modules.GuiSmeltingModule;
import sonar.calculator.mod.client.gui.modules.GuiStorageModule;
import sonar.calculator.mod.common.containers.ContainerAdvancedGreenhouse;
import sonar.calculator.mod.common.containers.ContainerAlgorithmAssimilator;
import sonar.calculator.mod.common.containers.ContainerAnalysingChamber;
import sonar.calculator.mod.common.containers.ContainerAssimilator;
import sonar.calculator.mod.common.containers.ContainerAtomicCalculator;
import sonar.calculator.mod.common.containers.ContainerAtomicMultiplier;
import sonar.calculator.mod.common.containers.ContainerBasicGreenhouse;
import sonar.calculator.mod.common.containers.ContainerCO2Generator;
import sonar.calculator.mod.common.containers.ContainerCalculator;
import sonar.calculator.mod.common.containers.ContainerCalculatorLocator;
import sonar.calculator.mod.common.containers.ContainerCalculatorPlug;
import sonar.calculator.mod.common.containers.ContainerConductorMast;
import sonar.calculator.mod.common.containers.ContainerCraftingCalculator;
import sonar.calculator.mod.common.containers.ContainerCrankedGenerator;
import sonar.calculator.mod.common.containers.ContainerDockingStation;
import sonar.calculator.mod.common.containers.ContainerDualOutputSmelting;
import sonar.calculator.mod.common.containers.ContainerDynamicCalculator;
import sonar.calculator.mod.common.containers.ContainerExtractor;
import sonar.calculator.mod.common.containers.ContainerFlawlessCalculator;
import sonar.calculator.mod.common.containers.ContainerFlawlessFurnace;
import sonar.calculator.mod.common.containers.ContainerFlawlessGreenhouse;
import sonar.calculator.mod.common.containers.ContainerFlux;
import sonar.calculator.mod.common.containers.ContainerFluxController;
import sonar.calculator.mod.common.containers.ContainerHealthProcessor;
import sonar.calculator.mod.common.containers.ContainerHungerProcessor;
import sonar.calculator.mod.common.containers.ContainerInfoCalculator;
import sonar.calculator.mod.common.containers.ContainerLantern;
import sonar.calculator.mod.common.containers.ContainerMagneticFlux;
import sonar.calculator.mod.common.containers.ContainerDynamicModule;
import sonar.calculator.mod.common.containers.ContainerPowerCube;
import sonar.calculator.mod.common.containers.ContainerResearchChamber;
import sonar.calculator.mod.common.containers.ContainerScientificCalculator;
import sonar.calculator.mod.common.containers.ContainerSmeltingBlock;
import sonar.calculator.mod.common.containers.ContainerSmeltingModule;
import sonar.calculator.mod.common.containers.ContainerStorageChamber;
import sonar.calculator.mod.common.containers.ContainerStorageModule;
import sonar.calculator.mod.common.containers.ContainerWeatherController;
import sonar.calculator.mod.common.item.calculators.FlawlessCalculator;
import sonar.calculator.mod.common.item.calculators.SonarModule;
import sonar.calculator.mod.common.item.modules.WIPSmeltingModule;
import sonar.calculator.mod.common.tileentity.TileEntityMachines;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankedGenerator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedPowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAnalysingChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAssimilator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.calculator.mod.common.tileentity.machines.TileEntityBasicGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityDockingStation;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessFurnace;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHealthProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHungerProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityPowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityResearchChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityWeatherController;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxController;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPlug;
import sonar.calculator.mod.common.tileentity.misc.TileEntityFluxPoint;
import sonar.calculator.mod.common.tileentity.misc.TileEntityGasLantern;
import sonar.calculator.mod.common.tileentity.misc.TileEntityMagneticFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityTeleporter;
import sonar.calculator.mod.network.packets.PacketCalculatorScreen;
import sonar.calculator.mod.network.packets.PacketFluxNetworkList;
import sonar.calculator.mod.network.packets.PacketFluxPoint;
import sonar.calculator.mod.network.packets.PacketJumpModule;
import sonar.calculator.mod.network.packets.PacketStorageChamber;
import sonar.calculator.mod.network.packets.PacketTeleportLinks;
import sonar.core.inventory.ContainerEmpty;
import sonar.core.inventory.IItemInventory;
import sonar.core.utils.IGuiItem;
import sonar.core.utils.IGuiTile;

public class CalculatorCommon implements IGuiHandler {

	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();

	public static void registerPackets() {
		Calculator.network.registerMessage(PacketStorageChamber.Handler.class, PacketStorageChamber.class, 0, Side.CLIENT);
		Calculator.network.registerMessage(PacketFluxPoint.Handler.class, PacketFluxPoint.class, 1, Side.SERVER);
		Calculator.network.registerMessage(PacketFluxNetworkList.Handler.class, PacketFluxNetworkList.class, 2, Side.CLIENT);
		Calculator.network.registerMessage(PacketCalculatorScreen.Handler.class, PacketCalculatorScreen.class, 3, Side.CLIENT);
		Calculator.network.registerMessage(PacketTeleportLinks.Handler.class, PacketTeleportLinks.class, 4, Side.CLIENT);
		Calculator.network.registerMessage(PacketJumpModule.Handler.class, PacketJumpModule.class, 5, Side.SERVER);
		Calculator.network.registerMessage(PacketJumpModule.Handler.class, PacketJumpModule.class, 6, Side.CLIENT);

	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
		if (entity != null) {
			switch (ID) {
			case IGuiTile.ID:
				return ((IGuiTile) entity).getGuiContainer(player);
			case CalculatorGui.NetworkSelect:
				if ((entity instanceof IFlux)) {
					return new ContainerFlux(player.inventory, (TileEntity) entity, false);
				}
				break;
			}
		} else {
			ItemStack equipped = player.getHeldItem();
			if (equipped != null) {
				switch (ID) {
				case IGuiItem.ID:
					return ((IGuiItem) equipped.getItem()).getGuiContainer(player, equipped);
				case CalculatorGui.InfoCalculator:
					return new ContainerInfoCalculator(player, player.inventory, world, x, y, z);
				case CalculatorGui.SmeltingModule:
					return new ContainerSmeltingModule(player, player.inventory, new WIPSmeltingModule.SmeltingInventory(equipped), equipped);
				case CalculatorGui.RecipeInfo:
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
			ItemStack equipped = player.getHeldItem();
			if (equipped != null) {
				switch (ID) {
				case IGuiItem.ID:
					return ((IGuiItem) equipped.getItem()).getGuiContainer(player, equipped);
				case CalculatorGui.InfoCalculator:
					return new GuiInfoCalculator(player, player.inventory, world, x, y, z);
				case CalculatorGui.SmeltingModule:
					return new GuiSmeltingModule(player, player.inventory, new WIPSmeltingModule.SmeltingInventory(equipped), equipped);
				case CalculatorGui.RecipeInfo:
					return new GuiRecipeInfo(player, player.inventory, world, x, y, z);
				}
			}

		}

		return null;
	}

	public void registerRenderThings() {}

	public static void storeEntityData(String name, NBTTagCompound compound) {
		extendedEntityData.put(name, compound);
	}

	public static NBTTagCompound getEntityData(String name) {
		return extendedEntityData.remove(name);
	}

}
