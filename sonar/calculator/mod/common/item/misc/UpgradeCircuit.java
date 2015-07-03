package sonar.calculator.mod.common.item.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IUpgradeCircuits;
import sonar.calculator.mod.common.item.CalcItem;
import sonar.core.utils.helpers.FontHelper;

public class UpgradeCircuit extends CalcItem {

	int type;

	public UpgradeCircuit(int type) {
		this.type = type;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z, int par7, float par8, float par9, float par10) {
		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile != null && tile instanceof IUpgradeCircuits) {
			IUpgradeCircuits uTile = (IUpgradeCircuits) tile;
			if (!player.isSneaking()) {
				if (uTile.canAddUpgrades() && uTile.canAddUpgrades(type)) {
					if (uTile.getUpgrades(type) != uTile.getMaxUpgrades(type)) {
						uTile.incrementUpgrades(type, 1);
						stack.stackSize -= 1;
						return true;
					}

				} else if (!uTile.canAddUpgrades()
						&& uTile.canAddUpgrades(type)) {
					FontHelper.sendMessage(FontHelper.translate("circuit.upgrade"),world, player);
				}
			} else {
				FontHelper.sendMessage(FontHelper.translate("circuit.acceptedUpgrades")
								+ ": "
								+ acceptsCircuit(0, uTile)
								+ acceptsCircuit(1, uTile)
								+ acceptsCircuit(2, uTile), world, player);

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
			return FontHelper.translate("item.SpeedUpgrade.name")
					+ " ";
		} else if (circuit == 1) {
			return FontHelper.translate("item.EnergyUpgrade.name")
					+ " ";
		} else if (circuit == 2) {
			return FontHelper.translate("item.VoidUpgrade.name")
					+ " ";
		}
		return null;
	}
	public static Item getItem(int circuit){
		switch(circuit){
		case 0: return Calculator.speedUpgrade;
		case 1: return Calculator.energyUpgrade;
		case 3: return Calculator.voidUpgrade;
		}
		return null;
	}
}
