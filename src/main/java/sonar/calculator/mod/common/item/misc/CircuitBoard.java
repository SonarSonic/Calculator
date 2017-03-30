package sonar.calculator.mod.common.item.misc;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.api.items.IStability;
import sonar.core.api.utils.ICalculatorCircuit;
import sonar.core.common.item.SonarMetaItem;
import sonar.core.helpers.FontHelper;

public class CircuitBoard extends SonarMetaItem implements IStability, ICalculatorCircuit {

	public CircuitBoard() {
		super(14);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		if (stack.hasTagCompound()) {
			int stable = stack.getTagCompound().getInteger("Stable");
			if (stack.getTagCompound().getBoolean("Analysed")) {
				list.add(FontHelper.translate(stable == 1 ? "circuit.stable" : "circuit.analysed"));
			} else {
				list.add(FontHelper.translate("Not analysed"));
			}
		}
	}

	public static void setData(ItemStack stack) {
		NBTTagCompound nbtData = stack.getTagCompound();
		if (stack != null) {
			if (nbtData == null) {
				int energy = 1 + (int) (Math.random() * 200.0D);
				int item1 = 1 + (int) (Math.random() * 50.0D);
				int item2 = 1 + (int) (Math.random() * 100.0D);
				int item3 = 1 + (int) (Math.random() * 1000.0D);
				int item4 = 1 + (int) (Math.random() * 2000.0D);
				int item5 = 1 + (int) (Math.random() * 10000.0D);
				int item6 = 1 + (int) (Math.random() * 20000.0D);
				int stable = 1 + (int) (Math.random() * 6.0D);
				nbtData = new NBTTagCompound();
				nbtData.setInteger("Energy", energy);
				nbtData.setInteger("Item1", item1);
				nbtData.setInteger("Item2", item2);
				nbtData.setInteger("Item3", item3);
				nbtData.setInteger("Item4", item4);
				nbtData.setInteger("Item5", item5);
				nbtData.setInteger("Item6", item6);
				nbtData.setInteger("Stable", stable);
				stack.setTagCompound(nbtData);
			}

		}
	}

	public void onUpdate(ItemStack stack, World world, Entity entity, int par, boolean bool) {
		if (stack.getTagCompound() == null && !stack.hasTagCompound()) {
			setData(stack);
		}
	}

	@Override
	public boolean getStability(ItemStack stack) {
		if (stack.hasTagCompound()) {
			return stack.getTagCompound().getInteger("Stable") == 1;
		}
		return false;
	}

	@Override
	public void onFalse(ItemStack stack) {
		stack.getTagCompound().setInteger("Stable", 0);
	}

	public boolean hasEffect(ItemStack stack) {
		return getStability(stack);
	}
}
