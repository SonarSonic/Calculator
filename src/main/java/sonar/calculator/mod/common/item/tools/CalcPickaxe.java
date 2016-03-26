package sonar.calculator.mod.common.item.tools;

import java.util.Random;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemPickaxe;

public class CalcPickaxe extends ItemPickaxe {
	private Random rand = new Random();

	public CalcPickaxe(ToolMaterial arg0) {
		super(arg0);
	}
}
