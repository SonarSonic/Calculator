package sonar.calculator.mod.integration.waila;

import mcp.mobius.waila.api.impl.ModuleRegistrar;
import sonar.calculator.mod.common.tileentity.TileEntityAbstractProcess;
import sonar.calculator.mod.common.tileentity.TileEntityGreenhouse;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorLocator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCalculatorPlug;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankedGenerator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAnalysingChamber;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHealthProcessor;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHungerProcessor;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCO2Generator;
import sonar.calculator.mod.common.tileentity.misc.TileEntityGasLantern;

/** Integrations with WAILA - Registers all HUDs */
public class CalculatorWailaModule {

	public static void register() {
		ModuleRegistrar.instance().registerBodyProvider(new HUDCircuitUpgrades(), TileEntityAbstractProcess.class);
		ModuleRegistrar.instance().registerBodyProvider(new HUDCircuitUpgrades(), TileEntityAnalysingChamber.class);
		ModuleRegistrar.instance().registerBodyProvider(new HUDHungerPoints(), TileEntityHungerProcessor.class);
		ModuleRegistrar.instance().registerBodyProvider(new HUDHealthPoints(), TileEntityHealthProcessor.class);
		ModuleRegistrar.instance().registerBodyProvider(new HUDLightningEnergy(), TileEntityConductorMast.class);
		ModuleRegistrar.instance().registerBodyProvider(new HUDCalculatorLocator(), TileEntityCalculatorLocator.class);
		ModuleRegistrar.instance().registerBodyProvider(new HUDCalculatorPlug(), TileEntityCalculatorPlug.class);
		ModuleRegistrar.instance().registerBodyProvider(new HUDLantern(), TileEntityGasLantern.class);
		ModuleRegistrar.instance().registerBodyProvider(new HUDCO2Generator(), TileEntityCO2Generator.class);
		ModuleRegistrar.instance().registerBodyProvider(new HUDAtomicMultiplier(), TileEntityAtomicMultiplier.class);
		ModuleRegistrar.instance().registerBodyProvider(new HUDGenerator(), TileEntityGenerator.class);
		ModuleRegistrar.instance().registerBodyProvider(new HUDCrankGenerator(), TileEntityCrankedGenerator.class);
		ModuleRegistrar.instance().registerBodyProvider(new HUDGreenhouse(), TileEntityGreenhouse.class);

	}

}