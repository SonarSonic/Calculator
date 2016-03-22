package sonar.calculator.mod.common.item.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.core.common.item.SonarItem;
import sonar.core.utils.IUpgradeCircuits;
import sonar.core.utils.helpers.FontHelper;

public class UpgradeCircuit extends SonarItem {

	int type;

	public UpgradeCircuit(int type) {
		this.type = type;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitx, float hity, float hitz) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile != null && tile instanceof IUpgradeCircuits) {
			IUpgradeCircuits uTile = (IUpgradeCircuits) tile;
			if (!player.isSneaking()) {
				if (uTile.canAddUpgrades() && uTile.canAddUpgrades(type)) {
					if (uTile.getUpgrades(type) != uTile.getMaxUpgrades(type)) {
						uTile.incrementUpgrades(type, 1);
						stack.stackSize -= 1;
						return true;
					}

				} else if (!uTile.canAddUpgrades() && uTile.canAddUpgrades(type)) {
					FontHelper.sendMessage(FontHelper.translate("circuit.upgrade"), world, player);
				}
			} else {
				FontHelper.sendMessage(FontHelper.translate("circuit.acceptedUpgrades") + ": " + acceptsCircuit(0, uTile) + acceptsCircuit(1, uTile) + acceptsCircuit(2, uTile), world, player);
			}
		}
		return false;
	}

	public String acceptsCircuit(int circuit, IUpgradeCircuits tile) {
		if (tile.canAddUpgrades(circuit)) {
			return circuitName(circuit);
		}
		return "";
	}

	public String circuitName(int circuit) {
		if (circuit == 0) {
			return FontHelper.translate("item.SpeedUpgrade.name") + " ";
		} else if (circuit == 1) {
			return FontHelper.translate("item.EnergyUpgrade.name") + " ";
		} else if (circuit == 2) {
			return FontHelper.translate("item.VoidUpgrade.name") + " ";
		}
		return null;
	}

	public static Item getItem(int circuit) {
		switch (circuit) {
		case 0:
			return Calculator.speedUpgrade;
		case 1:
			return Calculator.energyUpgrade;
		case 3:
			return Calculator.voidUpgrade;
		}
		return null;
	}
}
