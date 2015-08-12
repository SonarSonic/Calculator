package sonar.calculator.mod.common.entities;

import net.minecraft.util.DamageSource;

public class CalculatorDamages extends DamageSource {

	public static DamageSource smallstone = new DamageSource("stone");

	public CalculatorDamages(String source) {
		super(source);
	}

}
