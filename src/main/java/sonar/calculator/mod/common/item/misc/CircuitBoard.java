package sonar.calculator.mod.common.item.misc;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sonar.calculator.mod.api.items.IStability;
import sonar.core.SonarCore;
import sonar.core.api.utils.ICalculatorCircuit;
import sonar.core.common.item.SonarMetaItem;
import sonar.core.helpers.FontHelper;

import java.util.List;

public class CircuitBoard extends SonarMetaItem implements IStability, ICalculatorCircuit {

	public CircuitBoard() {
		super(14);
	}

	public enum CircuitState{
		STABLE("circuit.stable", "A"),
		ANALYSED("circuit.analysed", "B"),
		NOT_ANALYSED("Not analysed", "");

		String translate;
		String suffix;

		CircuitState(String translate, String suffix){
			this.translate = translate;
			this.suffix = suffix;
		}
	}

	public static CircuitState getState(ItemStack stack){
		if (!stack.isEmpty() && stack.getItem() instanceof CircuitBoard && stack.hasTagCompound() && stack.getTagCompound().getBoolean("Analysed")) {
			int stable = stack.getTagCompound().getInteger("Stable");
			return stable == 1 ? CircuitState.STABLE : CircuitState.ANALYSED;
		}
		return CircuitState.NOT_ANALYSED;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag par4) {
		super.addInformation(stack, world, list, par4);
		CircuitState state = getState(stack);
		if(state.suffix.isEmpty()) {
			list.add(FontHelper.translate(state.translate));
		}else{
			list.add(state.suffix + ": " +FontHelper.translate(state.translate));
		}
	}
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return super.getItemStackDisplayName(stack) + getState(stack).suffix;
	}

	public static void setData(ItemStack stack) {
		NBTTagCompound nbtData = stack.getTagCompound();
		if (nbtData == null) {
			nbtData = new NBTTagCompound();
			nbtData.setInteger("Energy", SonarCore.randInt(1, 200));
			nbtData.setInteger("Item1", SonarCore.randInt(1, 50));
			nbtData.setInteger("Item2", SonarCore.randInt(1, 100));
			nbtData.setInteger("Item3", SonarCore.randInt(1, 1000));
			nbtData.setInteger("Item4", SonarCore.randInt(1, 2000));
			nbtData.setInteger("Item5", SonarCore.randInt(1, 10000));
			nbtData.setInteger("Item6", SonarCore.randInt(1, 20000));
			nbtData.setInteger("Stable", SonarCore.randInt(1, 6));
			stack.setTagCompound(nbtData);
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par, boolean bool) {
		super.onUpdate(stack, world, entity, par, bool);
		if (stack.getTagCompound() == null && !stack.hasTagCompound()) {
			setData(stack);
		}
	}

	@Override
	public boolean getStability(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().getInteger("Stable") == 1;
	}

	@Override
	public void onFalse(ItemStack stack) {
		stack.getTagCompound().setInteger("Stable", 0);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return getStability(stack);
	}
}
