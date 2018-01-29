package sonar.calculator.mod.network;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import sonar.calculator.mod.client.renderers.*;
import sonar.calculator.mod.common.entities.EntityBabyGrenade;
import sonar.calculator.mod.common.entities.EntityGrenade;
import sonar.calculator.mod.common.entities.EntitySmallStone;
import sonar.calculator.mod.common.entities.EntitySoil;
import sonar.calculator.mod.common.tileentity.TileEntityMachine;
import sonar.calculator.mod.common.tileentity.generators.TileEntityConductorMast;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankHandle;
import sonar.calculator.mod.common.tileentity.machines.*;
import sonar.calculator.mod.common.tileentity.misc.TileEntityCalculatorScreen;
import sonar.calculator.mod.common.tileentity.misc.TileEntityMagneticFlux;
import sonar.calculator.mod.common.tileentity.misc.TileEntityReinforcedChest;
import sonar.calculator.mod.common.tileentity.misc.TileEntityScarecrow;

public class CalculatorClient extends CalculatorCommon{
	
	@Override
	public void registerRenderThings() {
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, RenderThrowables.GRENADE);
		RenderingRegistry.registerEntityRenderingHandler(EntityBabyGrenade.class, RenderThrowables.BABY_GRENADE);
		RenderingRegistry.registerEntityRenderingHandler(EntitySoil.class, RenderThrowables.SOIL);
		RenderingRegistry.registerEntityRenderingHandler(EntitySmallStone.class, RenderThrowables.SMALL_STONE);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrankHandle.class, new RenderCrank());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnalysingChamber.class, new RenderAnalysingChamber());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFabricationChamber.class, new RenderFabricationChamber());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityReinforcedChest.class, new RenderReinforcedChest());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConductorMast.class, new RenderHandlers.ConductorMast());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDockingStation.class, new RenderDockingStation());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConductorMast.class, new RenderHandlers.ConductorMast());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWeatherStation.class, new RenderWeatherStation());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityScarecrow.class, new RenderHandlers.Scarecrow());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransmitter.class, new RenderHandlers.Transmitter());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCalculatorScreen.class, new RenderCalculatorScreen());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachine.ExtractionChamber.class, new RenderChamber.Extraction());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachine.PrecisionChamber.class, new RenderChamber.Precision());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachine.ReassemblyChamber.class, new RenderChamber.Removal());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachine.RestorationChamber.class, new RenderChamber.Removal());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachine.ProcessingChamber.class, new RenderChamber.Processing());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMagneticFlux.class, new RenderMagneticFlux());
	}
}
