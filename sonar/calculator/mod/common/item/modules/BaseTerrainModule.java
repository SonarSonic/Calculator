package sonar.calculator.mod.common.item.modules;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.item.calculators.SonarCalculator;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BaseTerrainModule extends SonarCalculator {

	public Block[] replacable;

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		if (stack.hasTagCompound()) {
			list.add(FontHelper.translate("calc.mode") + ": " + currentBlockString(stack, player));
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
		if (this.getEnergyStored(stack) > 0) {
			if (!player.canPlayerEdit(x, y, z, par7, stack)) {
				return false;
			}
			if (player.isSneaking()) {
				incrementMode(stack);
				FontHelper.sendMessage(currentBlockString(stack, player), world, player);
			} else {
				if (canReplace(stack, world, x, y, z)) {
					world.setBlock(x, y, z, getCurrentBlock(stack));
					int energy = this.getEnergyStored(stack);
					stack.stackTagCompound.setInteger("Energy", energy - 1);
				}
			}
		}

		if (this.getEnergyStored(stack) == 0) {
			FontHelper.sendMessage(FontHelper.translate("energy.noEnergy"), world, player);
		}
		return true;
	}

	public String currentBlockString(ItemStack stack, EntityPlayer player) {
		return new ItemStack(getCurrentBlock(stack), 1).getDisplayName();
	}

	public Block getCurrentBlock(ItemStack stack) {
		return replacable[getCurrentMode(stack)];
	}

	public int getCurrentMode(ItemStack stack) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbtData = stack.stackTagCompound;
		if (nbtData == null) {
			stack.stackTagCompound.setInteger("Mode", 0);
		}
		return stack.stackTagCompound.getInteger("Mode");
	}

	public void incrementMode(ItemStack stack) {
		int current = this.getCurrentMode(stack);
		if (current + 1 != replacable.length) {
			stack.stackTagCompound.setInteger("Mode", current + 1);
		} else {
			stack.stackTagCompound.setInteger("Mode", 0);
		}
	}

	public boolean canReplace(ItemStack stack, World world, int x, int y, int z) {
		if (getCurrentBlock(stack) == world.getBlock(x, y, z)) {
			return false;
		} else if (replaceableBlock(world.getBlock(x, y, z))) {
			return true;
		}
		return false;
	}

	public boolean replaceableBlock(Block block) {
		for (int s = 0; s < replacable.length; s++) {
			if (replacable[s] != null) {
				if (block == replacable[s]) {
					return true;
				}
			}
		}
		return false;
	}
}
