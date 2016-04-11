package sonar.calculator.mod.common.block.generators;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.tileentity.generators.TileEntityCrankedGenerator;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.IGuiTile;

public class CrankedGenerator extends SonarMachineBlock {
	public CrankedGenerator() {
		super(SonarMaterials.machine, true, true);
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, EntityPlayer player, BlockInteraction interact) {
		if (!world.isRemote && player != null) {
			player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());

		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityCrankedGenerator();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);

	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {
		list.add(FontHelper.translate("energy.generate") + ": " + 18 + " RF/t");

	}
}
