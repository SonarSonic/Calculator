package sonar.calculator.mod.common.block.generators;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.common.tileentity.generators.TileEntityGenerator;
import sonar.calculator.mod.utils.helpers.CalculatorHelper;
import sonar.core.api.utils.BlockInteraction;
import sonar.core.common.block.SonarMachineBlock;
import sonar.core.common.block.SonarMaterials;
import sonar.core.helpers.FontHelper;
import sonar.core.utils.IGuiTile;

import java.util.List;

public class ExtractorBlock extends SonarMachineBlock {
	public int type;

	public ExtractorBlock(int type) {
		super(SonarMaterials.machine, false, true);
		this.type = type;
		this.setBlockBounds(0.0625F, 0.0625F, 0.0625F, 1 - 0.0625F, 1 - 0.0625F, 1 - 0.0625F);
	}

    @Override
	public boolean hasSpecialRenderer() {
		return true;
	}

	@Override
	public boolean operateBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, BlockInteraction interact) {
		if (player != null && !world.isRemote) {
			player.openGui(Calculator.instance, IGuiTile.ID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity != null && tileentity instanceof TileEntityGenerator) {
			TileEntityGenerator generator = (TileEntityGenerator) world.getTileEntity(pos);
			//generator.updateAdjacentHandlers();
		}
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbour) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity != null && tileentity instanceof TileEntityGenerator) {
			TileEntityGenerator generator = (TileEntityGenerator) world.getTileEntity(pos);
			//generator.updateAdjacentHandlers();
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
    public void addSpecialToolTip(ItemStack stack, World world, List<String> list, NBTTagCompound tag) {
        CalculatorHelper.addEnergytoToolTip(stack, world, list);
        CalculatorHelper.addItemLevelToolTip(stack, world, list);
        switch (type) {
		case 0:
			list.add(FontHelper.translate("energy.generate") + ": " + CalculatorConfig.getInteger("Starch Extractor") + " RF/t");
			break;
		case 1:
			list.add(FontHelper.translate("energy.generate") + ": " + CalculatorConfig.getInteger("Redstone Extractor") + " RF/t");
			break;
		case 2:
			list.add(FontHelper.translate("energy.generate") + ": " + CalculatorConfig.getInteger("Glowstone Extractor") + " RF/t");
			break;
		}
	}
}
