package sonar.calculator.mod.common.item.misc;

import java.util.List;
import java.util.Map;

import sonar.calculator.mod.api.IStability;
import sonar.core.common.item.SonarItem;
import sonar.core.utils.helpers.FontHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCircuit extends SonarItem implements IStability {
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		if (stack.getTagCompound() != null) {
			int stable = stack.getTagCompound().getInteger("Stable");
			if (stable == 1) {
				list.add(FontHelper.translate("circuit.stable"));
			}
		}
	}

	public static void setData(ItemStack stack) {
		NBTTagCompound nbtData = stack.getTagCompound();
		if (stack != null) {
			if (nbtData == null) {
				int energy = 1 + (int) (Math.random() * 100.0D);
				int item1 = 1 + (int) (Math.random() * 25.0D);
				int item2 = 1 + (int) (Math.random() * 50.0D);
				int item3 = 1 + (int) (Math.random() * 500.0D);
				int item4 = 1 + (int) (Math.random() * 1000.0D);
				int item5 = 1 + (int) (Math.random() * 5000.0D);
				int item6 = 1 + (int) (Math.random() * 10000.0D);
				int stable = 1 + (int) (Math.random() * 4.0D);
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
			NBTTagCompound nbtData = stack.getTagCompound();
			if (stack != null) {
				if (nbtData == null) {
					int energy = 1 + (int) (Math.random() * 100.0D);
					int item1 = 1 + (int) (Math.random() * 25.0D);
					int item2 = 1 + (int) (Math.random() * 50.0D);
					int item3 = 1 + (int) (Math.random() * 500.0D);
					int item4 = 1 + (int) (Math.random() * 1000.0D);
					int item5 = 1 + (int) (Math.random() * 5000.0D);
					int item6 = 1 + (int) (Math.random() * 10000.0D);
					int stable = 1 + (int) (Math.random() * 4.0D);
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
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		CircuitType[] atype = CircuitType.values();
		int i = atype.length;

		for (int j = 0; j < i; j++) {
			CircuitType type = atype[j];
			type.registerIcon(register);
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int p_77617_1_) {
		CircuitType type = CircuitType.getTypeFromDamage(p_77617_1_);
		return type.getIcon();
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		CircuitType[] atype = CircuitType.values();
		int i = atype.length;

		for (int j = 0; j < i; j++) {
			CircuitType type = atype[j];

			list.add(new ItemStack(this, 1, type.getItemDamage()));
		}
	}

	public String getUnlocalizedName(ItemStack stack) {
		CircuitType type = CircuitType.getTypeFromStack(stack);
		return getUnlocalizedName() + "." + type.name();
	}

	public static enum CircuitType {
		C1(0, "1"), C2(1, "2"), C3(2, "3"), C4(3, "4"), C5(4, "5"), C6(5, "6"), C7(6, "7"), C8(7, "8"), C9(8, "9"), C10(9, "10"), C11(10, "11"), C12(11, "12"), C13(12, "13"), C14(13, "14");

		private static final Map circuits;
		private final int number;
		private final String name;
		@SideOnly(Side.CLIENT)
		private IIcon icon;

		private CircuitType(int number, String name) {
			this.number = number;
			this.name = name;
		}

		public int getItemDamage() {
			return this.number;
		}

		public String getUnlocalizedNamePart() {
			return this.name;
		}

		@SideOnly(Side.CLIENT)
		public void registerIcon(IIconRegister register) {
			this.icon = register.registerIcon("Calculator:circuits/circuit" + this.name);
		}

		@SideOnly(Side.CLIENT)
		public IIcon getIcon() {
			return this.icon;
		}

		public static CircuitType getTypeFromDamage(int par) {
			CircuitType type = (CircuitType) circuits.get(Integer.valueOf(par));
			return type == null ? C1 : type;
		}

		public static CircuitType getTypeFromStack(ItemStack stack) {
			return (stack.getItem() instanceof ItemCircuit) ? getTypeFromDamage(stack.getItemDamage()) : C1;
		}

		static {
			circuits = Maps.newHashMap();

			CircuitType[] var0 = values();
			int var1 = var0.length;

			for (int var2 = 0; var2 < var1; var2++) {
				CircuitType var3 = var0[var2];
				circuits.put(Integer.valueOf(var3.getItemDamage()), var3);
			}
		}
	}

	@Override
	public boolean getStability(ItemStack stack) {
		if (stack.hasTagCompound()) {
			return stack.getTagCompound().getInteger("Stable") == 1;
		} else {
			this.setData(stack);
			return stack.getTagCompound().getInteger("Stable") == 1;
		}
	}

	@Override
	public void onFalse(ItemStack stack) {
		stack.getTagCompound().setInteger("Stable", 0);
	}
}
