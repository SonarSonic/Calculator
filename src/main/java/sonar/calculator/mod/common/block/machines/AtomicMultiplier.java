package sonar.calculator.mod.common.block.machines;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.machines.TileEntityAtomicMultiplier;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.utils.BlockInteraction;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AtomicMultiplier extends SonarMachineBlock {

	public AtomicMultiplier() {
		super(SonarMaterials.machine);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F - 0.0625F*3, 1.0F);
	}

	public boolean hasSpecialRenderer(){
		return true;		
	}	
	
	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ, BlockInteraction interact) {
		if (player != null) {
			if (!world.isRemote) {
				player.openGui(Calculator.instance, CalculatorGui.AtomicMultiplier, world, x, y, z);
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityAtomicMultiplier();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		TileEntityAtomicMultiplier te = (TileEntityAtomicMultiplier) world.getTileEntity(x, y, z);
		if (te.active == 1) {
			float x1 = x + random.nextFloat();
			float y1 = y + 0.5F;
			float z1 = z + random.nextFloat();

			world.spawnParticle("smoke", x1, y1, z1, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", x1, y1, z1, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
		list.add(FontHelper.translate("energy.required") + ": " + FontHelper.formatStorage(TileEntityAtomicMultiplier.requiredEnergy));

	}
}
