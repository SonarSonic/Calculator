package sonar.calculator.mod.common.item.modules;

import net.minecraft.client.renderer.texture.IIconRegister;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.item.calculators.SonarCalculator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EnergyModule extends SonarCalculator {
	public EnergyModule() {
		capacity = CalculatorConfig.moduleEnergy;
		maxReceive = CalculatorConfig.moduleEnergy;
		maxExtract = CalculatorConfig.moduleEnergy;
		maxTransfer = CalculatorConfig.moduleEnergy;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		this.itemIcon = iconregister.registerIcon("Calculator:energy_module");
	}

}
