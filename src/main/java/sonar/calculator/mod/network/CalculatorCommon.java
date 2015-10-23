package sonar.calculator.mod.network;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IFlux;
import sonar.calculator.mod.api.IResearchStore;
import sonar.calculator.mod.client.gui.calculators.GuiAtomicCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiCraftingCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiDynamicCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiFlawlessCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiInfoCalculator;
import sonar.calculator.mod.client.gui.calculators.GuiPortableDynamic;
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
import sonar.calculator.mod.client.gui.machines.GuiFlawlessGreenhouse;
import sonar.calculator.mod.client.gui.machines.GuiHealthProcessor;
import sonar.calculator.mod.client.gui.machines.GuiHungerProcessor;
import sonar.calculator.mod.client.gui.machines.GuiPowerCube;
import sonar.calculator.mod.client.gui.machines.GuiResearchChamber;
import sonar.calculator.mod.client.gui.machines.GuiSmeltingBlock;
import sonar.calculator.mod.client.gui.machines.GuiStorageChamber;
import sonar.calculator.mod.client.gui.misc.GuiAlgorithmAssimilator;
import sonar.calculator.mod.client.gui.misc.GuiStoneAssimilator;
import sonar.calculator.mod.client.gui.misc.GuiCO2Generator;
import sonar.calculator.mod.client.gui.misc.GuiFluxController;
import sonar.calculator.mod.client.gui.misc.GuiFluxPlug;
import sonar.calculator.mod.client.gui.misc.GuiFluxPoint;
import sonar.calculator.mod.client.gui.misc.GuiGasLantern;
import sonar.calculator.mod.client.gui.misc.GuiMagneticFlux;
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
import sonar.calculator.mod.common.containers.ContainerFlawlessGreenhouse;
import sonar.calculator.mod.common.containers.ContainerFlux;
import sonar.calculator.mod.common.containers.ContainerFluxController;
import sonar.calculator.mod.common.containers.ContainerHealthProcessor;
import sonar.calculator.mod.common.containers.ContainerHungerProcessor;
import sonar.calculator.mod.common.containers.ContainerInfoCalculator;
import sonar.calculator.mod.common.containers.ContainerLantern;
import sonar.calculator.mod.common.containers.ContainerMagneticFlux;
import sonar.calculator.mod.common.containers.ContainerPortableDynamic;
import sonar.calculator.mod.common.containers.ContainerPowerCube;
import sonar.calculator.mod.common.containers.ContainerResearchChamber;
import sonar.calculator.mod.common.containers.ContainerScientificCalculator;
import sonar.calculator.mod.common.containers.ContainerSmeltingBlock;
import sonar.calculator.mod.common.containers.ContainerSmeltingModule;
import sonar.calculator.mod.common.containers.ContainerStorageChamber;
import sonar.calculator.mod.common.containers.ContainerStorageModule;
import sonar.calculator.mod.common.containers.ContainerWeatherController;
import sonar.calculator.mod.common.item.calculators.CalculatorItem;
import sonar.calculator.mod.common.item.calculators.CraftingCalc;
import sonar.calculator.mod.common.item.calculators.FlawlessCalc;
import sonar.calculator.mod.common.item.modules.StorageModule;
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
import sonar.calculator.mod.network.packets.PacketStorageChamber;
import sonar.calculator.mod.network.packets.PacketTeleportLinks;
import sonar.calculator.mod.network.packets.PacketTextField;
import sonar.core.inventory.ContainerEmpty;
import sonar.core.network.PacketInventorySync;
import sonar.core.network.PacketMachineButton;
import sonar.core.network.PacketRequestSync;
import sonar.core.network.PacketSonarSides;
import sonar.core.network.PacketTileSync;
import sonar.core.utils.IItemInventory;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;

public class CalculatorCommon implements IGuiHandler {

	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();

