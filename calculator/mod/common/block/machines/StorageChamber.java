package sonar.calculator.mod.common.block.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IUpgradeCircuits;
import sonar.calculator.mod.common.tileentity.machines.TileEntityPowerCube;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarAPI;
import sonar.core.utils.SonarMaterials;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StorageChamber extends SonarMachineBlock {

	@SideOnly(Side.CLIENT)
	private IIcon front, front2, slot1, slot2;

	public StorageChamber() {
		super(SonarMaterials.machine);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.front = iconRegister.registerIcon("Calculator:storage_front_slot1");
		this.front2 = iconRegister.registerIcon("Calculator:storage_front_slot2");
		this.slot1 = iconRegister.registerIcon("Calculator:analysis_side_slot1");
		this.slot2 = iconRegister.registerIcon("Calculator:analysis_side_slot2");

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
					return t.getBlockTexture(side, metadata) ? this.slot1: this.slot2;
				}
				if (side == metadata) {
					return t.getBlockTexture(side, metadata) ? this.front: this.front2;
				}
			}
		}
		return this.slot1;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == metadata ? this.slot2 : side == 0 ? this.slot1: side == 1 ? this.slot1
						: (metadata == 0) && (side == 3) ? this.front: this.slot1;
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
				if (!world.isRemote) {
					player.openGui(Calculator.instance,	CalculatorGui.StorageChamber, world, x, y, z);
				}

		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityStorageChamber();
	}

	@Override
	public boolean dropStandard(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block oldblock,
			int oldMetadata) {
		world.removeTileEntity(x, y, z);

	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player,
			List list) {
		switch (stack.getTagCompound().getInteger("type")) {
		case 1:
			list.add(StatCollector.translateToLocal("circuit.type") + ": "
					+ StatCollector.translateToLocal("circuit.analysed"));
			break;
		case 2:
			list.add(StatCollector.translateToLocal("circuit.type") + ": "
					+ StatCollector.translateToLocal("circuit.stable"));
			break;

		case 3:
			list.add(StatCollector.translateToLocal("circuit.type") + ": "
					+ StatCollector.translateToLocal("circuit.damaged"));
			break;

		case 4:
			list.add(StatCollector.translateToLocal("circuit.type") + ": "
					+ StatCollector.translateToLocal("circuit.dirty"));
			break;
		}

		int[] stored = stack.stackTagCompound.getIntArray("stored");
		int total = 0;
		for (int i = 0; i < stored.length; i++) {
			total += stored[i];
		}
		if (total != 0) {
			list.add(StatCollector.translateToLocal("circuit.stored") + ": "+ total);
		}

	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {

	}
}
