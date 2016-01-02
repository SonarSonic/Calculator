package sonar.calculator.mod.common.block.machines;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityHealthProcessor;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HealthProcessor extends SonarMachineBlock {
	@SideOnly(Side.CLIENT)
	private IIcon iconFront, front,front2,slot1,slot2;
	
	public HealthProcessor() {
		super(SonarMaterials.machine);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.front = iconRegister.registerIcon("Calculator:healthprocessor_front2");
		this.front2 = iconRegister.registerIcon("Calculator:healthprocessor_front1");
		this.slot1 = iconRegister.registerIcon("Calculator:healthprocessor_slot2");
		this.slot2 = iconRegister.registerIcon("Calculator:healthprocessor_slot1");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
		TileEntityHealthProcessor t = (TileEntityHealthProcessor) access.getTileEntity(x, y, z);
		int metadata = access.getBlockMetadata(x, y, z);
		if (side != metadata) {
			return t.getBlockTexture(side, metadata) ? this.slot1 : this.slot2;
		}
		if (side == metadata) {
			return t.getBlockTexture(side, metadata) ? this.front : this.front2;
		}
		return this.slot1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == metadata ? this.slot2 : side == 0 ? this.slot1 : side == 1 ? this.slot1 : (metadata == 0) && (side == 3) ? this.front : this.slot1;
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if ((player.getHeldItem() != null) && (player.getHeldItem().getItem() == Calculator.wrench)) {
			return false;
		}
		if (player != null) {
			if (!world.isRemote) {
				player.openGui(Calculator.instance, CalculatorGui.HealthProcessor, world, x, y, z);
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityHealthProcessor();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		int health = stack.getTagCompound().getInteger("Food");
		if (health != 0) {
			list.add(FontHelper.translate("points.health") + ": " + health);
		}
	}

}