	public static void registerPackets() {
		Calculator.network.registerMessage(PacketStorageChamber.Handler.class, PacketStorageChamber.class, 0, Side.CLIENT);
		Calculator.network.registerMessage(PacketFluxPoint.Handler.class, PacketFluxPoint.class, 1, Side.SERVER);
		Calculator.network.registerMessage(PacketFluxNetworkList.Handler.class, PacketFluxNetworkList.class, 2, Side.CLIENT);
		Calculator.network.registerMessage(PacketCalculatorScreen.Handler.class, PacketCalculatorScreen.class, 3, Side.CLIENT);
		Calculator.network.registerMessage(PacketTextField.Handler.class, PacketTextField.class, 4, Side.SERVER);
		Calculator.network.registerMessage(PacketTeleportLinks.Handler.class, PacketTeleportLinks.class, 5, Side.CLIENT);
		
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {		
		TileEntity entity = world.getTileEntity(x, y, z);
		ItemStack equipped;

		if (entity != null) {
			switch (ID) {
			case CalculatorGui.PowerCube:
				if ((entity instanceof TileEntityPowerCube)) {
					return new ContainerPowerCube(player.inventory, (TileEntityPowerCube) entity);
				}
			case CalculatorGui.StoneSeperator:
				if ((entity instanceof TileEntityMachines.StoneSeperator)) {
					return new ContainerDualOutputSmelting(player.inventory, (TileEntityMachines.StoneSeperator) entity);
				}
				return null;
			case CalculatorGui.AlgorithmSeperator:
				if ((entity instanceof TileEntityMachines.AlgorithmSeperator)) {
					return new ContainerDualOutputSmelting(player.inventory, (TileEntityMachines.AlgorithmSeperator) entity);
				}
				return null;
			case CalculatorGui.HungerProcessor:
				if ((entity instanceof TileEntityHungerProcessor)) {
					return new ContainerHungerProcessor(player.inventory, (TileEntityHungerProcessor) entity);
				}
			case CalculatorGui.CalculatorLocator:
				if ((entity instanceof TileEntityCalculatorLocator)) {
					return new ContainerCalculatorLocator(player.inventory, (TileEntityCalculatorLocator) entity);
				}
			case CalculatorGui.CalculatorPlug:
				if ((entity instanceof TileEntityCalculatorPlug)) {
					return new ContainerCalculatorPlug(player.inventory, (TileEntityCalculatorPlug) entity);
				}
			case CalculatorGui.StarchExtractor:
				if ((entity instanceof TileEntityGenerator.StarchExtractor)) {
					return new ContainerExtractor(player.inventory, (TileEntityGenerator.StarchExtractor) entity);
				}
			case CalculatorGui.CrankedGenerator:
				if ((entity instanceof TileEntityCrankedGenerator)) {
					return new ContainerCrankedGenerator(player.inventory, (TileEntityCrankedGenerator) entity);
				}
			case CalculatorGui.HealthProcessor:
				if ((entity instanceof TileEntityHealthProcessor)) {
					return new ContainerHealthProcessor(player.inventory, (TileEntityHealthProcessor) entity);
				}
			case CalculatorGui.RestorationChamber:
				if ((entity instanceof TileEntityMachines.RestorationChamber)) {
					return new ContainerSmeltingBlock(player.inventory, (TileEntityMachines.RestorationChamber) entity);
				}
			case CalculatorGui.ReassemblyChamber:
				if ((entity instanceof TileEntityMachines.ReassemblyChamber)) {
					return new ContainerSmeltingBlock(player.inventory, (TileEntityMachines.ReassemblyChamber) entity);
				}
			case CalculatorGui.ProcessingChamber:
				if ((entity instanceof TileEntityMachines.ProcessingChamber)) {
					return new ContainerSmeltingBlock(player.inventory, (TileEntityMachines.ProcessingChamber) entity);
				}
			case CalculatorGui.ExtractionChamber:
				if ((entity instanceof TileEntityMachines.ExtractionChamber)) {
					return new ContainerDualOutputSmelting(player.inventory, (TileEntityMachines.ExtractionChamber) entity);
				}
			case CalculatorGui.AnalysingChamber:
				if ((entity instanceof TileEntityAnalysingChamber)) {
					return new ContainerAnalysingChamber(player.inventory, (TileEntityAnalysingChamber) entity);
				}
			case CalculatorGui.GlowstoneExtractor:
				if ((entity instanceof TileEntityGenerator.GlowstoneExtractor)) {
					return new ContainerExtractor(player.inventory, (TileEntityGenerator.GlowstoneExtractor) entity);
				}
			case CalculatorGui.RedstoneExtractor:
				if ((entity instanceof TileEntityGenerator.RedstoneExtractor)) {
					return new ContainerExtractor(player.inventory, (TileEntityGenerator.RedstoneExtractor) entity);
				}
			case CalculatorGui.AtomicMultiplier:
				if ((entity instanceof TileEntityAtomicMultiplier)) {
					return new ContainerAtomicMultiplier(player.inventory, (TileEntityAtomicMultiplier) entity);
				}
			case CalculatorGui.ConductorMast:
				if ((entity instanceof TileEntityConductorMast)) {
					return new ContainerConductorMast(player.inventory, (TileEntityConductorMast) entity);
				}
			case CalculatorGui.PrecisionChamber:
				if ((entity instanceof TileEntityMachines.PrecisionChamber)) {
					return new ContainerDualOutputSmelting(player.inventory, (TileEntityMachines.PrecisionChamber) entity);
				}
			case CalculatorGui.AdvancedGreenhouse:
				if ((entity instanceof TileEntityAdvancedGreenhouse)) {
					return new ContainerAdvancedGreenhouse(player.inventory, (TileEntityAdvancedGreenhouse) entity);
				}

			case CalculatorGui.Lantern:
				if ((entity instanceof TileEntityGasLantern)) {
					return new ContainerLantern(player.inventory, (TileEntityGasLantern) entity);
				}
			case CalculatorGui.BasicGreenhouse:
				if ((entity instanceof TileEntityBasicGreenhouse)) {
					return new ContainerBasicGreenhouse(player.inventory, (TileEntityBasicGreenhouse) entity);
				}
			case CalculatorGui.FlawlessGreenhouse:
				if ((entity instanceof TileEntityFlawlessGreenhouse)) {
					return new ContainerFlawlessGreenhouse(player.inventory, (TileEntityFlawlessGreenhouse) entity);
				}
			case CalculatorGui.CO2Generator:
				if ((entity instanceof TileEntityCO2Generator)) {
					return new ContainerCO2Generator(player.inventory, (TileEntityCO2Generator) entity);
				}
			case CalculatorGui.advancedCube:
				if ((entity instanceof TileEntityAdvancedPowerCube)) {
					return new ContainerPowerCube(player.inventory, (TileEntityAdvancedPowerCube) entity);
				}
			case CalculatorGui.ReinforcedFurnace:
				if ((entity instanceof TileEntityMachines.ReinforcedFurnace)) {
					return new ContainerSmeltingBlock(player.inventory, (TileEntityMachines.ReinforcedFurnace) entity);
				}
			case CalculatorGui.DockingStation:
				if ((entity instanceof TileEntityDockingStation)) {
					return new ContainerDockingStation(player.inventory, (TileEntityDockingStation) entity);
				}
			case CalculatorGui.StorageChamber:
				if ((entity instanceof TileEntityStorageChamber)) {
					return new ContainerStorageChamber(player.inventory, (TileEntityStorageChamber) entity);
				}
			case CalculatorGui.ResearchChamber:
				if ((entity instanceof TileEntityResearchChamber)) {
					return new ContainerResearchChamber(player, (TileEntityResearchChamber) entity);
				}
			case CalculatorGui.DynamicCalculator:
				if ((entity instanceof TileEntityCalculator.Dynamic)) {
					return new ContainerDynamicCalculator(player, (TileEntityCalculator.Dynamic) entity);
				}
			case CalculatorGui.AtomicCalculator:
				if ((entity instanceof TileEntityCalculator.Atomic)) {
					return new ContainerAtomicCalculator(player, (TileEntityCalculator.Atomic) entity);
				}
			case CalculatorGui.FluxPoint:
				if ((entity instanceof TileEntityFluxPoint)) {
					return new ContainerFlux(player.inventory, (TileEntityFluxPoint) entity, false);
				}
			case CalculatorGui.FluxPlug:
				if ((entity instanceof TileEntityFluxPlug)) {
					return new ContainerFlux(player.inventory, (TileEntityFluxPlug) entity, false);
				}
			case CalculatorGui.FluxController:
				if ((entity instanceof TileEntityFluxController)) {
					return new ContainerFluxController(player.inventory, (TileEntityFluxController) entity);
				}
			case CalculatorGui.NetworkSelect:
				if ((entity instanceof IFlux)) {
					return new ContainerFlux(player.inventory, (TileEntity) entity, false);
				}
			case CalculatorGui.WeatherController:
				if ((entity instanceof TileEntityWeatherController)) {
					return new ContainerWeatherController(player.inventory, (TileEntityWeatherController) entity);
				}
			case CalculatorGui.MagneticFlux:
				if ((entity instanceof TileEntityMagneticFlux)) {
					return new ContainerMagneticFlux(player.inventory, (TileEntityMagneticFlux) entity);
				}
			case CalculatorGui.Teleporter:
				if ((entity instanceof TileEntityTeleporter)) {
					return new ContainerEmpty(player.inventory, (TileEntityTeleporter) entity);
				}
			case CalculatorGui.sAssimilator:
				if ((entity instanceof TileEntityAssimilator.Stone)) {
					return new ContainerAssimilator(player.inventory, (TileEntityAssimilator) entity);
				}
			case CalculatorGui.aAssimilator:
				if ((entity instanceof TileEntityAssimilator.Algorithm)) {
					return new ContainerAlgorithmAssimilator(player.inventory, (TileEntityAssimilator) entity);
				}
			default:
				break;
			}
		} else {
			equipped = player.getHeldItem();
			if (equipped == null) {
				return null;
			}

			switch (ID) {
			case CalculatorGui.Calculator:
				return new ContainerCalculator(player, player.inventory, new CalculatorItem.CalculatorInventory(equipped), ((IResearchStore) equipped.getItem()).getResearch(equipped));

			case CalculatorGui.ScientificCalculator:
				return new ContainerScientificCalculator(player, player.inventory, new CalculatorItem.CalculatorInventory(equipped));

			case CalculatorGui.CraftingCalculator:
				return new ContainerCraftingCalculator(player, player.inventory, new CraftingCalc.CraftingInventory(equipped));

			case CalculatorGui.FlawlessCalculator:
				return new ContainerFlawlessCalculator(player, player.inventory, new FlawlessCalc.FlawlessInventory(equipped));

			case CalculatorGui.InfoCalculator:
				return new ContainerInfoCalculator(player, player.inventory, world, x, y, z);

			case CalculatorGui.PortableDynamic:
				return new ContainerPortableDynamic(player, player.inventory, new FlawlessCalc.DynamicInventory(equipped), ((IResearchStore) equipped.getItem()).getResearch(equipped));

			case CalculatorGui.PortableCrafting:
				return new ContainerCraftingCalculator(player, player.inventory, new FlawlessCalc.CraftingInventory(equipped));

			case CalculatorGui.StorageModule:
				return new ContainerStorageModule(player, player.inventory, new StorageModule.StorageInventory(equipped));

			case CalculatorGui.SmeltingModule:

				return new ContainerSmeltingModule(player, player.inventory, new WIPSmeltingModule.SmeltingInventory(equipped), equipped);

			case CalculatorGui.RecipeInfo:
				return new ContainerInfoCalculator(player, player.inventory, world, x, y, z);

			default:
				break;
			}

		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		ItemStack equipped;
		if (entity != null) {
			switch (ID) {
			case CalculatorGui.PowerCube:
				if ((entity instanceof TileEntityPowerCube)) {
					return new GuiPowerCube(player.inventory, (TileEntityPowerCube) entity);
				}
				return null;

			case CalculatorGui.StoneSeperator:
				if ((entity instanceof TileEntityMachines.StoneSeperator)) {
					return new GuiDualOutputSmelting.StoneSeperator(player.inventory, (TileEntityMachines.StoneSeperator) entity);
				}
				return null;

			case CalculatorGui.AlgorithmSeperator:
				if ((entity instanceof TileEntityMachines.AlgorithmSeperator)) {
					return new GuiDualOutputSmelting.AlgorithmSeperator(player.inventory, (TileEntityMachines.AlgorithmSeperator) entity);
				}
				return null;

			case CalculatorGui.HungerProcessor:
				if ((entity instanceof TileEntityHungerProcessor)) {
					return new GuiHungerProcessor(player.inventory, (TileEntityHungerProcessor) entity);
				}

			case CalculatorGui.CalculatorLocator:
				if ((entity instanceof TileEntityCalculatorLocator)) {
					return new GuiCalculatorLocator(player.inventory, (TileEntityCalculatorLocator) entity);
				}

			case CalculatorGui.CalculatorPlug:
				if ((entity instanceof TileEntityCalculatorPlug)) {
					return new GuiCalculatorPlug(player.inventory, (TileEntityCalculatorPlug) entity);
				}

			case CalculatorGui.StarchExtractor:
				if ((entity instanceof TileEntityGenerator.StarchExtractor)) {
					return new GuiExtractor.Starch(player.inventory, (TileEntityGenerator.StarchExtractor) entity);
				}

			case CalculatorGui.CrankedGenerator:
				if ((entity instanceof TileEntityCrankedGenerator)) {
					return new GuiCrankedGenerator(player.inventory, (TileEntityCrankedGenerator) entity);
				}

			case CalculatorGui.HealthProcessor:
				if ((entity instanceof TileEntityHealthProcessor)) {
					return new GuiHealthProcessor(player.inventory, (TileEntityHealthProcessor) entity);
				}

			case CalculatorGui.RestorationChamber:
				if ((entity instanceof TileEntityMachines.RestorationChamber)) {
					return new GuiSmeltingBlock.RestorationChamber(player.inventory, (TileEntityMachines.RestorationChamber) entity);
				}

			case CalculatorGui.ReassemblyChamber:
				if ((entity instanceof TileEntityMachines.ReassemblyChamber)) {
					return new GuiSmeltingBlock.ReassemblyChamber(player.inventory, (TileEntityMachines.ReassemblyChamber) entity);
				}

			case CalculatorGui.ProcessingChamber:
				if ((entity instanceof TileEntityMachines.ProcessingChamber)) {
					return new GuiSmeltingBlock.ProcessingChamber(player.inventory, (TileEntityMachines.ProcessingChamber) entity);
				}

			case CalculatorGui.ExtractionChamber:
				if ((entity instanceof TileEntityMachines.ExtractionChamber)) {
					return new GuiDualOutputSmelting.ExtractionChamber(player.inventory, (TileEntityMachines.ExtractionChamber) entity);
				}

			case CalculatorGui.AnalysingChamber:
				if ((entity instanceof TileEntityAnalysingChamber)) {
					return new GuiAnalysingChamber(player.inventory, (TileEntityAnalysingChamber) entity);
				}

			case CalculatorGui.GlowstoneExtractor:
				if ((entity instanceof TileEntityGenerator.GlowstoneExtractor)) {
					return new GuiExtractor.Glowstone(player.inventory, (TileEntityGenerator.GlowstoneExtractor) entity);
				}

			case CalculatorGui.RedstoneExtractor:
				if ((entity instanceof TileEntityGenerator.RedstoneExtractor)) {
					return new GuiExtractor.Redstone(player.inventory, (TileEntityGenerator.RedstoneExtractor) entity);
				}

			case CalculatorGui.AtomicMultiplier:
				if ((entity instanceof TileEntityAtomicMultiplier)) {
					return new GuiAtomicMultiplier(player.inventory, (TileEntityAtomicMultiplier) entity);
				}

			case CalculatorGui.ConductorMast:
				if ((entity instanceof TileEntityConductorMast)) {
					return new GuiConductorMast(player.inventory, (TileEntityConductorMast) entity);
				}

			case CalculatorGui.PrecisionChamber:
				if ((entity instanceof TileEntityMachines.PrecisionChamber)) {
					return new GuiDualOutputSmelting.PrecisionChamber(player.inventory, (TileEntityMachines.PrecisionChamber) entity);
				}

			case CalculatorGui.AdvancedGreenhouse:
				if ((entity instanceof TileEntityAdvancedGreenhouse)) {
					return new GuiAdvancedGreenhouse(player.inventory, (TileEntityAdvancedGreenhouse) entity);
				}

			case CalculatorGui.Lantern:
				if ((entity instanceof TileEntityGasLantern)) {
					return new GuiGasLantern(player.inventory, (TileEntityGasLantern) entity);
				}

			case CalculatorGui.BasicGreenhouse:
				if ((entity instanceof TileEntityBasicGreenhouse)) {
					return new GuiBasicGreenhouse(player.inventory, (TileEntityBasicGreenhouse) entity);
				}

			case CalculatorGui.FlawlessGreenhouse:
				if ((entity instanceof TileEntityFlawlessGreenhouse)) {
					return new GuiFlawlessGreenhouse(player.inventory, (TileEntityFlawlessGreenhouse) entity);
				}

			case CalculatorGui.CO2Generator:
				if ((entity instanceof TileEntityCO2Generator)) {
					return new GuiCO2Generator(player.inventory, (TileEntityCO2Generator) entity);
				}
			case CalculatorGui.advancedCube:
				if ((entity instanceof TileEntityAdvancedPowerCube)) {
					return new GuiAdvancedPowerCube(player.inventory, (TileEntityAdvancedPowerCube) entity);
				}

			case CalculatorGui.ReinforcedFurnace:
				if ((entity instanceof TileEntityMachines.ReinforcedFurnace)) {
					return new GuiSmeltingBlock.ReinforcedFurnace(player.inventory, (TileEntityMachines.ReinforcedFurnace) entity);
				}

			case CalculatorGui.DockingStation:
				if ((entity instanceof TileEntityDockingStation)) {
					return new GuiDockingStation(player.inventory, (TileEntityDockingStation) entity);
				}
			case CalculatorGui.StorageChamber:
				if ((entity instanceof TileEntityStorageChamber)) {
					return new GuiStorageChamber(player.inventory, (TileEntityStorageChamber) entity);
				}
			case CalculatorGui.ResearchChamber:
				if ((entity instanceof TileEntityResearchChamber)) {
					return new GuiResearchChamber(player, (TileEntityResearchChamber) entity);
				}
			case CalculatorGui.DynamicCalculator:
				if ((entity instanceof TileEntityCalculator.Dynamic)) {
					return new GuiDynamicCalculator(player, (TileEntityCalculator.Dynamic) entity);
				}
			case CalculatorGui.AtomicCalculator:
				if ((entity instanceof TileEntityCalculator.Atomic)) {
					return new GuiAtomicCalculator(player, (TileEntityCalculator.Atomic) entity);
				}
			case CalculatorGui.FluxPoint:
				if ((entity instanceof TileEntityFluxPoint)) {
					return new GuiFluxPoint(player.inventory, (TileEntityFluxPoint) entity);
				}
			case CalculatorGui.FluxPlug:
				if ((entity instanceof TileEntityFluxPlug)) {
					return new GuiFluxPlug(player.inventory, (TileEntityFluxPlug) entity);
				}
			case CalculatorGui.FluxController:
				if ((entity instanceof TileEntityFluxController)) {
					return new GuiFluxController(player.inventory, (TileEntityFluxController) entity);
				}
			case CalculatorGui.WeatherController:
				if ((entity instanceof TileEntityWeatherController)) {
					return new GuiWeatherController(player.inventory, (TileEntityWeatherController) entity);
				}
			case CalculatorGui.MagneticFlux:
				if ((entity instanceof TileEntityMagneticFlux)) {
					return new GuiMagneticFlux(player.inventory, (TileEntityMagneticFlux) entity);
				}
			case CalculatorGui.Teleporter:
				if ((entity instanceof TileEntityTeleporter)) {
					return new GuiTeleporter(player.inventory, (TileEntityTeleporter) entity);
				}
			case CalculatorGui.sAssimilator:
				if ((entity instanceof TileEntityAssimilator.Stone)) {
					return new GuiStoneAssimilator(player.inventory, (TileEntityAssimilator) entity);
				}
			case CalculatorGui.aAssimilator:
				if ((entity instanceof TileEntityAssimilator.Algorithm)) {
					return new GuiAlgorithmAssimilator(player.inventory, (TileEntityAssimilator) entity);
				}
			default:
				break;
			}

		} else {
			equipped = player.getHeldItem();
			if (equipped == null) {
				return null;
			}
			switch (ID) {
			case CalculatorGui.Calculator:
				return new GuiCalculator(player, player.inventory, new CalculatorItem.CalculatorInventory(equipped), ((IResearchStore) equipped.getItem()).getResearch(equipped));

			case CalculatorGui.ScientificCalculator:
				return new GuiScientificCalculator(player, player.inventory, new CalculatorItem.CalculatorInventory(equipped));

			case CalculatorGui.CraftingCalculator:
				return new GuiCraftingCalculator(player, player.inventory, new CraftingCalc.CraftingInventory(equipped));

			case CalculatorGui.FlawlessCalculator:
				Item item = player.getHeldItem().getItem();
				if (item != null && item instanceof IItemInventory) {
					IItemInventory target = (IItemInventory) item;
					return new GuiFlawlessCalculator(player, player.inventory, target.getInventory(equipped));
				}
				break;

			case CalculatorGui.InfoCalculator:
				return new GuiInfoCalculator(player, player.inventory, world, x, y, z);

			case CalculatorGui.PortableDynamic:
				return new GuiPortableDynamic(player, player.inventory, new FlawlessCalc.DynamicInventory(equipped), ((IResearchStore) equipped.getItem()).getResearch(equipped));

			case CalculatorGui.PortableCrafting:
				return new GuiCraftingCalculator(player, player.inventory, new CraftingCalc.CraftingInventory(equipped));

			case CalculatorGui.StorageModule:
				return new GuiStorageModule(player, player.inventory, new StorageModule.StorageInventory(equipped));

			case CalculatorGui.SmeltingModule:
				return new GuiSmeltingModule(player, player.inventory, new WIPSmeltingModule.SmeltingInventory(equipped), equipped);

			case CalculatorGui.RecipeInfo:
				return new GuiRecipeInfo(player, player.inventory, world, x, y, z);

			default:
				break;
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
