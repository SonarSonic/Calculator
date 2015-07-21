package sonar.calculator.mod.common.block.generators;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.utils.SonarMaterials;
import sonar.core.utils.helpers.FontHelper;

public class ExtractorBlock extends SonarMachineBlock {
	public int type;

	public ExtractorBlock(int type) {
		super(SonarMaterials.machine, false);
		this.type = type;
		this.setBlockBounds(0.0625F, 0.0625F, 0.0625F, 1 - 0.0625F, 1 - 0.0625F, 1 - 0.0625F);
	}

	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player != null) {
			if (!world.isRemote) {
				switch (type) {
				case 0:
					player.openGui(Calculator.instance, CalculatorGui.StarchExtractor, world, x, y, z);
					break;
				case 1:
					player.openGui(Calculator.instance, CalculatorGui.RedstoneExtractor, world, x, y, z);
					break;
				case 2:
					player.openGui(Calculator.instance, CalculatorGui.GlowstoneExtractor, world, x, y, z);
					break;
				}
			}
		}
		return true;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		TileEntityGenerator generator = (TileEntityGenerator) world.getTileEntity(x, y, z);
		generator.updateAdjacentHandlers();
	}

	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityGenerator) {
			TileEntityGenerator generator = (TileEntityGenerator) world.getTileEntity(x, y, z);
			generator.updateAdjacentHandlers();
		}

	}

	@Override
	public TileEntity createNewTileEntity(World world, int var) {
		switch (type) {
		case 0:
			return new TileEntityGenerator.StarchExtractor();
		case 1:
			return new TileEntityGenerator.RedstoneExtractor();
		case 2:
			return new TileEntityGenerator.GlowstoneExtractor();
		}
		return new TileEntityGenerator.StarchExtractor();
	}

	@Override
	public void addSpecialToolTip(ItemStack stack, EntityPlayer player, List list) {
		CalculatorHelper.addEnergytoToolTip(stack, player, list);
		CalculatorHelper.addItemLevelToolTip(stack, player, list);

	}

	@Override
	public void standardInfo(ItemStack stack, EntityPlayer player, List list) {

		switch (type) {
		case 0:
			list.add(FontHelper.translate("energy.generate") + ": " + CalculatorConfig.starchRF + " RF/t");
			break;
		case 1:
			list.add(FontHelper.translate("energy.generate") + ": " + CalculatorConfig.redstoneRF + " RF/t");
			break;
		case 2:
			list.add(FontHelper.translate("energy.generate") + ": " + CalculatorConfig.glowstoneRF + " RF/t");
			break;
		}
	}
}
