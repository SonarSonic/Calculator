package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAdvancedGreenhouse;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.BlockInteractionType;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.helpers.FontHelper;

public class AdvancedGreenhouse extends SonarMachineBlock {
	
	public AdvancedGreenhouse() {
		super(SonarMaterials.machine, true, true);
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityAdvancedGreenhouse) {
			TileEntityAdvancedGreenhouse house = (TileEntityAdvancedGreenhouse) tile;
			if (interact.type == BlockInteractionType.SHIFT_RIGHT) {
				if (house.hasRequiredStacks() && house.storage.getEnergyStored() >= house.requiredBuildEnergy) {
					if (house.isIncomplete() && !house.wasBuilt() && !house.isBeingBuilt()) {
						FailedCoords coords = house.createBlock();
						if (!coords.getBoolean()) {
							FontHelper.sendMessage(FontHelper.translate("greenhouse.block") + " " + "X: " + coords.getCoords().getX() + " Y: " + coords.getCoords().getY() + " Z: " + coords.getCoords().getZ() + " - " + FontHelper.translate("greenhouse.blocking"), world, player);
						} else {
							FontHelper.sendMessage(FontHelper.translate("greenhouse.construction"), world, player);
						}
					}
				}
				if (house.isIncomplete() && !house.wasBuilt() && !house.isBeingBuilt()) {
					if (!house.hasRequiredStacks()) {

						FontHelper.sendMessage(house.getRequiredStacks(), world, player);

					} else if (!(house.storage.getEnergyStored() >= house.requiredBuildEnergy)) {

						if (!world.isRemote) {

							FontHelper.sendMessage(FontHelper.translate("energy.notEnough"), world, player);
						}

					}
				}
				if (!house.isBeingBuilt() && house.isIncomplete() && house.wasBuilt()) {
					FailedCoords coords = house.isComplete();
					if (!coords.getBoolean()) {
						FontHelper.sendMessage("X: " + coords.getCoords().getX() + " Y: " + coords.getCoords().getY() + " Z: " + coords.getCoords().getZ() + " - " + FontHelper.translate("greenhouse.equal") + " " + coords.getBlock(), world, player);
					}
				} else if (house.isCompleted()) {
					FontHelper.sendMessage(FontHelper.translate("greenhouse.complete"), world, player);

				}

			} else {
				if (player != null) {
					if (!world.isRemote) {
						player.openGui(Calculator.instance, CalculatorGui.AdvancedGreenhouse, world, pos.getX(), pos.getY(), pos.getZ());
					}
				}
			}
		}
		return true;

	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityAdvancedGreenhouse();
	}

	public boolean checkLog(Block block) {

		for (int i = 0; i < OreDictionary.getOres("logWood").size(); i++) {
			if (OreDictionary.getOres("logWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		for (int i = 0; i < OreDictionary.getOres("treeWood").size(); i++) {
			if (OreDictionary.getOres("treeWood").get(i).getItem() == Item.getItemFromBlock(block)) {
				return true;
			}
		}
		if (block instanceof BlockLog) {
			return true;
		}
		return false;
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);
		CalculatorHelper.addGasToolTip(stack, player, list);
	}
}
