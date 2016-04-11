package sonar.calculator.mod.research;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import sonar.calculator.mod.Calculator;
import sonar.core.helpers.NBTHelper.SyncType;

public abstract class CalculatorResearch extends Research {

	public long count, required;
	public String name;

	public static class Basic extends CalculatorResearch {

		public Basic() {
			super(10000, "Calculator", "item.Calculator.name", Calculator.itemCalculator);
		}

		@Override
		public IResearch getInstance() {
			return new Basic();
		}
	}

	public static class Scientific extends CalculatorResearch {

		public Scientific() {
			super(5000, "Scientific", "item.ScientificCalculator.name", Calculator.itemScientificCalculator);
		}

		@Override
		public IResearch getInstance() {
			return new Scientific();
		}
	}

	public static class Atomic extends CalculatorResearch {

		public Atomic() {
			super(2500, "Atomic", "tile.AtomicCalculator.name", Item.getItemFromBlock(Calculator.atomicCalculator));
		}

		@Override
		public IResearch getInstance() {
			return new Scientific();
		}
	}

	public static class Flawless extends CalculatorResearch {

		public Flawless() {
			super(1000, "Flawless", "item.FlawlessCalculator.name", Calculator.itemFlawlessCalculator);
		}

		@Override
		public IResearch getInstance() {
			return new Flawless();
		}
	}

	@Override
	public ArrayList<IReward> getRewards() {
		return new ArrayList();
	}

	public CalculatorResearch(long required, String name, String clientName, Item logo) {
		super(name, clientName, logo);
		this.required = required;
	}

	@Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		count = nbt.getLong("count");
	}

	@Override
	public void writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		nbt.setLong("count", count);
	}

	@Override
	public String getHint() {
		return "Become a master of the " + name;
	}

	@Override
	public byte getProgress() {
		long current = count;
		if (current > required) {
			current = required;
		}
		return (byte) ((double) (current / required) * 100);
	}

	public ResearchCategory getResearchType() {
		return ResearchCategory.CALCULATORS;
	}

}
