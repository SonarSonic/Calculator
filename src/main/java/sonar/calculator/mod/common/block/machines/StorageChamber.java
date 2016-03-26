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
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.BlockInteraction;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StorageChamber extends SonarMachineBlock {

	@SideOnly(Side.CLIENT)
	private IIcon slot1, slot2, empty;

	public StorageChamber() {
		super(SonarMaterials.machine);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.slot1 = iconRegister.registerIcon("Calculator:overlays/machine_input_storage_top");
		this.slot2 = iconRegister.registerIcon("Calculator:overlays/machine_output_storage_top");
		this.empty = iconRegister.registerIcon("Calculator:overlays/clear");
		this.blockIcon = iconRegister.registerIcon("Calculator:analysis_side_slot1");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
		TileEntity entity = access.getTileEntity(x, y, z);
		if (entity != null) {
			if (entity instanceof TileEntityStorageChamber) {
				TileEntityStorageChamber t = (TileEntityStorageChamber) access.getTileEntity(x, y, z);
				int metadata = access.getBlockMetadata(x, y, z);
				if (side != metadata) {
					return t.getBlockTexture(side, metadata) ? this.slot1 : this.slot2;
				}
			}
		}
		return empty;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return this.blockIcon;
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, BlockInteraction interact) {
		if (player != null) {
			if (!world.isRemote) {
				player.openGui(Calculator.instance, CalculatorGui.StorageChamber, world, x, y, z);
			}

		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityStorageChamber();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block oldblock, int oldMetadata) {
		world.removeTileEntity(x, y, z);

	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		switch (stack.getTagCompound().getInteger("type")) {
		case 1:
			list.add(FontHelper.translate("circuit.type") + ": " + FontHelper.translate("circuit.analysed"));
			break;
		case 2:
			list.add(FontHelper.translate("circuit.type") + ": " + FontHelper.translate("circuit.stable"));
			break;

		case 3:
			list.add(FontHelper.translate("circuit.type") + ": " + FontHelper.translate("circuit.damaged"));
			break;

		case 4:
			list.add(FontHelper.translate("circuit.type") + ": " + FontHelper.translate("circuit.dirty"));
			break;
		}

		int[] stored = stack.getTagCompound().getIntArray("stored");
		int total = 0;
		for (int i = 0; i < stored.length; i++) {
			total += stored[i];
		}
		if (total != 0) {
			list.add(FontHelper.translate("circuit.stored") + ": " + total);
		}

	}

	@Override
	public int getRenderType() {
		return 0;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

}
