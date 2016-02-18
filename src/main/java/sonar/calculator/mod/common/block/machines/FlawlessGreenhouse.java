package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.blocks.IConnectedBlock;
import sonar.calculator.mod.common.block.ConnectedBlock;
import sonar.calculator.mod.common.tileentity.machines.TileEntityFlawlessGreenhouse;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.BlockInteractionType;
import sonar.core.utils.FailedCoords;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FlawlessGreenhouse extends SonarMachineBlock implements IConnectedBlock {

	public int[] connections = new int[]{0,5,6};
	
	@SideOnly(Side.CLIENT)
	private IIcon iconFront, iconTop;

	public FlawlessGreenhouse() {
		super(SonarMaterials.machine);
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, BlockInteraction interact) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityFlawlessGreenhouse) {
			TileEntityFlawlessGreenhouse house = (TileEntityFlawlessGreenhouse) world.getTileEntity(x, y, z);
			if (interact.type == BlockInteractionType.SHIFT_RIGHT) {
				if (!house.isBeingBuilt() && house.isIncomplete()) {
					FailedCoords coords = house.isComplete();
					if (!coords.getBoolean()) {
						FontHelper.sendMessage("X: " + coords.getX() + " Y: " + coords.getY() + " Z: " + coords.getZ() + " - " + FontHelper.translate("greenhouse.equal") + " " + coords.getBlock(), world, player);
					}
				} else if (house.isCompleted()) {
					FontHelper.sendMessage(FontHelper.translate("greenhouse.complete"), world, player);

				}

			} else {
				if (player != null) {
					if (!world.isRemote) {
						player.openGui(Calculator.instance, CalculatorGui.FlawlessGreenhouse, world, x, y, z);
					}
				}
			}
		}
		return true;

	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityFlawlessGreenhouse();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon("Calculator:greenhouse_side");
		this.iconFront = iconRegister.registerIcon("Calculator:flawless_greenhouse_front");
		this.iconTop = iconRegister.registerIcon("Calculator:greenhouse_side");

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess w, int x, int y, int z, int s) {
		Block block = w.getBlock(x, y + 1, z);
		if (block != null && block instanceof ConnectedBlock) {
			ConnectedBlock connect = (ConnectedBlock) block;
			return connect.getSpecialIcon(w, x, y, z, s);
		}
		return getIcon(s, w.getBlockMetadata(x, y, z));

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == metadata ? this.iconFront : side == 0 ? this.iconTop : side == 1 ? this.iconTop : (metadata == 0) && (side == 3) ? this.iconFront : this.blockIcon;
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}

	@Override
	public int[] getConnections() {
		return connections;
	}

}
